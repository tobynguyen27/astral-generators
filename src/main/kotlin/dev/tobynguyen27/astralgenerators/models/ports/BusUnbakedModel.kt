package dev.tobynguyen27.astralgenerators.models.ports

import com.mojang.datafixers.util.Pair
import dev.tobynguyen27.astralgenerators.core.util.Identifier
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

class BusUnbakedModel : UnbakedModel {

    companion object {
        val BASE_BASIC = Identifier("block/casings/basic_machine_casing")
        val BASE_ADVANCED = Identifier("block/casings/advanced_machine_casing")
        val BASE_INDUSTRIAL = Identifier("block/casings/industrial_machine_casing")

        val OVERLAY_INPUT = Identifier("block/ports/overlay_item_hatch_input")
        val OVERLAY_OUTPUT = Identifier("block/ports/overlay_item_hatch_output")

        val MATERIALS =
            setOf(BASE_BASIC, BASE_ADVANCED, BASE_INDUSTRIAL, OVERLAY_INPUT, OVERLAY_OUTPUT).map {
                Material(InventoryMenu.BLOCK_ATLAS, it)
            }
    }

    override fun getDependencies(): Collection<ResourceLocation> {
        return emptyList()
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
        val baseBasicSprite = spriteGetter.apply(MATERIALS[0])
        val baseAdvancedSprite = spriteGetter.apply(MATERIALS[1])
        val baseIndustrialSprite = spriteGetter.apply(MATERIALS[2])

        val inputOverlaySprite = spriteGetter.apply(MATERIALS[3])
        val outputOverlaySprite = spriteGetter.apply(MATERIALS[4])

        val cutoutMaterial =
            RendererAccess.INSTANCE.renderer!!.materialFinder()
                .blendMode(0, BlendMode.CUTOUT_MIPPED)
                .find()

        return PortBlockBakedModel(
            cutoutMaterial,
            baseBasicSprite,
            baseAdvancedSprite,
            baseIndustrialSprite,
            inputOverlaySprite,
            outputOverlaySprite,
        )
    }
}
