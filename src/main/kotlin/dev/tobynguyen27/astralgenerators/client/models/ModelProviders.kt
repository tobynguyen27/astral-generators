package dev.tobynguyen27.astralgenerators.client.models

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry

object ModelProviders {

    fun register() {
        ModelLoadingRegistry.INSTANCE.apply {
            registerResourceProvider { _ -> BusModelProvider() }
            registerResourceProvider { _ -> FluidHatchModelProvider() }
            registerResourceProvider { _ -> EnergyHatchModelProvider() }
        }
    }
}
