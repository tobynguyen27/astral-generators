package dev.tobynguyen27.astralgenerators.contents.resolith.providers

object ResolithAttribute {

    private val IRON_RELAY = ConnectionStats(4, 8, 0)
    private val ELECTRUM_RELAY = ConnectionStats(8, 16, 0)
    private val IRON_TRANSCEIVER = ConnectionStats(2, 8, 1000)
    private val ELECTRUM_TRANSCEIVER = ConnectionStats(3, 16, 10000)

    fun getStats(type: ResolithType, tier: ResolithTier): ConnectionStats {
        return when (type) {
            ResolithType.RELAY ->
                when (tier) {
                    ResolithTier.IRON -> IRON_RELAY
                    else -> ELECTRUM_RELAY
                }
            else ->
                when (tier) {
                    ResolithTier.IRON -> IRON_TRANSCEIVER
                    else -> ELECTRUM_TRANSCEIVER
                }
        }
    }
}
