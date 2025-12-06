package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.basic

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BasicFluidOutputHatch(properties: Properties) : FluidHatchBlock(properties) {

    companion object {
        const val ID = "basic_fluid_output_hatch"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.BASIC_FLUID_OUTPUT_HATCH.create(pos, state)
    }
}
