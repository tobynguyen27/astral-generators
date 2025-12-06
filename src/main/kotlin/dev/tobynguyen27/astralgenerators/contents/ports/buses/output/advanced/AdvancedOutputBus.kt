package dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.ports.buses.BusBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class AdvancedOutputBus(properties: Properties) : BusBlock(properties) {

    companion object {
        const val ID = "advanced_output_bus"
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AGBlockEntities.ADVANCED_OUTPUT_BUS.create(pos, state)
    }
}
