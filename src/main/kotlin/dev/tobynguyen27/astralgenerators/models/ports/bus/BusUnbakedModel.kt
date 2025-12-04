package dev.tobynguyen27.astralgenerators.models.ports.bus

import com.mojang.datafixers.util.Pair
import dev.tobynguyen27.astralgenerators.utils.Identifier
import java.util.function.Function
import net.fabricmc.fabric.api.renderer.v1.RendererAccess
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.client.resources.model.Material
import net.minecraft.client.resources.model.ModelBakery
import net.minecraft.client.resources.model.ModelState
import net.minecraft.client.resources.model.UnbakedModel
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.InventoryMenu

class BusUnbakedModel() : UnbakedModel {

    companion object {
        val DEFAULT_CASING_ID = Identifier("block/machines/side")
        val INPUT_OVERLAY_ID = Identifier("block/ports/overlay_item_hatch_input")
        val OUTPUT_OVERLAY_ID = Identifier("block/ports/overlay_item_hatch_output")

        val MATERIALS =
            listOf<Material>(
                Material(InventoryMenu.BLOCK_ATLAS, DEFAULT_CASING_ID),
                Material(InventoryMenu.BLOCK_ATLAS, INPUT_OVERLAY_ID),
                Material(InventoryMenu.BLOCK_ATLAS, OUTPUT_OVERLAY_ID),
            )
    }

    override fun getMaterials(
        modelGetter: Function<ResourceLocation, UnbakedModel>,
        missingTextureErrors: Set<Pair<String, String>>,
    ): Collection<Material> {
        return MATERIALS
    }

    override fun bake(
        modelBakery: ModelBakery,
        spriteGetter: Function<Material, TextureAtlasSprite>,
        transform: ModelState,
        location: ResourceLocation,
    ): BakedModel {
        val cutoutMaterial =
            RendererAccess.INSTANCE.renderer!!.materialFinder()
                .blendMode(0, BlendMode.CUTOUT_MIPPED)
                .find()
        val defaultCasingSprite = spriteGetter.apply(MATERIALS[0])
        val inputOverlaySprite = spriteGetter.apply(MATERIALS[1])
        val outputOverlaySprite = spriteGetter.apply(MATERIALS[2])

        return BusBakedModel(
            cutoutMaterial,
            defaultCasingSprite,
            inputOverlaySprite,
            outputOverlaySprite,
        )
    }

    override fun getDependencies(): Collection<ResourceLocation> {
        return emptyList()
    }
}
