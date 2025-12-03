package dev.tobynguyen27.astralgenerators.contents

import com.tterrag.registrate.util.entry.BlockEntry
import dev.tobynguyen27.astralgenerators.contents.blocks.BoilerCasing
import dev.tobynguyen27.astralgenerators.contents.blocks.CalvarCasing
import dev.tobynguyen27.astralgenerators.contents.blocks.HighMagneticCoil
import dev.tobynguyen27.astralgenerators.contents.blocks.MatrixCasing
import dev.tobynguyen27.astralgenerators.contents.blocks.SteamTurbineCasing
import dev.tobynguyen27.astralgenerators.contents.blocks.SteamTurbineVent
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

object AGBlocks {

    // Casings
    val CALVAR_CASING =
        BlockRegistry.registerCasingBlock(CalvarCasing.ID, ::CalvarCasing, true)
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.get(),
                    prov.models().cubeAll(ctx.name, prov.modLoc("block/machines/side")),
                )
            }
            .register()
    val BOILER_CASING =
        BlockRegistry.registerCasingBlock(BoilerCasing.ID, ::BoilerCasing).register()
    val STEAM_TURBINE_CASING =
        BlockRegistry.registerCasingBlock(SteamTurbineCasing.ID, ::SteamTurbineCasing).register()
    val MATRIX_CASING =
        BlockRegistry.registerCasingBlock(MatrixCasing.ID, ::MatrixCasing).register()

    // Coils
    val HIGH_MAGNETIC_COIL =
        BlockRegistry.registerCoilBlock(HighMagneticCoil.ID, ::HighMagneticCoil).register()

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

    val BOILER_CONTROLLER =
        BlockRegistry.registerControllerBlock(
                BoilerController.ID,
                ::BoilerController,
                "boiler_casing",
            )
            .blockEntity { type, blockPos, blockState ->
                BoilerControllerBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()
    val AM_CONTROLLER =
        BlockRegistry.registerControllerBlock(
                AMControllerBlock.ID,
                ::AMControllerBlock,
                "matrix_casing",
            )
            .blockEntity { type, blockPos, blockState ->
                AMControllerBlockEntity(type, blockPos, blockState)
            }
            .build()
            .register()

    val STEAM_TURBINE_VENT: BlockEntry<SteamTurbineVent> =
        BlockRegistry.register2LayersBlock(
                SteamTurbineVent.ID,
                ::SteamTurbineVent,
                Identifier("block/casings/steam_turbine_casing"),
                Identifier("block/steam_turbine_vent"),
            )
            .register()

    fun register() {}
}
