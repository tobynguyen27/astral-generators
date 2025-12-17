package dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlock
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntityLogical
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class AdvancedFluidOutputHatch(properties: Properties) : FluidHatchBlock(properties) {

    companion object {
        const val ID = "advanced_fluid_output_hatch"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.ADVANCED_FLUID_OUTPUT_HATCH.create(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>,
    ): BlockEntityTicker<T>? {
        if (level.isClientSide) return null

        return createTickerHelper(
            blockEntityType,
            AGBlockEntities.ADVANCED_FLUID_OUTPUT_HATCH.get(),
            FluidHatchBlockEntityLogical::serverTick,
        )
    }
}
