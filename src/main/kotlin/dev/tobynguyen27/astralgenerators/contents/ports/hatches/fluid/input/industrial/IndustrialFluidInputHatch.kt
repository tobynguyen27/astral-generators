package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class IndustrialFluidInputHatch(properties: Properties) : FluidHatchBlock(properties) {

    companion object {
        const val ID = "industrial_fluid_input_hatch"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.INDUSTRIAL_FLUID_INPUT_HATCH.create(pos, state)
    }
}
