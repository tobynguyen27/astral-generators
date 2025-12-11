package dev.tobynguyen27.astralgenerators.client.model

import dev.tobynguyen27.astralgenerators.client.model.providers.BusModelProvider
import dev.tobynguyen27.astralgenerators.client.model.providers.EnergyHatchModelProvider
import dev.tobynguyen27.astralgenerators.client.model.providers.FluidHatchModelProvider
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry

object ModelProviders {

    private val PROVIDERS =
        setOf(::BusModelProvider, ::FluidHatchModelProvider, ::EnergyHatchModelProvider)

    fun register() {
        PROVIDERS.forEach { factory ->
            ModelLoadingRegistry.INSTANCE.registerResourceProvider { _ -> factory() }
        }
    }
}
