package dev.tobynguyen27.astralgenerators.contents.machines.am_controller

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.utils.Identifier
import dev.tobynguyen27.codebebelib.math.MathHelper
import dev.tobynguyen27.codebebelib.render.CCModel
import dev.tobynguyen27.codebebelib.render.CCRenderState
import dev.tobynguyen27.codebebelib.render.model.OBJParser
import dev.tobynguyen27.codebebelib.vec.Matrix4
import dev.tobynguyen27.codebebelib.vec.Vector3
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.level.block.state.properties.BlockStateProperties

class AMControllerBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context): BlockEntityRenderer<AMControllerBlockEntity> {

    // swap YZ is useful for blender model
    private val obj = OBJParser(Identifier("models/obj/darkmatter_orb.obj")).quads().ignoreMtl().swapYZ().parse()
    private val model = CCModel.combine(obj.values).backfacedCopy().computeNormals()

    override fun render(
        blockEntity: AMControllerBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val facing = blockEntity.blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)

        val mat = Matrix4(poseStack)
        mat.translate(0.5, 0.5, 0.5)

        mat.rotate(Math.toRadians(-facing.toYRot().toDouble()), Vector3.Y_POS)

        mat.translate(0.0, 2.0, -2.0)
        mat.scale(1.7)

        val ccrs = CCRenderState.instance()
        ccrs.reset()
        ccrs.computeLighting = false
        // TODO: Customize this
        ccrs.bind(RenderType.endGateway(), bufferSource)

        model.render(ccrs , mat)
    }
}
