package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.basic

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BasicEnergyOutputHatch(properties: Properties) : EnergyHatchBlock(properties) {

    companion object {
        const val ID = "basic_energy_output_hatch"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.BASIC_ENERGY_OUTPUT_HATCH.create(pos, state)
    }
}
