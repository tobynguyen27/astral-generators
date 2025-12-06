package dev.tobynguyen27.astralgenerators.client.models

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry

object ModelProviders {

    fun register() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { _ -> BusModelProvider() }
        ModelLoadingRegistry.INSTANCE.registerResourceProvider { _ -> FluidHatchModelProvider() }
    }
}
