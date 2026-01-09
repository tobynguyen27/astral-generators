package dev.tobynguyen27.astralgenerators.contents.resolith

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithType
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithVoxelShape
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

abstract class ResolithBlock(properties: Properties) : BaseEntityBlock(properties) {

    abstract fun getResolithType(): ResolithType

    abstract fun getResolithTier(): ResolithTier

    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext,
    ): VoxelShape {
        if (getResolithType() == ResolithType.RELAY) {
            return ResolithVoxelShape.RELAY_SHAPE
        }

        return ResolithVoxelShape.TRANSCEIVER_SHAPES[
                state.getValue(BlockStateProperties.FACING).opposite] ?: Shapes.block()
    }

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
            if (blockEntity is ResolithBlockEntity) {
                blockEntity.connectedNodes.forEach {
                    if (level.hasChunk(it.x shr 4, it.z shr 4)) {
                        val targetBlockEntity = level.getBlockEntity(it)
                        if (targetBlockEntity is ResolithBlockEntity)
                            targetBlockEntity.removeConnection(pos)
                    }
                }
            }
            super.onRemove(state, level, pos, newState, isMoving)
        }
    }
}
