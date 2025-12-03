package dev.tobynguyen27.astralgenerators.hooks

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities.ASSEMBLER
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerEntity
import team.reborn.energy.api.EnergyStorage

object EnergyAPI {
    fun init() {
        EnergyStorage.SIDED.registerForBlockEntity(
            { blockEntity: AssemblerEntity, _ -> blockEntity.energyStorage },
            AGBlockEntities.ASSEMBLER.get(),
        )
    }
}
