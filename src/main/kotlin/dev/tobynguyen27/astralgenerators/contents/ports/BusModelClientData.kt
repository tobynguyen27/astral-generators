package dev.tobynguyen27.astralgenerators.contents.ports

import net.minecraft.world.level.block.state.BlockState

data class BusModelClientData(val type: BusBlockEntity.Type, val casingBlock: BlockState?) {}
