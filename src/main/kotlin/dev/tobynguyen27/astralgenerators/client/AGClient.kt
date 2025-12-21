package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.astralgenerators.client.model.ModelProviders
import dev.tobynguyen27.astralgenerators.client.network.MultiblockSyncHandler
import dev.tobynguyen27.astralgenerators.client.render.AGBlockEntityRenderers
import dev.tobynguyen27.astralgenerators.client.screen.AGMenuScreens
import dev.tobynguyen27.astralgenerators.data.config.ConfigSyncHandler
import dev.tobynguyen27.codebebelib.BebeClient
import net.fabricmc.api.ClientModInitializer

class AGClient : ClientModInitializer {
    override fun onInitializeClient() {
        BebeClient.initialize()

        AGBlockEntityRenderers.register()
        AGMenuScreens.register()
        ModelProviders.register()

        MultiblockSyncHandler.register()
        ConfigSyncHandler.initializeClient()
    }
}
