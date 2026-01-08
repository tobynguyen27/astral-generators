package dev.tobynguyen27.astralgenerators.client.render.resolith

import dev.tobynguyen27.astralgenerators.client.render.resolith.ResolithManipulatorRenderer.renderSelection
import dev.tobynguyen27.astralgenerators.registry.AGItems
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.minecraft.client.Minecraft

object ResolithRenderers {

    fun register() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register {
            ResolithLaserRenderingQueue.render(it.matrixStack())
        }

        WorldRenderEvents.END.register {
            Minecraft.getInstance().player?.let { player ->
                val itemInMainHand = player.mainHandItem
                if (itemInMainHand.`is`(AGItems.RESOLITH_MANIPULATOR.get())) {
                    renderSelection(it, itemInMainHand)
                }
            }
        }
    }
}
