package dev.tobynguyen27.astralgenerators.models.ports.bus

import dev.tobynguyen27.astralgenerators.contents.ports.BusBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.BusModelClientData
import java.util.Random
import java.util.function.Supplier
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachedBlockView
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.block.model.ItemTransforms
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState

class BusBakedModel(
    val renderMaterial: RenderMaterial,
    val baseBasicSprite: TextureAtlasSprite,
    val baseAdvancedSprite: TextureAtlasSprite,
    val baseIndustrialSprite: TextureAtlasSprite,
    val inputOverlaySprite: TextureAtlasSprite,
    val outputOverlaySprite: TextureAtlasSprite,
) : BakedModel, FabricBakedModel {

    override fun emitBlockQuads(
        blockView: BlockAndTintGetter,
        state: BlockState,
        pos: BlockPos,
        randomSupplier: Supplier<Random>,
        context: RenderContext,
    ) {
        if (blockView !is RenderAttachedBlockView) return
        val attachment = blockView.getBlockEntityRenderAttachment(pos)
        if (attachment !is BusModelClientData) return

        var baseTierToUse: TextureAtlasSprite? = null

        if (attachment.casingBlock == null) {
            baseTierToUse =
                when (attachment.tier) {
                    BusBlockEntity.Tier.ADVANCED -> baseAdvancedSprite
                    BusBlockEntity.Tier.INDUSTRIAL -> baseIndustrialSprite
                    else -> baseBasicSprite
                }
        }

        var overlaySpriteToUse = inputOverlaySprite

        if (attachment.mode == BusBlockEntity.Mode.OUTPUT) {
            overlaySpriteToUse = outputOverlaySprite
        }

        val emitter = context.emitter

        for (d in Direction.entries) {
            if (attachment.casingBlock != null) {
                // Base
                val casingBlockState = attachment.casingBlock
                val casingModel =
                    Minecraft.getInstance().blockRenderer.getBlockModel(casingBlockState)
                val quads = casingModel.getQuads(casingBlockState, d, randomSupplier.get())

                baseTierToUse =
                    if (quads.isNotEmpty()) {
                        quads[0].sprite
                    } else {
                        casingModel.particleIcon
                    }
            }

            // Base
            emitter.square(d, 0f, 0f, 1f, 1f, 0f)
            emitter.spriteBake(0, baseTierToUse, MutableQuadView.BAKE_LOCK_UV)
            emitter.spriteColor(0, -1, -1, -1, -1)
            emitter.emit()

            // Overlay
            emitter.material(renderMaterial)
            emitter.square(d, 0f, 0f, 1f, 1f, -3e-4f)
            emitter.cullFace(d)
            emitter.spriteBake(0, overlaySpriteToUse, MutableQuadView.BAKE_LOCK_UV)
            emitter.spriteColor(0, -1, -1, -1, -1)
            emitter.emit()
        }
    }

    override fun emitItemQuads(
        stack: ItemStack,
        randomSupplier: Supplier<Random>,
        context: RenderContext,
    ) {
        var defaultTier = "basic"
        var defaultMode = "input"

        stack.tag?.apply {
            if (contains("tier")) defaultTier = getString("tier")
            if (contains("mode")) defaultMode = getString("mode")
        }

        val baseTierToUse =
            when (defaultTier) {
                "advanced" -> baseAdvancedSprite
                "industrial" -> baseIndustrialSprite
                else -> baseBasicSprite
            }
        val modeToUse =
            when (defaultMode) {
                "output" -> outputOverlaySprite
                else -> inputOverlaySprite
            }

        val emitter = context.emitter

        for (d in Direction.entries) {
            // Base
            emitter.square(d, 0f, 0f, 1f, 1f, 0f)
            emitter.spriteBake(0, baseTierToUse, MutableQuadView.BAKE_LOCK_UV)
            emitter.spriteColor(0, -1, -1, -1, -1)
            emitter.emit()

            // Overlay
            emitter.material(renderMaterial)
            emitter.square(d, 0f, 0f, 1f, 1f, -3e-4f)
            emitter.cullFace(d)
            emitter.spriteBake(0, modeToUse, MutableQuadView.BAKE_LOCK_UV)
            emitter.spriteColor(0, -1, -1, -1, -1)
            emitter.emit()
        }
    }

    override fun getQuads(state: BlockState?, side: Direction?, rand: Random): List<BakedQuad> {
        return emptyList()
    }

    override fun useAmbientOcclusion(): Boolean {
        return true
    }

    override fun isGui3d(): Boolean {
        return false
    }

    override fun usesBlockLight(): Boolean {
        return true
    }

    override fun isCustomRenderer(): Boolean {
        return false
    }

    override fun getParticleIcon(): TextureAtlasSprite {
        return baseBasicSprite
    }

    override fun getTransforms(): ItemTransforms {
        return ModelHelper.MODEL_TRANSFORM_BLOCK
    }

    override fun getOverrides(): ItemOverrides {
        return ItemOverrides.EMPTY
    }

    override fun isVanillaAdapter(): Boolean {
        return false
    }
}
