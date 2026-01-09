package dev.tobynguyen27.astralgenerators.contents.resolith.transceiver

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlock
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithType
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty

abstract class ResolithTransceiver(properties: Properties) : ResolithBlock(properties) {

    companion object {
        val FACING: DirectionProperty = BlockStateProperties.FACING
    }

    init {
        registerDefaultState(with(defaultBlockState()) { setValue(FACING, Direction.DOWN) })
    }

    override fun getResolithType(): ResolithType = ResolithType.TRANSCEIVER

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
}
