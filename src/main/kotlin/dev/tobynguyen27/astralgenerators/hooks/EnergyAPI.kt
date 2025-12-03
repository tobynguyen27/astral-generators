package dev.tobynguyen27.astralgenerators.hooks

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerBlockEntity
import team.reborn.energy.api.EnergyStorage

object EnergyAPI {
    fun init() {
        EnergyStorage.SIDED.registerForBlockEntity(
            { blockEntity: AssemblerBlockEntity, _ -> blockEntity.energyStorage },
            AGBlockEntities.ASSEMBLER.get(),
        )
    }
}
