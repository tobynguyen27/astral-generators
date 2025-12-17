package dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

object SteamTurbineControllerLogical {
    fun clientTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: SteamTurbineControllerBlockEntity,
    ) {}

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: SteamTurbineControllerBlockEntity,
    ) {
        blockEntity.link()

        if (blockEntity.shapeMatcher == null) return
        if (!blockEntity.isFormed) return

        blockEntity.setChanged()
    }
}
