package dev.tobynguyen27.astralgenerators.contents.resolith.relay.iron

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.relay.ResolithRelayBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class IronResolithRelayBlockEntity(
    type: BlockEntityType<IronResolithRelayBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : ResolithRelayBlockEntity(type, blockPos, blockState) {

    override fun getResolithTier(): ResolithTier = ResolithTier.IRON
}
