package dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.iron

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithAttribute
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiverBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class IronResolithTransceiverBlockEntity(
    type: BlockEntityType<IronResolithTransceiverBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : ResolithTransceiverBlockEntity(type, blockPos, blockState) {
    override fun getMaxConnectionRange(): Int = ResolithAttribute.IRON_TRANSCEIVER.range

    override fun getMaxConnection(): Int = ResolithAttribute.IRON_TRANSCEIVER.maxConnections

    override fun getResolithTier(): ResolithTier = ResolithTier.IRON
}
