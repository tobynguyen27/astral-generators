package dev.tobynguyen27.astralgenerators.contents.ports

import net.minecraft.core.BlockPos
import net.minecraft.world.Containers
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState

abstract class BusBlock(properties: Properties) : BaseEntityBlock(properties) {

    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        isMoving: Boolean,
    ) {
        if (state.block != newState.block) {
            val blockEntity = level.getBlockEntity(pos)

            if (blockEntity is BusBlockEntity) {
                Containers.dropContents(level, pos, blockEntity.getItems())
                level.updateNeighbourForOutputSignal(pos, this)
            }
        }

        super.onRemove(state, level, pos, newState, isMoving)
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }
}
