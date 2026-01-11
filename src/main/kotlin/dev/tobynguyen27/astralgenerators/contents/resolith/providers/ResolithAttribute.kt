package dev.tobynguyen27.astralgenerators.contents.resolith.providers

import dev.tobynguyen27.astralgenerators.data.config.ConfigHolder.CONFIG

object ResolithAttribute {
    fun getStats(type: ResolithType, tier: ResolithTier): ConnectionStats {
        return when (type) {
            ResolithType.RELAY ->
                when (tier) {
                    ResolithTier.IRON ->
                        ConnectionStats(CONFIG.ironRelayMaxConnections, CONFIG.ironRelayRange, 0)
                    else ->
                        ConnectionStats(
                            CONFIG.electrumRelayMaxConnections,
                            CONFIG.electrumRelayRange,
                            0,
                        )
                }
            else ->
                when (tier) {
                    ResolithTier.IRON ->
                        ConnectionStats(
                            CONFIG.ironTransceiverMaxConnections,
                            CONFIG.ironTransceiverRange,
                            CONFIG.ironTransceiverTransferRate.toLong(),
                        )
                    else ->
                        ConnectionStats(
                            CONFIG.electrumTransceiverMaxConnections,
                            CONFIG.electrumTransceiverRange,
                            CONFIG.electrumTransceiverTransferRate.toLong(),
                        )
                }
        }
    }
}
