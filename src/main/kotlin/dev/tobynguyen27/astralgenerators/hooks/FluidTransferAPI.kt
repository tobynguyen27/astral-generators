package dev.tobynguyen27.astralgenerators.hooks

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage

object FluidTransferAPI {
    fun init() {
        FluidStorage.SIDED.registerForBlockEntity(
            { blockEntity, direction -> blockEntity.fluidStorage },
            AGBlockEntities.ASSEMBLER.get(),
        )
    }
}
