package dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced

import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlock
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlockEntityLogical
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class AdvancedOutputBus(properties: Properties) : BusBlock(properties) {

    companion object {
        const val ID = "advanced_output_bus"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.ADVANCED_OUTPUT_BUS.create(pos, state)
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T?>,
    ): BlockEntityTicker<T?>? {
        if (level.isClientSide) return null

        return createTickerHelper(
            blockEntityType,
            AGBlockEntities.ADVANCED_OUTPUT_BUS.get(),
            BusBlockEntityLogical::serverTick,
        )
    }
}
