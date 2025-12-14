package dev.tobynguyen27.astralgenerators.client.model.providers

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.advanced.AdvancedEnergyInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.basic.BasicEnergyInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.input.industrial.IndustrialEnergyInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.advanced.AdvancedEnergyOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.basic.BasicEnergyOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.energy.output.industrial.IndustrialEnergyOutputHatch
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.models.ports.EnergyHatchUnbakedModel
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.client.resources.model.UnbakedModel
import net.minecraft.resources.ResourceLocation

class EnergyHatchModelProvider : ModelResourceProvider {

    companion object {

        private val HATCHES =
            setOf(
                BasicEnergyInputHatch.ID,
                AdvancedEnergyInputHatch.ID,
                IndustrialEnergyInputHatch.ID,
                BasicEnergyOutputHatch.ID,
                AdvancedEnergyOutputHatch.ID,
                IndustrialEnergyOutputHatch.ID,
            )

        private val HATCH_MODELS =
            HATCHES.flatMap { bus ->
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
        if (resourceId in HATCH_MODELS) {
            return EnergyHatchUnbakedModel()
        }

        return null
    }
}
