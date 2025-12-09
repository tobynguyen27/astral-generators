package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.contents.AGBlocks
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.multiblocks.PortFlags
import dev.tobynguyen27.astralgenerators.multiblocks.ShapeTemplate
import dev.tobynguyen27.astralgenerators.multiblocks.SimpleMember

object BoilerMultiblock {
    val SHAPE =
        ShapeTemplate.Builder(AGBlocks.BOILER_CASING.defaultState)
            .add3by3(
                -1,
                SimpleMember.forBlock(AGBlocks.BOILER_CASING.get()),
                false,
                PortFlags.Builder.with(
                        PortBlockType.ITEM_INPUT,
                        PortBlockType.FLUID_INPUT,
                        PortBlockType.FLUID_OUTPUT,
                    )
                    .build(),
            )
            .add3by3(0, SimpleMember.forBlock(AGBlocks.BOILER_CASING.get()), true, null)
            .add3by3(1, SimpleMember.forBlock(AGBlocks.BOILER_CASING.get()), true, null)
            .build()
}
