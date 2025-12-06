package dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BasicInputBus(properties: Properties) : BusBlock(properties) {

    companion object {
        const val ID = "basic_input_bus"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.BASIC_INPUT_BUS.create(pos, state)
    }
}
