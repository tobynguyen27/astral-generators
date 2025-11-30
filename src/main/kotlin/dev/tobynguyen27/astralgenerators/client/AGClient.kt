package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.codebebelib.BebeClient
import net.fabricmc.api.ClientModInitializer

class AGClient : ClientModInitializer {
    override fun onInitializeClient() {
        BebeClient.initialize()

        AGMenuScreens.register()
    }
}
