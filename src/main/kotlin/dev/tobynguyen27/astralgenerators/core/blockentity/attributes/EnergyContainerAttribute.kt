package dev.tobynguyen27.astralgenerators.core.blockentity.attributes

import net.minecraft.nbt.CompoundTag
import team.reborn.energy.api.base.SimpleEnergyStorage

interface EnergyContainerAttribute : Attribute {
    val energyContainer: SimpleEnergyStorage

    fun saveEnergyData(tag: CompoundTag) {
        tag.putLong("energyAmount", energyContainer.amount)
    }

    fun loadEnergyData(tag: CompoundTag) {
        energyContainer.amount = tag.getLong("energyAmount")
    }

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
