package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import dev.tobynguyen27.astralgenerators.registry.AGFluids
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.core.BlockPos
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids

object BoilerControllerLogical {

    private const val WATER_BOILING_POINT = 100

    fun clientTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BoilerControllerBlockEntity,
    ) {}

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BoilerControllerBlockEntity,
    ) {
        blockEntity.link()

        if (!blockEntity.isFormed) return
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

        // Temperature
        if (blockEntity.heat < blockEntity.maxHeat) {
            blockEntity.heat += 2
        }

        if (blockEntity.heat < WATER_BOILING_POINT) return
        // Steam

        // Craft logic here
        Transaction.openOuter().use { transaction ->
            val consumeFluid = consumeWater(transaction, inputHatch, 1)
            val canProduceFluid = produceSteam(transaction, outputHatch, 2, true)
            val consumeFuel = consumeFuel(transaction, inputBus, 1)

            if (consumeFluid && canProduceFluid && consumeFuel) {
                produceSteam(transaction, outputHatch, 2)
                transaction.commit()
                blockEntity.setChanged()
            } else {
                transaction.abort()
            }
        }

        blockEntity.setChanged()
    }

    private fun consumeFuel(
        transaction: Transaction,
        inputBus: BusBlockEntity,
        amountToConsume: Int,
        simulate: Boolean = false,
    ): Boolean {
        transaction.openNested().use {
            val logFuel = Ingredient.of(ItemTags.LOGS_THAT_BURN)
            val plankFuel = Ingredient.of(ItemTags.PLANKS)
            val coalFuel = Ingredient.of(ItemTags.COALS)

            var neededAmount = amountToConsume.toLong()

            for (input in inputBus.inputStorage) {
                if (neededAmount == 0L) {
                    break
                }
                if (input.isResourceBlank) {
                    continue
                }

                val resource = input.resource

                if (
                    logFuel.test(resource.toStack()) ||
                        plankFuel.test(resource.toStack()) ||
                        coalFuel.test(resource.toStack())
                ) {
                    val consumedAmount = input.extract(resource, neededAmount, it)

                    neededAmount -= consumedAmount
                }
            }

            val result = neededAmount <= 0

            if (result && !simulate) {
                it.commit()
            }

            return result
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

            val result = producedAmount == amountToProduce

            if (result && !simulate) {
                it.commit()
            }

            return result
        }
    }

    private fun consumeWater(
        transaction: Transaction,
        inputHatch: FluidHatchBlockEntity,
        amountToConsume: Long,
    ): Boolean {
        val consumedAmount =
            inputHatch.fluidStorage.extract(
                FluidVariant.of(Fluids.WATER),
                amountToConsume,
                transaction,
            )

        return consumedAmount == amountToConsume
    }
}
