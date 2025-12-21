package dev.tobynguyen27.astralgenerators.data.config

import dev.tobynguyen27.astralgenerators.core.network.Packets
import me.shedaniel.autoconfig.AutoConfig
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking

object ConfigSyncHandler {

    fun initialize() {
        ServerPlayConnectionEvents.JOIN.register { impl, _, _ ->
            val config = AutoConfig.getConfigHolder<AGConfig>(AGConfig::class.java).get()
            val packet = PacketByteBufs.create()

            packet.writeInt(config.waterBoilingPoint)
            packet.writeInt(config.steamExpansionRatio)
            packet.writeInt(config.idealWaterConsumption)

            packet.writeDouble(config.dragCoefficient)
            packet.writeDouble(config.acceleratorFactor)
            packet.writeInt(config.maxSteamIntake)
            packet.writeInt(config.energyMultiplier)

            ServerPlayNetworking.send(impl.player, Packets.CONFIG_SYNC, packet)
        }
    }

    fun initializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(
            Packets.CONFIG_SYNC,
            { minecraft, _, packet, _ ->
                val waterBoilingPoint = packet.readInt()
                val steamExpansionRatio = packet.readInt()
                val idealWaterConsumption = packet.readInt()

                val dragCoefficient = packet.readDouble()
                val acceleratorFactor = packet.readDouble()
                val maxSteamIntake = packet.readInt()
                val energyMultiplier = packet.readInt()

                minecraft.execute {
                    val config = AutoConfig.getConfigHolder<AGConfig>(AGConfig::class.java).config

                    config.waterBoilingPoint = waterBoilingPoint
                    config.steamExpansionRatio = steamExpansionRatio
                    config.idealWaterConsumption = idealWaterConsumption

                    config.dragCoefficient = dragCoefficient
                    config.acceleratorFactor = acceleratorFactor
                    config.maxSteamIntake = maxSteamIntake
                    config.energyMultiplier = energyMultiplier
                }
            },
        )
        ClientPlayConnectionEvents.DISCONNECT.register { _, _ ->
            AutoConfig.getConfigHolder<AGConfig>(AGConfig::class.java).load()
        }
    }
}
