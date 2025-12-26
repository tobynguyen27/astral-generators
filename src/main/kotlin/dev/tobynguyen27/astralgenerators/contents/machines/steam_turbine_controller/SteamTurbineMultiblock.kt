package dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.core.multiblock.PortFlags
import dev.tobynguyen27.astralgenerators.core.multiblock.ShapeTemplate
import dev.tobynguyen27.astralgenerators.core.multiblock.SimpleMember
import dev.tobynguyen27.astralgenerators.registry.AGBlocks

object SteamTurbineMultiblock {

    private val STEAM_TURBINE_VENT = SimpleMember.forBlock(AGBlocks.STEAM_TURBINE_VENT.get())
    private val PIPE_CASING = SimpleMember.forBlock(AGBlocks.PIPE_CASING.get())
    private val CASING = SimpleMember.forBlock(AGBlocks.STEAM_TURBINE_CASING.get())
    private val PORTS =
        PortFlags.Builder()
            .with(
                PortBlockType.FLUID_INPUT,
                PortBlockType.FLUID_OUTPUT,
                PortBlockType.ENERGY_OUTPUT,
            )
            .build()

    val SHAPE =
        ShapeTemplate.Builder(AGBlocks.STEAM_TURBINE_CASING.defaultState)
            // Bottom
            .add3by3(-1, CASING, false, PORTS)
            .apply {
                for (x in -1..1) {
                    add(x, 0, 3, CASING, PORTS)
                }
            }
            // Middle
            .apply {
                for (z in 0..3) {
                    add(-1, 0, z, CASING, PORTS)
                    add(1, 0, z, CASING, PORTS)
                }
            }
            .add(0, 0, 0, CASING)
            .add(0, 0, 1, PIPE_CASING)
            .add(0, 0, 2, PIPE_CASING)
            .add(0, 0, 3, STEAM_TURBINE_VENT)
            // Top
            .add3by3(1, CASING, false, PORTS)
            .apply {
                for (x in -1..1) {
                    add(x, 1, 3, CASING, PORTS)
                }
            }
            .build()
}
