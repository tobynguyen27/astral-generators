package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerBlockEntity.Companion.IDEAL_WATER_CONSUMPTION
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerBlockEntity.Companion.STEAM_EXPANSION_RATIO
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerBlockEntity.Companion.WATER_BOILING_POINT
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import dev.tobynguyen27.astralgenerators.registry.AGFluids
import dev.tobynguyen27.astralgenerators.registry.AGSounds
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Fluids

object BoilerControllerLogical {

    fun clientTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BoilerControllerBlockEntity,
    ) {
        val isActive = blockState.getValue(BlockStateProperties.LIT)

        if (!isActive) return
        if (level.gameTime % 64L != 0L) return

        level.playLocalSound(
            blockPos.x.toDouble(),
            blockPos.y.toDouble(),
            blockPos.z.toDouble(),
            AGSounds.BOILER_WORKING,
            SoundSource.BLOCKS,
            1f,
            1f,
            false,
        )
    }

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BoilerControllerBlockEntity,
    ) {
        blockEntity.link()

        if (!blockEntity.isFormed) {
            blockEntity.burnTime = 0
            blockEntity.maxBurnTime = 0
            blockEntity.heat = 0
            blockEntity.setChanged()

            blockEntity.updateActiveState(false)
            blockEntity.updateFireboxActiveState(false)
            return
        }
        if (blockEntity.shapeMatcher == null) return

        var inputBus: BusBlockEntity? = null
        var inputHatch: FluidHatchBlockEntity? = null
        var outputHatch: FluidHatchBlockEntity? = null

        blockEntity.shapeMatcher!!.getMatchedHatches().forEach { blockEntity ->
            when (blockEntity.getPortType()) {
                PortBlockType.ITEM_INPUT -> {
                    if (inputBus == null) {
                        inputBus = blockEntity as BusBlockEntity
                    }
                }
                PortBlockType.FLUID_INPUT -> {
                    if (inputHatch == null) {
                        inputHatch = blockEntity as FluidHatchBlockEntity
                    }
                }
                PortBlockType.FLUID_OUTPUT -> {
                    if (outputHatch == null) {
                        outputHatch = blockEntity as FluidHatchBlockEntity
                    }
                }
                else -> {}
            }
        }

        if (inputBus == null || inputHatch == null || outputHatch == null) return

        if (isFulled(outputHatch)) {
            if (blockEntity.burnTime > 0) {
                blockEntity.burnTime--
                blockEntity.setChanged()
            }

            if (blockEntity.heat > 0) {
                blockEntity.heat--
                blockEntity.setChanged()
            }

            blockEntity.updateActiveState(false)
            blockEntity.updateFireboxActiveState(false)
            return
        }

        // Fuel logic
        // Consume fuel if boiler is not being heated yet
        if (blockEntity.burnTime <= 0) {
            val addedBurnTime = consumeFuel(inputBus)
            if (addedBurnTime > 0) {
                blockEntity.burnTime = addedBurnTime
                blockEntity.maxBurnTime = addedBurnTime
                blockEntity.setChanged()
            }
        }

        val isBurning = blockEntity.burnTime > 0
        blockEntity.updateActiveState(isBurning)
        blockEntity.updateFireboxActiveState(isBurning)

        // Consume burn time
        if (isBurning) {
            blockEntity.burnTime--
            blockEntity.setChanged()
        }

        // Increase temp when it has water
        if (hasWater(inputHatch) && isBurning) {
            if (blockEntity.heat < blockEntity.maxHeat) {
                blockEntity.heat++
                blockEntity.setChanged()
            }
        } else {
            // No water so cooling
            if (blockEntity.burnTime > 0) {
                blockEntity.burnTime--
                // Use exponential decay
                blockEntity.setChanged()
            }
        }

        if (blockEntity.heat < WATER_BOILING_POINT) return

        val efficiency = blockEntity.heat.toDouble() / blockEntity.maxHeat
        val waterToConsume = (IDEAL_WATER_CONSUMPTION * efficiency).toLong()

        if (waterToConsume <= 0) return

        Transaction.openOuter().use {
            val extractedWater =
                inputHatch.fluidStorage.extract(FluidVariant.of(Fluids.WATER), waterToConsume, it)

            if (extractedWater > 0) {
                val steamToProduce = extractedWater * STEAM_EXPANSION_RATIO

                if (produceSteam(it, outputHatch, steamToProduce)) {
                    it.commit()
                    blockEntity.setChanged()
                } else {
                    it.abort()
                }
            } else {
                it.abort()
            }
        }

        blockEntity.setChanged()
    }

    private fun isFulled(outputHatch: FluidHatchBlockEntity): Boolean {
        Transaction.openOuter().use {
            return outputHatch.fluidStorage.insert(
                FluidVariant.of(AGFluids.STEAM.get().source),
                1L,
                it,
            ) == 0L
        }
    }

    private fun hasWater(inputHatch: FluidHatchBlockEntity): Boolean {
        Transaction.openOuter().use {
            return inputHatch.fluidStorage.extract(FluidVariant.of(Fluids.WATER), 1L, it) > 0
        }
    }

    private fun consumeFuel(inputBus: BusBlockEntity): Int {
        Transaction.openOuter().use {
            for (input in inputBus.inputStorage) {
                if (input.isResourceBlank) {
                    continue
                }

                val resource = input.resource
                val fuelBurnTime = getFuelBurnTime(resource.toStack())
                if (fuelBurnTime > 0) {
                    val consumedAmount = input.extract(resource, 1, it)

                    if (consumedAmount == 1L) {
                        it.commit()
                        return fuelBurnTime
                    }
                }
            }

            return 0
        }
    }

    private fun getFuelBurnTime(fuel: ItemStack): Int {
        return when {
            fuel.`is`(ItemTags.COALS) -> 1600
            fuel.`is`(ItemTags.LOGS_THAT_BURN) -> 800
            fuel.`is`(ItemTags.PLANKS) -> 400
            else -> 0
        }
    }

    private fun produceSteam(
        transaction: Transaction,
        outputHatch: FluidHatchBlockEntity,
        amountToProduce: Long,
        simulate: Boolean = false,
    ): Boolean {
        transaction.openNested().use {
            val producedAmount =
                outputHatch.fluidStorage.insert(
                    FluidVariant.of(AGFluids.STEAM.get().source),
                    amountToProduce,
                    it,
                )

            val result = producedAmount > 0

            if (result && !simulate) {
                it.commit()
            }

            return result
        }
    }
}
