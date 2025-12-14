package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.industrial

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlock
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class IndustrialFluidOutputHatch(properties: Properties) : FluidHatchBlock(properties) {

    companion object {
        const val ID = "industrial_fluid_output_hatch"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.INDUSTRIAL_FLUID_OUTPUT_HATCH.create(pos, state)
    }
}
