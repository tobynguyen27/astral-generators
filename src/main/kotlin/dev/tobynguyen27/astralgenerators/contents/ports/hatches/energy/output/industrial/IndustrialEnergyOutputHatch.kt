package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.industrial

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class IndustrialEnergyOutputHatch(properties: Properties) : EnergyHatchBlock(properties) {

    companion object {
        const val ID = "industrial_energy_output_hatch"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.INDUSTRIAL_ENERGY_OUTPUT_HATCH.create(pos, state)
    }
}
