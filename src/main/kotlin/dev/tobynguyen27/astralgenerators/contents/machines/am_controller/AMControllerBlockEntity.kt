package dev.tobynguyen27.astralgenerators.contents.machines.am_controller

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class AMControllerBlockEntity(type: BlockEntityType<AMControllerBlockEntity>, blockPos: BlockPos, blockState: BlockState): BlockEntity(
    type, blockPos, blockState
) {
    companion object {
        const val ID = "am_entity"
    }
}
