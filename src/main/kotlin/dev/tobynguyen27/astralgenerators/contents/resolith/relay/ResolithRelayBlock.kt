package dev.tobynguyen27.astralgenerators.contents.resolith.relay

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlock
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithType

abstract class ResolithRelayBlock(properties: Properties) : ResolithBlock(properties) {
    override fun getResolithType(): ResolithType = ResolithType.RELAY
}
