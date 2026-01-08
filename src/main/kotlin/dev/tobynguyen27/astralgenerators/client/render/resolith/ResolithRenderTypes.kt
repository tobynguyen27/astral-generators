package dev.tobynguyen27.astralgenerators.client.render.resolith

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import net.minecraft.client.renderer.RenderStateShard.COLOR_DEPTH_WRITE
import net.minecraft.client.renderer.RenderStateShard.COLOR_WRITE
import net.minecraft.client.renderer.RenderStateShard.CULL
import net.minecraft.client.renderer.RenderStateShard.LEQUAL_DEPTH_TEST
import net.minecraft.client.renderer.RenderStateShard.NO_LAYERING
import net.minecraft.client.renderer.RenderStateShard.NO_LIGHTMAP
import net.minecraft.client.renderer.RenderStateShard.POSITION_COLOR_TEX_LIGHTMAP_SHADER
import net.minecraft.client.renderer.RenderStateShard.TRANSLUCENT_TRANSPARENCY
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard
import net.minecraft.client.renderer.RenderStateShard.VIEW_OFFSET_Z_LAYERING
import net.minecraft.client.renderer.RenderType

object ResolithRenderTypes {

    val SMALL_LASER_BEAM_TEXTURE = Identifier("textures/obj/laser.png")
    val BIG_LASER_BEAM_TEXTURE = Identifier("textures/obj/laser2.png")

    val LASER_MAIN_BEAM: RenderType =
        RenderType.create(
            "MiningLaserMainBeam",
            DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                .setTextureState(TextureStateShard(BIG_LASER_BEAM_TEXTURE, false, false))
                .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                .setLayeringState(NO_LAYERING)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setDepthTestState(LEQUAL_DEPTH_TEST)
                .setCullState(CULL)
                .setLightmapState(NO_LIGHTMAP)
                .setWriteMaskState(COLOR_DEPTH_WRITE)
                .createCompositeState(false),
        )

    val LASER_MAIN_CORE: RenderType =
        RenderType.create(
            "MiningLaserCoreBeam",
            DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                .setTextureState(TextureStateShard(SMALL_LASER_BEAM_TEXTURE, false, false))
                .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setDepthTestState(LEQUAL_DEPTH_TEST)
                .setCullState(CULL)
                .setLightmapState(NO_LIGHTMAP)
                .setWriteMaskState(COLOR_WRITE)
                .createCompositeState(false),
        )
}
