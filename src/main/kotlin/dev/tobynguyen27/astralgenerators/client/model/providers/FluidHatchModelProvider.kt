package dev.tobynguyen27.astralgenerators.client.model.providers

import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.advanced.AdvancedFluidInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.basic.BasicFluidInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.input.industrial.IndustrialFluidInputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.advanced.AdvancedFluidOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.basic.BasicFluidOutputHatch
import dev.tobynguyen27.astralgenerators.contents.ports.hatches.fluid.output.industrial.IndustrialFluidOutputHatch
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.models.ports.FluidHatchUnbakedModel
import net.fabricmc.fabric.api.client.model.ModelProviderContext
import net.fabricmc.fabric.api.client.model.ModelResourceProvider
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.client.resources.model.UnbakedModel
import net.minecraft.resources.ResourceLocation

class FluidHatchModelProvider : ModelResourceProvider {

    companion object {

        private val HATCHES =
            setOf(
                BasicFluidInputHatch.ID,
                AdvancedFluidInputHatch.ID,
                IndustrialFluidInputHatch.ID,
                BasicFluidOutputHatch.ID,
                AdvancedFluidOutputHatch.ID,
                IndustrialFluidOutputHatch.ID,
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
            return FluidHatchUnbakedModel()
        }

        return null
    }
}
