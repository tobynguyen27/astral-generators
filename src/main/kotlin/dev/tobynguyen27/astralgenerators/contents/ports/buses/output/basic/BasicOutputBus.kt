package dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic

import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlock
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlockEntityLogical
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class BasicOutputBus(properties: Properties) : BusBlock(properties) {

    companion object {
        const val ID = "basic_output_bus"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.BASIC_OUTPUT_BUS.create(pos, state)
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T?>
    ): BlockEntityTicker<T?>? {
        if (level.isClientSide) return null

        return createTickerHelper(
            blockEntityType,
            AGBlockEntities.BASIC_OUTPUT_BUS.get(),
            BusBlockEntityLogical::serverTick,
        )
    }
}
