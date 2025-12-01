package dev.tobynguyen27.astralgenerators.contents.machines.am_controller

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class AMControllerBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context): BlockEntityRenderer<AMControllerBlockEntity> {
    override fun render(
        blockEntity: AMControllerBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {}
}
