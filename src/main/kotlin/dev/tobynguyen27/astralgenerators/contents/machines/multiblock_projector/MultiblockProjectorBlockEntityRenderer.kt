package dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexFormat
import dev.tobynguyen27.astralgenerators.multiblocks.ShapeMatcher
import dev.tobynguyen27.astralgenerators.multiblocks.pool.MultiblocksPool
import dev.tobynguyen27.astralgenerators.utils.Identifier
import dev.tobynguyen27.codebebelib.math.MathHelper
import dev.tobynguyen27.codebebelib.render.RenderUtils
import dev.tobynguyen27.codebebelib.render.buffer.TransformingVertexConsumer
import dev.tobynguyen27.codebebelib.utils.ClientUtils
import dev.tobynguyen27.codebebelib.vec.Cuboid6
import java.util.OptionalDouble
import kotlin.math.abs
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.properties.BlockStateProperties

class MultiblockProjectorBlockEntityRenderer(ctx: BlockEntityRendererProvider.Context) :
    BlockEntityRenderer<MultiblockProjectorBlockEntity> {

    companion object {

        var CURRENT_MULTIBLOCK: ResourceLocation? = null

        private val INVALID_OUTLINE =
            RenderType.create(
                Identifier("invalid_outline").toString(),
                DefaultVertexFormat.POSITION_COLOR_NORMAL,
                VertexFormat.Mode.LINES,
                256,
                RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.RENDERTYPE_LINES_SHADER)
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
                    .setLineState(RenderStateShard.LineStateShard(OptionalDouble.of(4.0)))
                    .createCompositeState(false),
            )
    }

    override fun render(
        blockEntity: MultiblockProjectorBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int,
    ) {
        if (CURRENT_MULTIBLOCK == null) {
            return
        }

        val definition = MultiblocksPool.DEFINITIONS[CURRENT_MULTIBLOCK] ?: return

        val facing = blockEntity.blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)
        val level = blockEntity.level!! // Renderer is only activated when a block entity placed
        val player = Minecraft.getInstance().player
        val invalidBlockPositions = mutableListOf<BlockPos>()

        for ((blockPos, blockState) in definition.getBlocks().entries) {

            val rotatedBlockPos = ShapeMatcher.toWorldPos(BlockPos.ZERO, facing.opposite, blockPos)
            val worldPos = blockEntity.blockPos.offset(rotatedBlockPos)

            if (level.isEmptyBlock(worldPos)) {

                if (player != null && worldPos.distToCenterSqr(player.eyePosition) < (2 * 2))
                    continue

                poseStack.pushPose()

                poseStack.translate(
                    rotatedBlockPos.x.toDouble(),
                    rotatedBlockPos.y.toDouble(),
                    rotatedBlockPos.z.toDouble(),
                )
                poseStack.translate(0.5, 0.5, 0.5)
                poseStack.scale(0.8f, 0.8f, 0.8f)
                poseStack.translate(-0.5, -0.5, -0.5)

                val rotatedBlockState =
                    when (facing) {
                        Direction.NORTH -> blockState.rotate(Rotation.CLOCKWISE_180)
                        Direction.EAST -> blockState.rotate(Rotation.COUNTERCLOCKWISE_90)
                        Direction.WEST -> blockState.rotate(Rotation.CLOCKWISE_90)
                        else -> blockState.rotate(Rotation.NONE)
                    }

                Minecraft.getInstance()
                    .blockRenderer
                    .renderSingleBlock(
                        rotatedBlockState,
                        poseStack,
                        bufferSource,
                        LightTexture.FULL_BRIGHT,
                        packedOverlay,
                    )

                poseStack.popPose()
            } else if (!level.getBlockState(worldPos).`is`(blockState.block)) {
                invalidBlockPositions.add(rotatedBlockPos)
            }
        }

        if (bufferSource is MultiBufferSource.BufferSource) {
            bufferSource.endBatch()
        }

        if (invalidBlockPositions.isNotEmpty()) {
            invalidBlockPositions.forEach { blockPos ->
                poseStack.pushPose()

                poseStack.translate(blockPos.x + 0.5, blockPos.y + 0.5, blockPos.z + 0.5)
                val builder =
                    TransformingVertexConsumer(bufferSource.getBuffer(INVALID_OUTLINE), poseStack)

                val time = ClientUtils.getRenderTime()
                val duration = 25

                // PingPong
                val linearProgress = 1.0 - abs((time % duration - duration / 2) / (duration / 2))
                val easeAnim = MathHelper.sin((linearProgress * Math.PI) / 2.0)
                val scale = MathHelper.interpolate(0.1, 1.0, easeAnim) / 2
                val box = Cuboid6().expand(scale)
                RenderUtils.bufferCuboidOutline(builder, box, 1f, 0f, 0f, 1f)

                poseStack.popPose()
            }
        }
    }
}
