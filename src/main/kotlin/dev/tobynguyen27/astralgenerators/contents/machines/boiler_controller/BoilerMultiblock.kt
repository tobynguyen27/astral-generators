package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.core.multiblock.PortFlags
import dev.tobynguyen27.astralgenerators.core.multiblock.ShapeTemplate
import dev.tobynguyen27.astralgenerators.core.multiblock.SimpleMember
import dev.tobynguyen27.astralgenerators.registry.AGBlocks

object BoilerMultiblock {
    val SHAPE =
        ShapeTemplate.Builder(AGBlocks.BOILER_CASING.defaultState)
            .add3by3(-1, SimpleMember.forBlock(AGBlocks.FIREBOX_CASING.get()), false, null)
            .add3by3(
                0,
                SimpleMember.forBlock(AGBlocks.BOILER_CASING.get()),
                true,
                PortFlags.Builder.with(
                        PortBlockType.ITEM_INPUT,
                        PortBlockType.FLUID_INPUT,
                        PortBlockType.FLUID_OUTPUT,
                    )
                    .build(),
            )
            .add3by3(1, SimpleMember.forBlock(AGBlocks.BOILER_CASING.get()), true, null)
            .add3by3(2, SimpleMember.forBlock(AGBlocks.BOILER_CASING.get()), true, null)
            .add3by3(3, SimpleMember.forBlock(AGBlocks.BOILER_CASING.get()), true, null)
            .build()
}
