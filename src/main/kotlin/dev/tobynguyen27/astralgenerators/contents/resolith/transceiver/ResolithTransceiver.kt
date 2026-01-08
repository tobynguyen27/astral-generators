package dev.tobynguyen27.astralgenerators.contents.resolith.transceiver

import com.google.common.collect.ImmutableSet
import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlock
import dev.tobynguyen27.codebebelib.raytracer.IndexedVoxelShape
import dev.tobynguyen27.codebebelib.raytracer.MultiIndexedVoxelShape
import dev.tobynguyen27.codebebelib.vec.Cuboid6
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

abstract class ResolithTransceiver(properties: Properties) : ResolithBlock(properties) {

    companion object {
        val SHAPE: VoxelShape = run {
            val builder = ImmutableSet.builder<IndexedVoxelShape>()

            builder.add(box(5.0, 5.0, 5.0, 11.0, 11.0, 11.0, 0))
            builder.add(box(7.0, 11.0, 5.0, 9.0, 13.0, 13.0, 1))
            builder.add(box(7.0, 5.0, 11.0, 9.0, 11.0, 13.0, 2))
            builder.add(box(7.0, 5.0, 3.0, 9.0, 13.0, 5.0, 3))
            builder.add(box(3.0, 11.0, 7.0, 7.0, 13.0, 9.0, 4))
            builder.add(box(9.0, 11.0, 7.0, 11.0, 13.0, 9.0, 5))
            builder.add(box(3.0, 5.0, 7.0, 5.0, 11.0, 9.0, 6))
            builder.add(box(11.0, 5.0, 7.0, 13.0, 13.0, 9.0, 7))

            MultiIndexedVoxelShape(builder.build())
        }

        private fun box(
            x1: Double,
            y1: Double,
            z1: Double,
            x2: Double,
            y2: Double,
            z2: Double,
            data: Any,
        ): IndexedVoxelShape {
            val cuboid = Cuboid6(x1 / 16.0, y1 / 16.0, z1 / 16.0, x2 / 16.0, y2 / 16.0, z2 / 16.0)
            return IndexedVoxelShape(cuboid.shape(), data)
        }

        val FACING: DirectionProperty = BlockStateProperties.FACING
    }

    init {
        registerDefaultState(with(defaultBlockState()) { setValue(FACING, Direction.DOWN) })
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val direction = context.clickedFace
        val blockState =
            context.level.getBlockState(context.clickedPos.relative(direction.opposite))
        return if (
            blockState.`is`(this) &&
                blockState.getValue<Direction>(DirectionalBlock.FACING) == direction
        )
            this.defaultBlockState()
                .setValue<Direction, Direction>(DirectionalBlock.FACING, direction.opposite)
        else
            this.defaultBlockState()
                .setValue<Direction, Direction>(DirectionalBlock.FACING, direction)
    }

    override fun getShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext,
    ): VoxelShape = SHAPE
}
