package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.core.multiblock.PortFlags
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object BoilerControllerLogical {

    fun clientTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BoilerControllerBlockEntity,
    ) {}

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BoilerControllerBlockEntity,
    ) {
        blockEntity.link()

        if(!blockEntity.isFormed) return
        if(blockEntity.shapeMatcher == null) return

        var inputBus: BlockPos? = null
        var inputHatch: BlockPos? = null
        var outputHatch: BlockPos? = null

        blockEntity.shapeMatcher!!.portFlags.forEach { (blockPos) ->
            val blockEntity = level.getBlockEntity(blockPos)

            if(blockEntity is PortBlockEntity) {
                when(blockEntity.getPortType()) {
                    PortBlockType.ITEM_INPUT -> {
                        if(inputBus == null) {
                            inputBus = blockPos
                        }
                    }
                    PortBlockType.FLUID_INPUT -> {
                        if(inputHatch == null) {
                            inputHatch = blockPos
                        }
                    }
                    PortBlockType.FLUID_OUTPUT -> {
                        if(outputHatch == null) {
                            outputHatch = blockPos
                        }
                    }
                    else -> {}
                }
            }
        }

        if(inputBus == null || inputHatch == null || outputHatch == null) return

        println(inputBus)
        println(inputHatch)
        println(outputHatch)

        blockEntity.setChanged()
    }
}
