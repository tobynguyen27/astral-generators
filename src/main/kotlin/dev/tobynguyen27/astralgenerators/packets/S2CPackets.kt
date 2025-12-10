package dev.tobynguyen27.astralgenerators.packets

import dev.tobynguyen27.astralgenerators.utils.Identifier

object S2CPackets {

    val ENERGY_AMOUNT = Identifier("energy_amount")

    val FLUID_AMOUNT = Identifier("fluid_amount")
    val FLUID_VARIANT = Identifier("fluid_variant")

    val MULTIBLOCK_SYNC = Identifier("multiblock_sync")
}
