package dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.electrum

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithAttribute
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiverBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class ElectrumResolithTransceiverBlockEntity(
    type: BlockEntityType<ElectrumResolithTransceiverBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : ResolithTransceiverBlockEntity(type, blockPos, blockState) {
    override fun getMaxConnectionRange(): Int = ResolithAttribute.ELECTRUM_TRANSCEIVER.range

    override fun getMaxConnection(): Int = ResolithAttribute.ELECTRUM_TRANSCEIVER.maxConnections

    override fun getResolithTier(): ResolithTier = ResolithTier.ELECTRUM
}
