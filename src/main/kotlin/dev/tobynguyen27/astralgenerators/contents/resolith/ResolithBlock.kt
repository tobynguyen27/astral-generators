package dev.tobynguyen27.astralgenerators.contents.resolith

import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithAttribute
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithTier
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithType
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithVoxelShape
import dev.tobynguyen27.astralgenerators.data.client.Texts
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
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

    override fun appendHoverText(
        stack: ItemStack,
        level: BlockGetter?,
        tooltip: MutableList<Component>,
        flag: TooltipFlag,
    ) {
        val stats = ResolithAttribute.getStats(getResolithType(), getResolithTier())

        tooltip.add(
            TranslatableComponent(Texts.NODE_MAX_CONNECTION)
                .withStyle(ChatFormatting.BLUE)
                .append(
                    TranslatableComponent(Texts.NODE_MAX_CONNECTION_UNIT, stats.maxConnections)
                        .withStyle(ChatFormatting.GRAY)
                )
        )
        tooltip.add(
            TranslatableComponent(Texts.NODE_MAX_RANGE)
                .withStyle(ChatFormatting.BLUE)
                .append(
                    TranslatableComponent(Texts.NODE_MAX_RANGE_UNIT, stats.range)
                        .withStyle(ChatFormatting.GRAY)
                )
        )
    }
}
