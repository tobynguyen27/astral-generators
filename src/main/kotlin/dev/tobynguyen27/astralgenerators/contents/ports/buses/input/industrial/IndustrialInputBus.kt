package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class IndustrialInputBus(properties: Properties) : BusBlock(properties) {

    companion object {
        const val ID = "industrial_input_bus"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.INDUSTRIAL_INPUT_BUS.create(pos, state)
    }
}
