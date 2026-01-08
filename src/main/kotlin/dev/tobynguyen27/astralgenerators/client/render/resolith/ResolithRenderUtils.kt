package dev.tobynguyen27.astralgenerators.client.render.resolith

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Matrix4f
import com.mojang.math.Vector3f
import dev.tobynguyen27.astralgenerators.contents.resolith.network.ResolithNode
import java.util.Queue
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.world.level.block.entity.BlockEntity

object ResolithRenderUtils {

    fun drawLasers(nodes: Queue<ResolithNode>, poseStack: PoseStack) {
        val client = Minecraft.getInstance()
        val buffer = client.renderBuffers().bufferSource()
        val projectedView = client.gameRenderer.mainCamera.position

        val beamBuilder = buffer.getBuffer(ResolithRenderTypes.LASER_MAIN_BEAM)
        val coreBuilder = buffer.getBuffer(ResolithRenderTypes.LASER_MAIN_CORE)

        while (nodes.isNotEmpty()) {
            val node = nodes.remove()
            val level = node.level ?: continue
            val gameTime = level.gameTime
            val v = gameTime * 0.04
            val startBlock = node.blockPos

            poseStack.pushPose()

            poseStack.translate(
                startBlock.x - projectedView.x,
                startBlock.y - projectedView.y,
                startBlock.z - projectedView.z,
            )

            val positionMatrix = poseStack.last().pose()

            val startLaser = Vector3f(0.5f, .5f, 0.5f)

            for (target in node.connectedNodes) {
                val endBlock = target
                val diffX = endBlock.x + .5f - startBlock.x
                val diffY = endBlock.y + .5f - startBlock.y
                val diffZ = endBlock.z + .5f - startBlock.z
                val endLaser = Vector3f(diffX, diffY, diffZ)

                drawLaser(
                    beamBuilder,
                    positionMatrix,
                    endLaser,
                    startLaser,
                    1f,
                    1f,
                    0f,
                    1f,
                    0.0175f,
                    v,
                    v + diffY * 4.5,
                    node,
                )

                drawLaser(
                    coreBuilder,
                    positionMatrix,
                    endLaser,
                    startLaser,
                    1f,
                    1f,
                    0f,
                    1f,
                    0.0125f,
                    v,
                    v + diffY * 1.5,
                    node,
                )
            }

            poseStack.popPose()
        }

        buffer.endBatch(ResolithRenderTypes.LASER_MAIN_BEAM)
        buffer.endBatch(ResolithRenderTypes.LASER_MAIN_CORE)
    }

    fun drawLaser(
        builder: VertexConsumer,
        positionMatrix: Matrix4f,
        from: Vector3f,
        to: Vector3f,
        r: Float,
        g: Float,
        b: Float,
        alpha: Float,
        thickness: Float,
        v1: Double,
        v2: Double,
        be: BlockEntity,
    ) {
        val adjustedVec = adjustBeamToEyes(from, to, be)
        adjustedVec.mul(thickness)

        val p1: Vector3f = from.copy()
        p1.add(adjustedVec)
        val p2: Vector3f = from.copy()
        p2.sub(adjustedVec)
        val p3: Vector3f = to.copy()
        p3.add(adjustedVec)
        val p4: Vector3f = to.copy()
        p4.sub(adjustedVec)

        addVertexToBuilder(builder, positionMatrix, p1, r, g, b, alpha, 1f, v1.toFloat())
        addVertexToBuilder(builder, positionMatrix, p3, r, g, b, alpha, 1f, v2.toFloat())
        addVertexToBuilder(builder, positionMatrix, p4, r, g, b, alpha, 0f, v2.toFloat())
        addVertexToBuilder(builder, positionMatrix, p2, r, g, b, alpha, 0f, v1.toFloat())
    }

    fun adjustBeamToEyes(from: Vector3f, to: Vector3f, be: BlockEntity): Vector3f {
        val player = Minecraft.getInstance().player ?: return Vector3f()
        val P =
            Vector3f(
                (player.x - be.blockPos.x).toFloat(),
                (player.eyeY - be.blockPos.y).toFloat(),
                (player.z - be.blockPos.z).toFloat(),
            )

        val PS: Vector3f = from.copy()
        PS.sub(P)
        val SE: Vector3f = to.copy()
        SE.sub(from)

        val adjustedVec: Vector3f = PS.copy()
        adjustedVec.cross(SE)
        adjustedVec.normalize()
        return adjustedVec
    }

    fun addVertexToBuilder(
        builder: VertexConsumer,
        positionMatrix: Matrix4f,
        position: Vector3f,
        r: Float,
        g: Float,
        b: Float,
        alpha: Float,
        v1: Float,
        v2: Float,
    ) {
        builder
            .vertex(positionMatrix, position.x(), position.y(), position.z())
            .color(r, g, b, alpha)
            .uv(v1, v2)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(15728880)
            .endVertex()
    }
}
