package dev.tobynguyen27.astralgenerators.hooks

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage

object FluidTransferAPI {

    fun init() {
        FluidStorage.SIDED.registerForBlockEntity(
            { blockEntity, _ -> blockEntity.fluidStorage },
            AGBlockEntities.ASSEMBLER.get(),
        )

        initFluidHatches()
    }

    private fun initFluidHatches() {
        listOf(
                AGBlockEntities.BASIC_FLUID_INPUT_HATCH,
                AGBlockEntities.BASIC_FLUID_OUTPUT_HATCH,
                AGBlockEntities.ADVANCED_FLUID_INPUT_HATCH,
                AGBlockEntities.ADVANCED_FLUID_OUTPUT_HATCH,
                AGBlockEntities.INDUSTRIAL_FLUID_INPUT_HATCH,
                AGBlockEntities.INDUSTRIAL_FLUID_OUTPUT_HATCH,
            )
            .forEach {
                FluidStorage.SIDED.registerForBlockEntity(
                    { blockEntity, _ -> blockEntity.fluidStorage },
                    it.get(),
                )
            }
    }
}
