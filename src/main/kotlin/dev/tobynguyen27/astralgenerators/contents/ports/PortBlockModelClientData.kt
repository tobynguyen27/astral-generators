package dev.tobynguyen27.astralgenerators.contents.ports

import net.minecraft.world.level.block.state.BlockState

data class PortBlockModelClientData(
    val mode: PortBlockSpecification.Mode,
    val tier: PortBlockSpecification.Tier,
    val casingBlock: BlockState?,
) {}
