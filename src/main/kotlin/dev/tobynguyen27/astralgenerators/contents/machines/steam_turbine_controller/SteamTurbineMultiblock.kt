package dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.core.multiblock.PortFlags
import dev.tobynguyen27.astralgenerators.core.multiblock.ShapeTemplate
import dev.tobynguyen27.astralgenerators.core.multiblock.SimpleMember
import dev.tobynguyen27.astralgenerators.registry.AGBlocks

object SteamTurbineMultiblock {

    val SHAPE =
        ShapeTemplate.Builder(AGBlocks.STEAM_TURBINE_CASING.defaultState)
            .apply {
                val steamTurbineVent = SimpleMember.forBlock(AGBlocks.STEAM_TURBINE_VENT.get())
                val pipeCasing = SimpleMember.forBlock(AGBlocks.PIPE_CASING.get())
                val casing = SimpleMember.forBlock(AGBlocks.STEAM_TURBINE_CASING.get())
                val ports =
                    PortFlags(
                        PortBlockType.FLUID_INPUT,
                        PortBlockType.FLUID_OUTPUT,
                        PortBlockType.ENERGY_OUTPUT,
                    )

                // Bottom
                add3by3(-1, casing, false, ports)
                apply {
                    for (x in -1..1) {
                        add(x, 0, 3, casing, ports)
                    }
                }
                // Middle
                apply {
                    for (z in 0..3) {
                        add(-1, 0, z, casing, ports)
                        add(1, 0, z, casing, ports)
                    }
                }
                add(0, 0, 0, casing)
                add(0, 0, 1, pipeCasing)
                add(0, 0, 2, pipeCasing)
                add(0, 0, 3, steamTurbineVent)
                // Top
                add3by3(1, casing, false, ports)
                apply {
                    for (x in -1..1) {
                        add(x, 1, 3, casing, ports)
                    }
                }
            }
            .build()
}
