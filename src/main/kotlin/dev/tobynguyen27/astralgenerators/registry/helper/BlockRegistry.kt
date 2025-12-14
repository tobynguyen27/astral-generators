package dev.tobynguyen27.astralgenerators.registry.helper

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.contents.tags.AGBlockTags
import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import java.util.function.Supplier
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RotatedPillarBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Material
import net.minecraftforge.client.model.generators.ModelFile

object BlockRegistry {

    fun <T : RotatedPillarBlock> registerColumnBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> {
        return register(name, factory).simpleItem().blockstate { ctx, prov ->
            val name = ctx.name

            val activeModel =
                prov
                    .models()
                    .cubeColumn(
                        "${name}_active",
                        prov.modLoc("block/${name}_active"),
                        prov.modLoc("block/${name}_side"),
                    )
            val inactiveModel =
                prov
                    .models()
                    .cubeColumn(name, prov.modLoc("block/$name"), prov.modLoc("block/${name}_side"))

            prov
                .getVariantBuilder(ctx.entry)
                .partialState()
                .with(BlockStateProperties.LIT, true)
                .modelForState()
                .modelFile(activeModel)
                .addModel()
                .partialState()
                .with(BlockStateProperties.LIT, false)
                .modelForState()
                .modelFile(inactiveModel)
                .addModel()
        }
    }

    fun <T : Block> registerControllerBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
        casingBlockName: String,
    ): BlockBuilder<T, Registrate> {
        return register(name, factory)
            .simpleItem()
            .addLayer { Supplier { RenderType.translucent() } }
            .blockstate { ctx, prov ->
                val name = ctx.name

                val casingTexture = prov.modLoc("block/casings/$casingBlockName")
                val controllerTexture = prov.modLoc("block/machines/$name")
                val activeControllerTexture = prov.modLoc("block/machines/${name}_active")

                val modelFactory: (String, ResourceLocation) -> ModelFile = { suffix, fronTexture ->
                    prov
                        .models()
                        .withExistingParent(name + suffix, prov.modLoc("block/cube_2_layer/front"))
                        .texture("top_north", fronTexture)
                        .texture("all", casingTexture)
                }

                val inactiveModel = modelFactory("", controllerTexture)
                val activeModel = modelFactory("_active", activeControllerTexture)

                prov.horizontalBlock(ctx.get()) { state ->
                    if (state.getValue(BlockStateProperties.LIT)) {
                        return@horizontalBlock activeModel
                    }

                    return@horizontalBlock inactiveModel
                }
            }
    }

    fun <T : Block> registerSingleMachineBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> {
        return register(name, factory).simpleItem().blockstate { ctx, prov ->
            val name = ctx.name

            val frontTexture = Identifier("block/machines/$name")
            val activeFrontTexture = Identifier("block/machines/${name}_active")
            val topTexture = Identifier("block/machines/${name}_top")
            val activeTopTexture = Identifier("block/machines/${name}_top_active")

            val modelFactory: (String, ResourceLocation, ResourceLocation) -> ModelFile =
                { suffix, frontTexture, topTexture ->
                    val sideTexture = Identifier("block/machines/side")

                    prov.models().orientable(name + suffix, sideTexture, frontTexture, topTexture)
                }

            val inactiveModel = modelFactory("", frontTexture, topTexture)
            val activeModel = modelFactory("_active", activeFrontTexture, activeTopTexture)

            prov.horizontalBlock(ctx.get()) { state ->
                if (state.getValue(BlockStateProperties.LIT)) {
                    return@horizontalBlock activeModel
                }
                return@horizontalBlock inactiveModel
            }
        }
    }

    fun <T : Block> registerCasingBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
        withCustomBlockState: Boolean = false,
    ): BlockBuilder<T, Registrate> {
        return register(name, factory).simpleItem().tag(AGBlockTags.CASING).apply {
            if (!withCustomBlockState) {
                blockstate { ctx, prov ->
                    prov.simpleBlock(
                        ctx.get(),
                        prov.models().cubeAll(ctx.name, prov.modLoc("block/casings/$name")),
                    )
                }
            }
        }
    }

    fun <T : Block> registerCoilBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
        withCustomBlockState: Boolean = false,
    ): BlockBuilder<T, Registrate> {
        return register(name, factory).simpleItem().tag(AGBlockTags.COIL).apply {
            if (!withCustomBlockState) {
                blockstate { ctx, prov ->
                    prov.simpleBlock(
                        ctx.get(),
                        prov.models().cubeAll(ctx.name, prov.modLoc("block/coils/$name")),
                    )
                }
            }
        }
    }

    fun <T : Block> register2LayersBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
        bottomLayer: ResourceLocation,
        topLayer: ResourceLocation,
    ): BlockBuilder<T, Registrate> {
        return register(name, factory)
            .simpleItem()
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov
                        .models()
                        .withExistingParent(ctx.name, prov.modLoc("block/cube_2_layer/all"))
                        .texture("bot_all", bottomLayer)
                        .texture("top_all", topLayer),
                )
            }
            .addLayer { Supplier { RenderType.translucent() } }
    }

    fun <T : Block> register(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> {
        return REGISTRATE.block(name, factory)
            .lang(FormattingUtil.toEnglishName(name))
            .properties {
                FabricBlockSettings.of(Material.METAL)
                    .sound(SoundType.METAL)
                    .strength(5f)
                    .explosionResistance(6.0f)
                    .requiresCorrectToolForDrops()
            }
            .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL)
    }
}
