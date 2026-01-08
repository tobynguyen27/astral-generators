package dev.tobynguyen27.astralgenerators.contents.resolith

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.client.render.resolith.ResolithLaserRenderingQueue
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class ResolithBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context) :
    BlockEntityRenderer<ResolithBlockEntity> {

    override fun render(
        blockEntity: ResolithBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int,
    ) {
        if (blockEntity.connectedNodes.isNotEmpty()) ResolithLaserRenderingQueue.add(blockEntity)
    }
}
