package dev.tobynguyen27.astralgenerators.client.render

import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlockEntityRenderer
import dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector.MultiblockProjectorBlockEntityRenderer
import dev.tobynguyen27.astralgenerators.registry.AGBlockEntities
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry

object AGBlockEntityRenderers {

    fun register() {
        BlockEntityRendererRegistry.register(
            AGBlockEntities.AM_CONTROLLER.get(),
            ::AMControllerBlockEntityRenderer,
        )
        BlockEntityRendererRegistry.register(
            AGBlockEntities.MULTIBLOCK_PROJECTOR.get(),
            ::MultiblockProjectorBlockEntityRenderer,
        )
    }
}
