package dev.tobynguyen27.astralgenerators.contents.resolith.relay.electrum

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.relay.ResolithRelayBlock
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class ElectrumResolithRelay(properties: Properties) : ResolithRelayBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
        AGBlockEntities.ELECTRUM_RESOLITH_PYLON.create(pos, state)

    override fun getResolithTier(): ResolithTier = ResolithTier.ELECTRUM
}
