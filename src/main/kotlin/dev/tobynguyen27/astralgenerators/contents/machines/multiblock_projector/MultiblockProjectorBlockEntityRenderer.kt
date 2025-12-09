package dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class MultiblockProjectorBlockEntityRenderer(
    ctx: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<MultiblockProjectorBlockEntity> {
    override fun render(
        blockEntity: MultiblockProjectorBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
    }
}
