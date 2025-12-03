package dev.tobynguyen27.astralgenerators.contents

import com.tterrag.registrate.util.entry.BlockEntityEntry
import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerBlockEntity
import net.minecraft.core.Registry

object AGBlockEntities {

    val ASSEMBLER: BlockEntityEntry<AssemblerBlockEntity> =
        BlockEntityEntry.cast<AssemblerBlockEntity>(
            AGBlocks.ASSEMBLER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    val AM_CONTROLLER: BlockEntityEntry<AMControllerBlockEntity> =
        BlockEntityEntry.cast<AMControllerBlockEntity>(
            AGBlocks.AM_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    val BOILER_CONTROLLER: BlockEntityEntry<BoilerControllerBlockEntity> =
        BlockEntityEntry.cast<BoilerControllerBlockEntity>(
            AGBlocks.BOILER_CONTROLLER.getSibling(Registry.BLOCK_ENTITY_TYPE)
        )

    fun register() {}
}
