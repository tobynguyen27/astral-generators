package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlock
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class AdvancedEnergyOutputHatch(properties: Properties) : EnergyHatchBlock(properties) {

    companion object {
        const val ID = "advanced_energy_output_hatch"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.ADVANCED_ENERGY_OUTPUT_HATCH.create(pos, state)
    }
}
