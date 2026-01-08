package dev.tobynguyen27.astralgenerators.contents.resolith.pylon.pure

import dev.tobynguyen27.astralgenerators.contents.resolith.pylon.ResolithPylonBlock
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PureResolithPylon(properties: Properties) : ResolithPylonBlock(properties) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
        AGBlockEntities.PURE_RESOLITH_PYLON.create(pos, state)
}
