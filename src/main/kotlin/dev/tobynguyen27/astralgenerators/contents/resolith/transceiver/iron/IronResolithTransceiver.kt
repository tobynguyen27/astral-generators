package dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.iron

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiver
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState

class IronResolithTransceiver(properties: BlockBehaviour.Properties) :
    ResolithTransceiver(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
        AGBlockEntities.RAW_RESOLITH_TRANSCEIVER.create(pos, state)

    override fun getResolithTier(): ResolithTier = ResolithTier.IRON
}
