package dev.tobynguyen27.astralgenerators.contents.resolith.pylon.raw

import dev.tobynguyen27.astralgenerators.contents.resolith.pylon.ResolithPylonBlock
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class RawResolithPylon(properties: Properties) : ResolithPylonBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
        AGBlockEntities.RAW_RESOLITH_PYLON.create(pos, state)
}
