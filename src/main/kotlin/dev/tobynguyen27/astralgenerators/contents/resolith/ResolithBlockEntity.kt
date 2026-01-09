package dev.tobynguyen27.astralgenerators.contents.resolith

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class ResolithBlockEntity(
    type: BlockEntityType<out ResolithBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : ResolithNode(type, blockPos, blockState) {

    abstract fun getTier(): ResolithTier
}
