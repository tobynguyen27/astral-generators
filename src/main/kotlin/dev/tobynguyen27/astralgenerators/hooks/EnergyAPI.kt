package dev.tobynguyen27.astralgenerators.hooks

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerBlockEntity
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import team.reborn.energy.api.EnergyStorage

object EnergyAPI {

    fun init() {
        EnergyStorage.SIDED.registerForBlockEntity(
            { blockEntity: AssemblerBlockEntity, _ -> blockEntity.energyContainer },
            AGBlockEntities.ASSEMBLER.get(),
        )

        initEnergyHatches()
    }

    private fun initEnergyHatches() {
        setOf(
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
