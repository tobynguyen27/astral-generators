package dev.tobynguyen27.astralgenerators.contents.ports

import net.minecraft.world.level.block.state.BlockState

data class BusModelClientData(
    val mode: BusBlockEntity.Mode,
    val tier: BusBlockEntity.Tier,
    val casingBlock: BlockState?,
) {}
