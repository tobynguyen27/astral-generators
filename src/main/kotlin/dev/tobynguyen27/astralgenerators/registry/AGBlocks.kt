package dev.tobynguyen27.astralgenerators.registry

import com.tterrag.registrate.util.entry.BlockEntry
import dev.tobynguyen27.astralgenerators.contents.blocks.FireboxCasing
import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlock
import dev.tobynguyen27.astralgenerators.contents.machines.am_controller.AMControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.Assembler
import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerController
import dev.tobynguyen27.astralgenerators.contents.machines.boiler_controller.BoilerControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector.MultiblockProjector
import dev.tobynguyen27.astralgenerators.contents.machines.multiblock_projector.MultiblockProjectorBlockEntity
import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineController
import dev.tobynguyen27.astralgenerators.contents.machines.steam_turbine_controller.SteamTurbineControllerBlockEntity
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarBlock
import dev.tobynguyen27.astralgenerators.contents.materials.calvar.CalvarColor
import dev.tobynguyen27.astralgenerators.contents.resolith.providers.ResolithType
import dev.tobynguyen27.astralgenerators.contents.resolith.relay.electrum.ElectrumResolithRelay
import dev.tobynguyen27.astralgenerators.contents.resolith.relay.electrum.ElectrumResolithRelayBlockEntity
import dev.tobynguyen27.astralgenerators.contents.resolith.relay.iron.IronResolithRelay
import dev.tobynguyen27.astralgenerators.contents.resolith.relay.iron.IronResolithRelayBlockEntity
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.electrum.ElectrumResolithTransceiver
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.electrum.ElectrumResolithTransceiverBlockEntity
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.iron.IronResolithTransceiver
import dev.tobynguyen27.astralgenerators.contents.resolith.transceiver.iron.IronResolithTransceiverBlockEntity
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.registry.helper.BlockRegistry
import dev.tobynguyen27.astralgenerators.registry.helper.MaterialSetRegistry
import net.minecraft.world.level.block.Block

object AGBlocks {

    val IRON_RESOLITH_RELAY =
        BlockRegistry.registerResolith(
                "iron_resolith_relay",
                ::IronResolithRelay,
                ResolithType.RELAY,
            )
            .blockEntity(::IronResolithRelayBlockEntity)
            .build()
            .register()
    val ELECTRUM_RESOLITH_RELAY =
        BlockRegistry.registerResolith(
                "electrum_resolith_relay",
                ::ElectrumResolithRelay,
                ResolithType.RELAY,
            )
            .blockEntity(::ElectrumResolithRelayBlockEntity)
            .build()
            .register()
    val IRON_RESOLITH_TRANSCEIVER =
        BlockRegistry.registerResolith(
                "iron_resolith_transceiver",
                ::IronResolithTransceiver,
                ResolithType.TRANSCEIVER,
            )
            .blockEntity(::IronResolithTransceiverBlockEntity)
            .build()
            .register()
    val ELECTRUM_RESOLITH_TRANSCEIVER =
        BlockRegistry.registerResolith(
                "electrum_resolith_transceiver",
                ::ElectrumResolithTransceiver,
                ResolithType.TRANSCEIVER,
            )
            .blockEntity(::ElectrumResolithTransceiverBlockEntity)
            .build()
            .register()

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
    val FIREBOX_CASING =
        BlockRegistry.registerColumnBlock(FireboxCasing.ID, ::FireboxCasing).register()
    val PIPE_CASING = BlockRegistry.registerCasingBlock("pipe_casing", ::Block).register()

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

    val MULTIBLOCK_PROJECTOR: BlockEntry<MultiblockProjector> =
        BlockRegistry.register(MultiblockProjector.ID, ::MultiblockProjector)
            .simpleItem()
            .blockEntity { type, blockPos, blockState ->
                MultiblockProjectorBlockEntity(type, blockPos, blockState)
            }
            .build()
            .blockstate { ctx, prov ->
                val name = ctx.name
                val frontTexture = Identifier("block/machines/$name")
                val sideTexture = Identifier("block/machines/side")

                prov.horizontalBlock(
                    ctx.get(),
                    prov.models().orientable(name, sideTexture, frontTexture, sideTexture),
                )
            }
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
    val STEAM_TURBINE_CONTROLLER: BlockEntry<SteamTurbineController> =
        BlockRegistry.registerControllerBlock(
                SteamTurbineController.ID,
                ::SteamTurbineController,
                STEAM_TURBINE_CASING.id.path,
            )
            .blockEntity(::SteamTurbineControllerBlockEntity)
            .build()
            .register()

    fun register() {
        AGPortBlocks.register()
    }
}
