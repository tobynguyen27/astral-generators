package dev.tobynguyen27.astralgenerators.core.blockentity.attributes

import team.reborn.energy.api.base.SimpleEnergyStorage

interface EnergyContainerAttribute : Attribute {
    var energyContainer: SimpleEnergyStorage

    fun createEnergyContainer(
        capacity: Long,
        maxExtract: Long,
        maxInsert: Long,
    ): SimpleEnergyStorage {
        return object : SimpleEnergyStorage(capacity, maxInsert, maxExtract) {
            override fun onFinalCommit() {
                self.setChanged()
            }
        }
    }
}
