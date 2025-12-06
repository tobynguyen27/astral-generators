package dev.tobynguyen27.astralgenerators.models.ports

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockModelClientData
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
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

class PortBlockBakedModel(
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
        if (attachment !is PortBlockModelClientData) return

        var baseTierToUse: TextureAtlasSprite? = null

        if (attachment.casingBlock == null) {
            baseTierToUse =
                when (attachment.tier) {
                    PortBlockSpecification.Tier.ADVANCED -> baseAdvancedSprite
                    PortBlockSpecification.Tier.INDUSTRIAL -> baseIndustrialSprite
                    else -> baseBasicSprite
                }
        } else {
            val casingBlockState = attachment.casingBlock
            val casingModel = Minecraft.getInstance().blockRenderer.getBlockModel(casingBlockState)
            baseTierToUse = casingModel.particleIcon
        }

        var overlaySpriteToUse = inputOverlaySprite

        if (attachment.mode == PortBlockSpecification.Mode.OUTPUT) {
            overlaySpriteToUse = outputOverlaySprite
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
        var defaultTier = PortBlockSpecification.Tier.BASIC.toString()
        var defaultMode = PortBlockSpecification.Mode.INPUT.toString()

        stack.tag?.apply {
            if (contains("tier")) defaultTier = getString("tier")
            if (contains("mode")) defaultMode = getString("mode")
        }

        val baseTierToUse =
            when (defaultTier) {
                PortBlockSpecification.Tier.ADVANCED.toString() -> baseAdvancedSprite
                PortBlockSpecification.Tier.INDUSTRIAL.toString() -> baseIndustrialSprite
                else -> baseBasicSprite
            }
        val modeToUse =
            when (defaultMode) {
                PortBlockSpecification.Mode.OUTPUT.toString() -> outputOverlaySprite
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
