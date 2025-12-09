package dev.tobynguyen27.astralgenerators.contents.ports

enum class PortBlockType(val value: Int) {

    ITEM_INPUT(1),
    ITEM_OUTPUT(2),
    FLUID_INPUT(3),
    FLUID_OUTPUT(4),
    ENERGY_INPUT(5),
    ENERGY_OUTPUT(6),
}
