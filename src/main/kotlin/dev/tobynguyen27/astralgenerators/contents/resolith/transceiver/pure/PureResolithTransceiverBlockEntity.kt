package dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.pure

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiverBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class PureResolithTransceiverBlockEntity(
    type: BlockEntityType<PureResolithTransceiverBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : ResolithTransceiverBlockEntity(type, blockPos, blockState) {
    override fun getTier(): ResolithTier = ResolithTier.ELECTRUM

    override fun getConnectionLimit(): Int = 3
}
