package dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import dev.tobynguyen27.astralgenerators.data.config.ConfigHolder.CONFIG
import dev.tobynguyen27.astralgenerators.registry.AGFluids
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.world.level.material.Fluids

object SteamTurbineLogic {

    fun produceWater(blockEntity: FluidHatchBlockEntity): Long {
        Transaction.openOuter().use {
            val producedAmount =
                blockEntity.fluidContainer.insert(
                    FluidVariant.of(Fluids.WATER.source),
                    CONFIG.maxSteamIntake.toLong(),
                    it,
                )

            if (producedAmount > 0L) it.commit()

            return producedAmount
        }
    }

    fun produceEnergy(blockEntity: EnergyHatchBlockEntity, amount: Long): Long {
        Transaction.openOuter().use {
            val producedAmount = blockEntity.energyContainer.insert(amount, it)

            if (producedAmount > 0L) it.commit()

            return producedAmount
        }
    }

    fun consumeSteam(blockEntity: FluidHatchBlockEntity): Long {
        Transaction.openOuter().use {
            val consumedAmount =
                blockEntity.fluidContainer.extract(
                    FluidVariant.of(AGFluids.STEAM.get().source),
                    CONFIG.maxSteamIntake.toLong(),
                    it,
                )

            if (consumedAmount > 0L) it.commit()

            return consumedAmount
        }
    }

    /** Calculate how many RPM can [steamAmount] generate. */
    fun calculateRotorSpeed(steamAmount: Int) = (steamAmount * CONFIG.acceleratorFactor).toInt()

    /** Calculate rotor speed loss because of friction. */
    fun calculateRotorSpeedLoss(rotorSpeed: Int) = (rotorSpeed * CONFIG.dragCoefficient).toInt()

    /** Calculate amount of energy can be produced at [rotorSpeed]. */
    fun calculateEnergyProduced(rotorSpeed: Int) = (rotorSpeed * CONFIG.energyMultiplier).toLong()
}
