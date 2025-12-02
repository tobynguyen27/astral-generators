package dev.tobynguyen27.astralgenerators.client

import dev.tobynguyen27.astralgenerators.contents.AGBlockEntities
import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlockEntityRenderer
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry

object AGBlockEntityRenderers {

    fun register() {
        BlockEntityRendererRegistry.register(
            AGBlockEntities.AM_ENTITY.get(),
            ::AMControllerBlockEntityRenderer,
        )
    }
}
