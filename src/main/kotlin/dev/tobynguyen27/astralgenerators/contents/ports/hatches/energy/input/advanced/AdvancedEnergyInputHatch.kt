package dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlock
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlockEntityLogical
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class AdvancedEnergyInputHatch(properties: Properties) : EnergyHatchBlock(properties) {

    companion object {
        const val ID = "advanced_energy_input_hatch"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.ADVANCED_ENERGY_INPUT_HATCH.create(pos, state)
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T?>,
    ): BlockEntityTicker<T?>? {
        if (level.isClientSide) return null

        return createTickerHelper(
            blockEntityType,
            AGBlockEntities.ADVANCED_ENERGY_INPUT_HATCH.get(),
            EnergyHatchBlockEntityLogical::serverTick,
        )
    }
}
