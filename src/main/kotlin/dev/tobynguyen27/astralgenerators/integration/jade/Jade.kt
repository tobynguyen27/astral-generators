package dev.tobynguyen27.astralgenerators.integration.jade

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlock
import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlockEntity
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiver
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.ResolithTransceiverBlockEntity
import dev.tobynguyen27.astralgenerators.integration.jade.components.ResolithComponent
import dev.tobynguyen27.astralgenerators.integration.jade.components.ResolithTransceiverComponent
import snownee.jade.api.IWailaClientRegistration
import snownee.jade.api.IWailaCommonRegistration
import snownee.jade.api.IWailaPlugin
import snownee.jade.api.WailaPlugin

@WailaPlugin
class Jade : IWailaPlugin {

    override fun register(registration: IWailaCommonRegistration) {
        registration.registerBlockDataProvider(ResolithComponent, ResolithBlockEntity::class.java)
        registration.registerBlockDataProvider(ResolithTransceiverComponent, ResolithTransceiverBlockEntity::class.java)
    }

    override fun registerClient(registration: IWailaClientRegistration) {
        registration.registerBlockComponent(ResolithComponent, ResolithBlock::class.java)
        registration.registerBlockComponent(ResolithTransceiverComponent, ResolithTransceiver::class.java)
    }
}
