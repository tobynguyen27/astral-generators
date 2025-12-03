package dev.tobynguyen27.astralgenerators.contents.machines.am_controller

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexFormat
import dev.tobynguyen27.astralgenerators.utils.Identifier
import dev.tobynguyen27.codebebelib.render.CCModel
import dev.tobynguyen27.codebebelib.render.CCRenderState
import dev.tobynguyen27.codebebelib.render.RenderUtils
import dev.tobynguyen27.codebebelib.render.model.OBJParser
import dev.tobynguyen27.codebebelib.utils.ClientUtils
import dev.tobynguyen27.codebebelib.vec.Matrix4
import dev.tobynguyen27.codebebelib.vec.Vector3
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.world.level.block.state.properties.BlockStateProperties

class AMControllerBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context) :
    BlockEntityRenderer<AMControllerBlockEntity> {

    companion object {
        private val STAR_TEXTURE = Identifier("textures/obj/star.png")
        private val SPACE_TEXTURE = Identifier("textures/obj/space.png")

        val STAR_RENDER_TYPE: RenderType.CompositeRenderType =
            RenderType.create(
                Identifier("star").toString(),
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                false,
                false,
                RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_EYES_SHADER)
                    .setTextureState(RenderStateShard.TextureStateShard(STAR_TEXTURE, false, false))
                    .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                    .setCullState(RenderStateShard.CULL)
                    .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                    .createCompositeState(true),
            )

        val SPACE_RENDER_TYPE: RenderType.CompositeRenderType =
            RenderType.create(
                Identifier("space").toString(),
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                false,
                false,
                RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_EYES_SHADER)
                    .setTextureState(
                        RenderStateShard.TextureStateShard(SPACE_TEXTURE, false, false)
                    )
                    .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                    .setCullState(RenderStateShard.CULL)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .createCompositeState(true),
            )
    }

    private val starObj = OBJParser(Identifier("models/obj/star.obj")).quads().ignoreMtl().parse()
    private val starModel = CCModel.combine(starObj.values)

    private val spaceObj = OBJParser(Identifier("models/obj/space.obj")).quads().ignoreMtl().parse()
    private val spaceModel = CCModel.combine(spaceObj.values)

    override fun render(
        blockEntity: AMControllerBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int,
    ) {
        val facing = blockEntity.blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)
        val time = ClientUtils.getRenderTime() + RenderUtils.getTimeOffset(blockEntity.blockPos)

        val ccrs = CCRenderState.instance()
        ccrs.reset()
        ccrs.computeLighting = false
        ccrs.brightness = LightTexture.FULL_BRIGHT
        ccrs.overlay = OverlayTexture.NO_OVERLAY

        val mat = Matrix4(poseStack)
        mat.translate(0.5, 0.5, 0.5)

        mat.rotate(Math.toRadians(-facing.toYRot().toDouble()), Vector3.Y_POS)

        mat.translate(0.0, 2.0, -2.0)

        // White dwarf
        renderStar(ccrs, mat.copy(), bufferSource, time)
        renderSpace(ccrs, mat.copy(), bufferSource, time)

    }

    private fun renderStar(ccrs: CCRenderState,mat: Matrix4, bufferSource: MultiBufferSource, time: Double) {
        mat.scale(0.2)
        mat.rotate(time * 0.02, Vector3.Y_POS)
        mat.rotate(time * 0.01, Vector3.X_POS)

        ccrs.bind(STAR_RENDER_TYPE, bufferSource, mat)
        starModel.render(ccrs)
    }

    private fun renderSpace(ccrs: CCRenderState,mat: Matrix4, bufferSource: MultiBufferSource, time: Double) {
        mat.scale(-0.028, 0.028, 0.028)
        mat.rotate(-time * 0.005, Vector3.Y_POS)
        mat.rotate(time * 0.005, Vector3.Z_POS)

        ccrs.bind(SPACE_RENDER_TYPE, bufferSource, mat)
        spaceModel.render(ccrs)
    }
}
