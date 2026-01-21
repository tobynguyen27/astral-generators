package dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.core.multiblock.PortFlags
import dev.tobynguyen27.astralgenerators.core.multiblock.ShapeTemplate
import dev.tobynguyen27.astralgenerators.core.multiblock.SimpleMember
import dev.tobynguyen27.astralgenerators.registry.AGBlocks

object BoilerMultiblock {
    val SHAPE =
        ShapeTemplate.Builder(AGBlocks.BOILER_CASING.defaultState)
            .apply {
                val fireboxCasing = SimpleMember.forBlock(AGBlocks.FIREBOX_CASING.get())
                val boilerCasing = SimpleMember.forBlock(AGBlocks.BOILER_CASING.get())
                val ports =
                    PortFlags(
                        PortBlockType.ITEM_INPUT,
                        PortBlockType.FLUID_INPUT,
                        PortBlockType.FLUID_OUTPUT,
                    )

                add3by3(-1, fireboxCasing, false, null)
                add3by3(0, boilerCasing, true, ports)
                add3by3(2, boilerCasing, false, null)
                add3by3(1, boilerCasing, true, null)
            }
            .build()
}
