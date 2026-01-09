package dev.tobynguyen27.astralgenerators.client.render.resolith

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithBlockEntity
import java.util.*

object ResolithLaserRenderingQueue {
    val resolithNodes: Queue<ResolithBlockEntity> = LinkedList<ResolithBlockEntity>()

    fun render(poseStack: PoseStack) {
        if (resolithNodes.isNotEmpty()) {
            ResolithRenderUtils.drawLasers(resolithNodes, poseStack)
        }
    }

    fun add(node: ResolithBlockEntity) = resolithNodes.add(node)
}
