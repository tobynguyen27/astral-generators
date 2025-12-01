package dev.tobynguyen27.astralgenerators.contents.registry

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.builders.ItemBuilder
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.utils.StringHelper
import java.util.function.Supplier
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

object MaterialSetRegistry {

    fun <T : Block> registerBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
        color: Int,
    ): BlockBuilder<T, Registrate> =
        BlockRegistry.register(name, factory)
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    prov
                        .models()
                        .getBuilder(ctx.name)
                        .parent(prov.models().getExistingFile(prov.mcLoc("block/cube")))
                        .texture("particle", prov.modLoc("block/base/block"))
                        .texture("all", prov.modLoc("block/base/block"))
                        .element()
                        .from(0f, 0f, 0f)
                        .to(16f, 16f, 16f)
                        .allFaces { direction, builder ->
                            builder.texture("#all").cullface(direction).tintindex(0)
                        }
                        .end(),
                )
            }
            .color { Supplier { BlockColor { _, _, _, _ -> color } } }
            .item()
            .color { Supplier { ItemColor { _, _ -> color } } }
            .build()

    fun <T : Item> registerAlloyIngot(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.entry },
                    prov.modLoc("item/base/ingot"),
                    prov.modLoc("item/base/ingot_overlay"),
                    prov.modLoc("item/base/ingot_secondary"),
                )
            }
            .color { Supplier { ItemColor { _, _ -> color } } }

    fun <T : Item> registerIngot(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.entry },
                    prov.modLoc("item/base/ingot"),
                    prov.modLoc("item/base/ingot_overlay"),
                )
            }
            .color { Supplier { ItemColor { _, _ -> color } } }

    fun <T : Item> registerAlloyNugget(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.entry },
                    prov.modLoc("item/base/nugget"),
                    prov.modLoc("item/base/nugget_overlay"),
                    prov.modLoc("item/base/nugget_secondary"),
                )
            }
            .color { Supplier { ItemColor { _, _ -> color } } }

    fun <T : Item> registerNugget(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.entry },
                    prov.modLoc("item/base/nugget"),
                    prov.modLoc("item/base/nugget_overlay"),
                )
            }
            .color { Supplier { ItemColor { _, _ -> color } } }

    fun <T : Item> registerAlloyPlate(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.entry },
                    prov.modLoc("item/base/plate"),
                    prov.modLoc("item/base/plate_overlay"),
                    prov.modLoc("item/base/plate_secondary"),
                )
            }
            .color { Supplier { ItemColor { _, _ -> color } } }

    fun <T : Item> registerPlate(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.entry },
                    prov.modLoc("item/base/plate"),
                    prov.modLoc("item/base/plate_overlay"),
                )
            }
            .color { Supplier { ItemColor { _, _ -> color } } }

    fun <T : Item> registerAlloyRod(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.entry },
                    prov.modLoc("item/base/rod"),
                    prov.modLoc("item/base/rod_overlay"),
                    prov.modLoc("item/base/rod_secondary"),
                )
            }
            .color { Supplier { ItemColor { _, _ -> color } } }

    fun <T : Item> registerRod(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.entry },
                    prov.modLoc("item/base/rod"),
                    prov.modLoc("item/base/rod_overlay"),
                )
            }
            .color { Supplier { ItemColor { _, _ -> color } } }

    fun <T : Item> registerAlloyDust(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov ->
                prov.generated(
                    { ctx.entry },
                    prov.modLoc("item/base/dust"),
                    prov.modLoc("item/base/dust_secondary"),
                )
            }
            .color { Supplier { ItemColor { _, _ -> color } } }

    fun <T : Item> registerDust(
        name: String,
        factory: NonNullFunction<Item.Properties, T>,
        color: Int,
    ): ItemBuilder<T, Registrate> =
        REGISTRATE.item(name, factory)
            .lang(StringHelper.toEnglishName(name))
            .model { ctx, prov -> prov.generated({ ctx.entry }, prov.modLoc("item/base/dust")) }
            .color { Supplier { ItemColor { _, _ -> color } } }
}
