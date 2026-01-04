package dev.tobynguyen27.astralgenerators.contents.ports.buses

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.Containers
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

abstract class BusBlock(properties: Properties) : PortBlock(properties) {

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
                Containers.dropContents(level, pos, blockEntity.items)
                level.updateNeighbourForOutputSignal(pos, this)
            }
        }

        super.onRemove(state, level, pos, newState, isMoving)
    }
}
