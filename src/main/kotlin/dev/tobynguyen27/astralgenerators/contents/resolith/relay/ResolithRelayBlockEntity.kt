package dev.tobynguyen27.astralgenerators.contents.resolith.relay

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlockEntity
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithType
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class ResolithRelayBlockEntity(
    type: BlockEntityType<out ResolithRelayBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) : ResolithBlockEntity(type, blockPos, blockState) {
    override fun getNodeType(): ResolithType = ResolithType.RELAY
}
