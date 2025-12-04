package dev.tobynguyen27.astralgenerators.client.models

import dev.tobynguyen27.astralgenerators.models.ports.bus.BusUnbakedModel
import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.resources.model.UnbakedModel
import net.minecraft.resources.ResourceLocation

class BusModelProvider : ModelResourceProvider {

    companion object {
        private val BASIC_INPUT_BUS_MODEL = Identifier("block/basic_input_bus")
        private val ADVANCED_INPUT_BUS_MODEL = Identifier("block/advanced_input_bus")
        private val INDUSTRIAL_INPUT_BUS_MODEL = Identifier("block/industrial_input_bus")
    }

    override fun loadModelResource(p0: ResourceLocation, p1: ModelProviderContext): UnbakedModel? {
        if (p0 == BASIC_INPUT_BUS_MODEL || p0 == ADVANCED_INPUT_BUS_MODEL || p0 == INDUSTRIAL_INPUT_BUS_MODEL) {
            return BusUnbakedModel()
        }

        return null
    }
}
