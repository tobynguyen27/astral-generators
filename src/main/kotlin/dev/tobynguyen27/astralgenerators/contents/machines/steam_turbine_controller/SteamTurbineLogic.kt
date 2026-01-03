package dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import dev.tobynguyen27.astralgenerators.registry.AGFluids
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.world.level.material.Fluids

object SteamTurbineLogic {

    // At 100%: MAX_STEAM_INTAKE * ACCELERATOR_FACTOR = MAX_RPM * DRAG_COEFFICIENT
    // These must be data-driven
    const val ENERGY_MULTIPLIER = 4
    const val DRAG_COEFFICIENT = 0.005
    const val ACCELERATOR_FACTOR = 0.001

    const val MAX_STEAM_INTAKE = 18000L // Max steam intake per tick

    fun produceWater(blockEntity: FluidHatchBlockEntity): Long {
        Transaction.openOuter().use {
            val producedAmount =
                blockEntity.fluidContainer.insert(
                    FluidVariant.of(Fluids.WATER.source),
                    MAX_STEAM_INTAKE,
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
                    MAX_STEAM_INTAKE,
                    it,
                )

            if (consumedAmount > 0L) it.commit()

            return consumedAmount
        }
    }

    /** Calculate how many RPM can [steamAmount] generate. */
    fun calculateRotorSpeed(steamAmount: Int) = (steamAmount * ACCELERATOR_FACTOR).toInt()

    /** Calculate rotor speed loss because of friction. */
    fun calculateRotorSpeedLoss(rotorSpeed: Int) = (rotorSpeed * DRAG_COEFFICIENT).toInt()

    /** Calculate amount of energy can be produced at [rotorSpeed]. */
    fun calculateEnergyProduced(rotorSpeed: Int) = (rotorSpeed * ENERGY_MULTIPLIER).toLong()
}
