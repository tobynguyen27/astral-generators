package dev.tobynguyen27.astralgenerators.contents.ports.buses.output.industrial

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.BusBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class IndustrialOutputBus(properties: Properties) : BusBlock(properties) {

    companion object {
        const val ID = "industrial_output_bus"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.INDUSTRIAL_OUTPUT_BUS.create(pos, state)
    }
}
