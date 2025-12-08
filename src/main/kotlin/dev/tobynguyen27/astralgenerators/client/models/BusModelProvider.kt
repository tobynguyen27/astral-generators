package dev.tobynguyen27.astralgenerators.client.models

import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.advanced.AdvancedInputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.basic.BasicInputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.input.industrial.IndustrialInputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.advanced.AdvancedOutputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.basic.BasicOutputBus
import dev.tobynguyen27.astralgenerators.contents.ports.buses.output.industrial.IndustrialOutputBus
import dev.tobynguyen27.astralgenerators.models.ports.BusUnbakedModel
import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.client.resources.model.UnbakedModel
import net.minecraft.resources.ResourceLocation

class BusModelProvider : ModelResourceProvider {

    companion object {

        private val BUSES =
            setOf<String>(
                BasicInputBus.ID,
                BasicOutputBus.ID,
                AdvancedInputBus.ID,
                AdvancedOutputBus.ID,
                IndustrialInputBus.ID,
                IndustrialOutputBus.ID,
            )

        private val BUS_MODELS =
            BUSES.flatMap { bus ->
                setOf(
                    ModelResourceLocation(Identifier("block/$bus"), ""),
                    ModelResourceLocation(Identifier("item/$bus"), "inventory"),
                )
            }
    }

    override fun loadModelResource(
        resourceId: ResourceLocation,
        ctx: ModelProviderContext,
    ): UnbakedModel? {
        if (resourceId in BUS_MODELS) {
            return BusUnbakedModel()
        }

        return null
    }
}
