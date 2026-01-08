package dev.tobynguyen27.astralgenerators.contents.resolith.pylon.pure

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.pylon.ResolithPylonBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class PureResolithPylonBlockEntity(
    type: BlockEntityType<PureResolithPylonBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : ResolithPylonBlockEntity(type, blockPos, blockState) {
    override fun getTier() = ResolithTier.ELECTRUM

    override fun getConnectionLimit(): Int = 16
}
