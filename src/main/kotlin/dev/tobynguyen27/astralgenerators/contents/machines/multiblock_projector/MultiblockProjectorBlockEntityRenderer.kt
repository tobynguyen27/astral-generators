package dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.codebebelib.render.CCRenderState
import dev.tobynguyen27.codebebelib.vec.Matrix4
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class MultiblockProjectorBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context) :
    BlockEntityRenderer<MultiblockProjectorBlockEntity> {
    override fun render(
        blockEntity: MultiblockProjectorBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int,
    ) {
        val mat = Matrix4(poseStack)
        val ccrs = CCRenderState.instance()
        ccrs.reset()
    }
}
