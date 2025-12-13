package dev.tobynguyen27.astralgenerators.contents.blocks

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty

class FireboxCasing(properties: Properties) : RotatedPillarBlock(properties) {

    companion object {
        const val ID = "firebox_casing"

        val LIT: BooleanProperty = BlockStateProperties.LIT
    }

    init {
        registerDefaultState(with(defaultBlockState()) { setValue(LIT, false) })
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(builder)
        builder.add(LIT)
    }
}
