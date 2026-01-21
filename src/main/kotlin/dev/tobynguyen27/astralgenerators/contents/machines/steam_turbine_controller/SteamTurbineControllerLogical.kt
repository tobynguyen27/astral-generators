package dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller

import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineLogic.calculateEnergyProduced
import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineLogic.calculateRotorSpeed
import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineLogic.consumeSteam
import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineLogic.produceEnergy
import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineLogic.produceWater
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockType
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.EnergyHatchBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.FluidHatchBlockEntity
import dev.tobynguyen27.astralgenerators.registry.AGFluids
import dev.tobynguyen27.astralgenerators.registry.AGSounds
import dev.tobynguyen27.sense.util.PrimitiveUtils.toBoolean
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties

object SteamTurbineControllerLogical {

    fun clientTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: SteamTurbineControllerBlockEntity,
    ) {
        val isActive = blockState.getValue(BlockStateProperties.LIT)

        if (!isActive) return
        if (level.gameTime % 64L != 0L) return

        level.playLocalSound(
            blockPos.x.toDouble(),
            blockPos.y.toDouble(),
            blockPos.z.toDouble(),
            AGSounds.STEAM_TURBINE_WORKING,
            SoundSource.BLOCKS,
            1f,
            1f,
            false,
        )
    }

    fun serverTick(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: SteamTurbineControllerBlockEntity,
    ) {
        if (!blockEntity.isEnabled.toBoolean()) {
            blockEntity.updateActiveState(false)
            return
        }
        blockEntity.link()

        if (!blockEntity.isFormed) {
            blockEntity.updateActiveState(false)
            return
        }
        if (blockEntity.shapeMatcher == null) return

        var fluidInputHatch: FluidHatchBlockEntity? = null
        var fluidOutputHatch: FluidHatchBlockEntity? = null
        var energyOutputHatch: EnergyHatchBlockEntity? = null

        blockEntity.shapeMatcher!!.getMatchedHatches().forEach { blockEntity ->
            when (blockEntity.getPortType()) {
                PortBlockType.FLUID_INPUT -> {
                    if (fluidInputHatch == null) {
                        fluidInputHatch = blockEntity as FluidHatchBlockEntity
                    }
                }
                PortBlockType.FLUID_OUTPUT -> {
                    if (fluidOutputHatch == null) {
                        fluidOutputHatch = blockEntity as FluidHatchBlockEntity
                    }
                }
                PortBlockType.ENERGY_OUTPUT -> {
                    if (energyOutputHatch == null) {
                        energyOutputHatch = blockEntity as EnergyHatchBlockEntity
                    }
                }
                else -> {}
            }
        }

        if (fluidInputHatch == null || fluidOutputHatch == null || energyOutputHatch == null) return

        // Always run
        val loss = SteamTurbineLogic.calculateRotorSpeedLoss(blockEntity.rotorSpeed)
        blockEntity.rotorSpeed -= if (blockEntity.rotorSpeed > 0) loss.coerceAtLeast(1) else 0
        blockEntity.setChanged()

        blockEntity.updateActiveState(false)

        if (blockEntity.rotorSpeed > 0) {
            produceWater(fluidOutputHatch)

            val producedEnergy = calculateEnergyProduced(blockEntity.rotorSpeed)
            produceEnergy(energyOutputHatch, producedEnergy)
        }

        // Check input
        if (
            fluidInputHatch.fluidContainer.variant !=
                FluidVariant.of(AGFluids.STEAM.get().source) ||
                fluidInputHatch.fluidContainer.amount == 0L
        )
            return

        // Consume steam and spin rotor
        val isSpeeding = blockEntity.rotorSpeed > blockEntity.maxRotorSpeed
        var isConsumingSteam = false

        if (!isSpeeding && !isEnergyHatchFull(energyOutputHatch)) {
            val consumedAmount = consumeSteam(fluidInputHatch)
            val generatedRotorSpeed = calculateRotorSpeed(consumedAmount.toInt())

            blockEntity.rotorSpeed += generatedRotorSpeed
            blockEntity.setChanged()
            isConsumingSteam = true
        }

        blockEntity.updateActiveState(isConsumingSteam)

        blockEntity.setChanged()
    }

    private fun isEnergyHatchFull(outputHatch: EnergyHatchBlockEntity): Boolean {
        return outputHatch.energyContainer.amount == outputHatch.energyContainer.capacity
    }
}
