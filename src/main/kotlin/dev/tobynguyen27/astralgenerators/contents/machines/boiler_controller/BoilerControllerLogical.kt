package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import dev.tobynguyen27.astralgenerators.registry.AGFluids
import dev.tobynguyen27.codebebelib.utils.ClientUtils
import dev.tobynguyen27.codebebelib.utils.ServerUtils
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.core.BlockPos
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
        if(blockEntity.heat < blockEntity.maxHeat) {
            blockEntity.heat += 2
        }

        if (blockEntity.heat < WATER_BOILING_POINT) return
        // Steam

        // Craft logic here
        Transaction.openOuter().use { transaction ->
            val consumeFluid = consumeFluid(transaction, inputHatch, 1)
            val canProduceFluid = produceFluid(transaction, outputHatch, 2, true)
            if (consumeFluid && canProduceFluid) {
                produceFluid(transaction, outputHatch, 2)
                transaction.commit()
                blockEntity.setChanged()
            } else {
                transaction.abort()
            }
        }

        blockEntity.setChanged()
    }

    private fun produceFluid(
        transaction: Transaction,
        outputHatch: FluidHatchBlockEntity,
        amountToProduce: Long,
        simulate: Boolean = false,
    ): Boolean {

        if (simulate) {
            transaction.openNested().use { t ->
                val producedAmount =
                    outputHatch.fluidStorage.insert(
                        FluidVariant.of(AGFluids.STEAM.get().source),
                        amountToProduce,
                        t,
                    )

                return producedAmount == amountToProduce
            }
        }

        val producedAmount =
            outputHatch.fluidStorage.insert(
                FluidVariant.of(AGFluids.STEAM.get().source),
                amountToProduce,
                transaction,
            )

        return producedAmount == amountToProduce
    }

    private fun consumeFluid(
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
