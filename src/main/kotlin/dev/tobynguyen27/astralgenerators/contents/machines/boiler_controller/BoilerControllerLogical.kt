package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object BoilerControllerLogical {

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
        if (!level.isClientSide) {
            blockEntity.link()

            if (blockEntity.isFormed) {
                println("VALID")
            } else {
                println("INVALID")
            }
            blockEntity.setChanged()
        }
    }
}
