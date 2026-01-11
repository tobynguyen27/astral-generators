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

            packet.writeInt(config.ironRelayMaxConnections)
            packet.writeInt(config.ironRelayRange)
            packet.writeInt(config.electrumRelayMaxConnections)
            packet.writeInt(config.electrumRelayRange)

            packet.writeInt(config.ironTransceiverMaxConnections)
            packet.writeInt(config.ironTransceiverRange)
            packet.writeInt(config.ironTransceiverTransferRate)

            packet.writeInt(config.electrumTransceiverMaxConnections)
            packet.writeInt(config.electrumTransceiverRange)
            packet.writeInt(config.electrumTransceiverTransferRate)

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

                val ironRelayMaxConnections = packet.readInt()
                val ironRelayRange = packet.readInt()
                val electrumRelayMaxConnections = packet.readInt()
                val electrumRelayRange = packet.readInt()

                val ironTransceiverMaxConnections = packet.readInt()
                val ironTransceiverRange = packet.readInt()
                val ironTransceiverTransferRate = packet.readInt()

                val electrumTransceiverMaxConnections = packet.readInt()
                val electrumTransceiverRange = packet.readInt()
                val electrumTransceiverTransferRate = packet.readInt()

                minecraft.execute {
                    val config = AutoConfig.getConfigHolder<AGConfig>(AGConfig::class.java).config

                    config.waterBoilingPoint = waterBoilingPoint
                    config.steamExpansionRatio = steamExpansionRatio
                    config.idealWaterConsumption = idealWaterConsumption

                    config.dragCoefficient = dragCoefficient
                    config.acceleratorFactor = acceleratorFactor
                    config.maxSteamIntake = maxSteamIntake
                    config.energyMultiplier = energyMultiplier

                    config.ironRelayMaxConnections = ironRelayMaxConnections
                    config.ironRelayRange = ironRelayRange
                    config.electrumRelayMaxConnections = electrumRelayMaxConnections
                    config.electrumRelayRange = electrumRelayRange

                    config.ironTransceiverMaxConnections = ironTransceiverMaxConnections
                    config.ironTransceiverRange = ironTransceiverRange
                    config.ironTransceiverTransferRate = ironTransceiverTransferRate

                    config.electrumTransceiverMaxConnections = electrumTransceiverMaxConnections
                    config.electrumTransceiverRange = electrumTransceiverRange
                    config.electrumTransceiverTransferRate = electrumTransceiverTransferRate
                }
            },
        )
        ClientPlayConnectionEvents.DISCONNECT.register { _, _ ->
            AutoConfig.getConfigHolder<AGConfig>(AGConfig::class.java).load()
        }
    }
}
