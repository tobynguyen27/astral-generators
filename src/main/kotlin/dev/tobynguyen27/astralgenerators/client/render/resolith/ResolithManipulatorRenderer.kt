package dev.tobynguyen27.astralgenerators.client.render.resolith

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlockEntity
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.codebebelib.render.RenderUtils
import dev.tobynguyen27.codebebelib.render.buffer.TransformingVertexConsumer
import dev.tobynguyen27.codebebelib.vec.Cuboid6
import java.util.OptionalDouble
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.minecraft.nbt.NbtUtils
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.shapes.Shapes

object ResolithManipulatorRenderer {

    val BOX_NO_DEPTH: RenderType =
        RenderType.create(
            Identifier("box_no_depth").toString(),
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            256,
            false,
            true,
            RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.POSITION_COLOR_SHADER)
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
                .createCompositeState(false),
        )
    val OUTLINE_TYPE: RenderType =
        RenderType.create(
            Identifier("outline").toString(),
            DefaultVertexFormat.POSITION_COLOR_NORMAL,
            VertexFormat.Mode.LINES,
            256,
            RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_LINES_SHADER)
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setCullState(RenderStateShard.NO_CULL)
                .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
                .setLineState(RenderStateShard.LineStateShard(OptionalDouble.of(5.0)))
                .createCompositeState(false),
        )

    fun renderSelection(context: WorldRenderContext, stack: ItemStack) {
        // TODO: Do not render if it to far from player

        val tag = stack.tag ?: return
        if (!tag.contains("SelectedPos")) return
        val targetPos: BlockPos = NbtUtils.readBlockPos(tag.getCompound("SelectedPos"))

        val level = context.world()
        val poseStack = context.matrixStack()

        val isValid = level.getBlockEntity(targetPos) is ResolithBlockEntity

        val state = level.getBlockState(targetPos)
        var shape = state.getShape(level, targetPos)
        if (shape.isEmpty) {
            shape = Shapes.block()
        }
        val box = Cuboid6(shape.bounds()).expand(0.005)

        val source = Minecraft.getInstance().renderBuffers().bufferSource()
        val camera = Minecraft.getInstance().gameRenderer.mainCamera
        val cameraPos = camera.position

        poseStack.pushPose()
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)
        poseStack.translate(targetPos.x.toDouble(), targetPos.y.toDouble(), targetPos.z.toDouble())

        RenderUtils.bufferCuboidSolid(
            TransformingVertexConsumer(source.getBuffer(BOX_NO_DEPTH), poseStack),
            box,
            (if (isValid) 0 else 1).toFloat(),
            (if (isValid) 1 else 0).toFloat(),
            0f,
            0.5f,
        )
        source.endBatch()

        RenderUtils.bufferCuboidOutline(
            TransformingVertexConsumer(source.getBuffer(OUTLINE_TYPE), poseStack),
            box,
            0f,
            0f,
            0f,
            1f,
        )
        source.endBatch()

        poseStack.popPose()
    }
}
