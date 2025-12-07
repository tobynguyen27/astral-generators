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

        initEnergyHatches()
    }

    private fun initEnergyHatches() {
        listOf(
                AGBlockEntities.BASIC_ENERGY_INPUT_HATCH,
                AGBlockEntities.ADVANCED_ENERGY_INPUT_HATCH,
                AGBlockEntities.INDUSTRIAL_ENERGY_INPUT_HATCH,
                AGBlockEntities.BASIC_ENERGY_OUTPUT_HATCH,
                AGBlockEntities.ADVANCED_ENERGY_OUTPUT_HATCH,
                AGBlockEntities.INDUSTRIAL_ENERGY_OUTPUT_HATCH,
            )
            .forEach {
                EnergyStorage.SIDED.registerForBlockEntity(
                    { blockEntity, _ -> blockEntity.energyStorage },
                    it.get(),
                )
            }
    }
}
