package dev.tobynguyen27.astralgenerators.client.render.resolith

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.contents.resolith.network.ResolithNode
import java.util.*

object ResolithLaserRenderingQueue {
    val resolithNodes: Queue<ResolithNode> = LinkedList<ResolithNode>()

    fun render(poseStack: PoseStack) {
        if (resolithNodes.isNotEmpty()) {
            ResolithRenderUtils.drawLasers(resolithNodes, poseStack)
        }
    }

    fun add(node: ResolithNode) = resolithNodes.add(node)
}
