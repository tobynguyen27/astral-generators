package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.basic

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlock
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BasicEnergyInputHatch(properties: Properties) : EnergyHatchBlock(properties) {

    companion object {
        const val ID = "basic_energy_input_hatch"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.BASIC_ENERGY_INPUT_HATCH.create(pos, state)
    }
}
