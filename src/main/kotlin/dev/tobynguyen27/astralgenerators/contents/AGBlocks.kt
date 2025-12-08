package dev.tobynguyen27.astralgenerators.contents

import com.tterrag.registrate.util.entry.BlockEntry
import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlock
import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.Assembler
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerController
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarBlock
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarColor
import dev.tobynguyen27.astralgenerators.contents.registry.BlockRegistry
import dev.tobynguyen27.astralgenerators.contents.registry.MaterialSetRegistry
import dev.tobynguyen27.astralgenerators.utils.Identifier
import net.minecraft.world.level.block.Block

object AGBlocks {

    // Casings
    val CALVAR_CASING =
        BlockRegistry.registerCasingBlock("calvar_casing", ::Block, true)
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.get(),
                    prov.models().cubeAll(ctx.name, prov.modLoc("block/machines/side")),
                )
            }
            .register()
    val BOILER_CASING = BlockRegistry.registerCasingBlock("boiler_casing", ::Block).register()
    val STEAM_TURBINE_CASING =
        BlockRegistry.registerCasingBlock("steam_turbine_casing", ::Block).register()
    val MATRIX_CASING = BlockRegistry.registerCasingBlock("matrix_casing", ::Block).register()
    val BASIC_MACHINE_CASING =
        BlockRegistry.registerCasingBlock("basic_machine_casing", ::Block).register()
    val ADVANCED_MACHINE_CASING =
        BlockRegistry.registerCasingBlock("advanced_machine_casing", ::Block).register()
    val INDUSTRIAL_MACHINE_CASING =
        BlockRegistry.registerCasingBlock("industrial_machine_casing", ::Block).register()
    val INDUSTRIAL_COMPOSTER_CASING =
        BlockRegistry.registerCasingBlock("industrial_composter_casing", ::Block).register()

    val HIGH_MAGNETIC_COIL =
        BlockRegistry.registerCoilBlock("high_magnetic_coil", ::Block).register()

    val STEAM_TURBINE_VENT: BlockEntry<Block> =
        BlockRegistry.register2LayersBlock(
                "steam_turbine_vent",
                ::Block,
                Identifier("block/casings/steam_turbine_casing"),
                Identifier("block/steam_turbine_vent"),
            )
            .register()

    // Material
    val CALVAR_BLOCK =
        MaterialSetRegistry.registerBlock(CalvarBlock.ID, ::CalvarBlock, CalvarColor.PRIMARY)
            .register()

    // Machines
    val ASSEMBLER: BlockEntry<Assembler> =
        BlockRegistry.registerSingleMachineBlock(Assembler.ID, ::Assembler)
            .blockEntity { type, blockPos, blockState ->
                AssemblerBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()

    val BOILER_CONTROLLER: BlockEntry<BoilerController> =
        BlockRegistry.registerControllerBlock(
                BoilerController.ID,
                ::BoilerController,
                BOILER_CASING.id.path,
            )
            .blockEntity { type, blockPos, blockState ->
                BoilerControllerBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val AM_CONTROLLER: BlockEntry<AMControllerBlock> =
        BlockRegistry.registerControllerBlock(
                AMControllerBlock.ID,
                ::AMControllerBlock,
                MATRIX_CASING.id.path,
            )
            .blockEntity { type, blockPos, blockState ->
                AMControllerBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()

    fun register() {
        AGPortBlocks.register()
    }
}
