package dev.tobynguyen27.astralgenerators.contents.resolith

import dev.tobynguyen27.astralgenerators.contents.resolith.network.ResolithNode
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState

abstract class ResolithBlock(properties: Properties) : BaseEntityBlock(properties) {

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        isMoving: Boolean,
    ) {
        if (state != newState) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is ResolithNode) {
                blockEntity.connectedNodes.forEach {
                    if (level.hasChunk(it.x shr 4, it.z shr 4)) {
                        val targetBlockEntity = level.getBlockEntity(it)
                        if (targetBlockEntity is ResolithNode)
                            targetBlockEntity.removeConnection(pos)
                    }
                }
            }
            super.onRemove(state, level, pos, newState, isMoving)
        }
    }
}
