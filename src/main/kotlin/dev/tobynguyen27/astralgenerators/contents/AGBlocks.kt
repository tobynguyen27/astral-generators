package dev.tobynguyen27.astralgenerators.contents

import com.tterrag.registrate.util.entry.BlockEntry
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.contents.blocks.CalvarCasing
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.Assembler
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerEntity
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarBlock
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarColor
import dev.tobynguyen27.astralgenerators.contents.registry.MaterialSetRegistry
import dev.tobynguyen27.astralgenerators.utils.RegistrateHelper
import dev.tobynguyen27.astralgenerators.utils.StringHelper
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.world.level.block.Blocks

object AGBlocks {

    val CALVAR_CASING =
        AstralGenerators.REGISTRATE.block(CalvarCasing.ID, ::CalvarCasing)
            .lang(StringHelper.toEnglishName(CalvarCasing.ID))
            .properties { FabricBlockSettings.copyOf(Blocks.IRON_BLOCK) }
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.get(),
                    prov.models().cubeAll(ctx.name, prov.modLoc("block/machines/side")),
                )
            }
            .simpleItem()
            .register()

    val CALVAR_BLOCK =
        MaterialSetRegistry.registerBlock(CalvarBlock.ID, ::CalvarBlock, CalvarColor.PRIMARY)
            .register()

    val ASSEMBLER: BlockEntry<Assembler> =
        RegistrateHelper.registerSimpleMachine(
                Assembler.ID,
                ::Assembler,
                Assembler.ACTIVE,
                Assembler.FACING,
            )
            .blockEntity { type, blockPos, blockState ->
                AssemblerEntity(type, blockPos, blockState)
            }
            .build()
            .register()

    fun register() {}
}
