package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.astralgenerators.client.models.ModelProviders
import dev.tobynguyen27.astralgenerators.client.networking.MultiblocksSync
import dev.tobynguyen27.codebebelib.BebeClient
import net.fabricmc.api.ClientModInitializer

class AGClient : ClientModInitializer {
    override fun onInitializeClient() {
        BebeClient.initialize()

        AGBlockEntityRenderers.register()
        AGMenuScreens.register()
        ModelProviders.register()

        MultiblocksSync.register()
    }
}
