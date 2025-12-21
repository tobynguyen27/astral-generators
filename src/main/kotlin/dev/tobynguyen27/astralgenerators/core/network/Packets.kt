package dev.tobynguyen27.astralgenerators.core.network

import dev.tobynguyen27.astralgenerators.core.util.Identifier

object Packets {

    val ENERGY_AMOUNT = Identifier("energy_amount")

    val FLUID_AMOUNT = Identifier("fluid_amount")
    val FLUID_VARIANT = Identifier("fluid_variant")

    val CONFIG_SYNC = Identifier("config_sync")
    val MULTIBLOCK_SYNC = Identifier("multiblock_sync")
    val TOGGLE_AUTO_IMPORT = Identifier("toggle_auto_import")
    val TOGGLE_AUTO_EXPORT = Identifier("toggle_auto_export")
    val TOGGLE_MACHINE = Identifier("toggle_machine")
}
