package dev.tobynguyen27.astralgenerators.contents

import com.tterrag.registrate.util.entry.BlockEntityEntry
import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerEntity
import net.minecraft.core.Registry

object AGBlockEntities {

    val ASSEMBLER_ENTITY =
        BlockEntityEntry.cast<AssemblerEntity>(
            AGBlocks.ASSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    val AM_ENTITY =
        BlockEntityEntry.cast<AMControllerBlockEntity>(
            AGBlocks.AM_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    fun register() {}
}
