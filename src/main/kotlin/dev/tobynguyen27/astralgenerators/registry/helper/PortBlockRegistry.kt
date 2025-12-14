package dev.tobynguyen27.astralgenerators.registry.helper

import com.tterrag.registrate.Registrate
import com.tterrag.registrate.builders.BlockBuilder
import com.tterrag.registrate.util.nullness.NonNullFunction
import dev.tobynguyen27.astralgenerators.AstralGenerators
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.registry.helper.BlockRegistry.register
import java.util.function.Supplier
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.NonNullList
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.client.model.generators.ModelFile

object PortBlockRegistry {

    fun <T : Block> registerBasicInputBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> {
        return registerPortBlock(
            name,
            factory,
            PortBlockSpecification.Tier.BASIC,
            PortBlockSpecification.Mode.INPUT,
        )
    }

    fun <T : Block> registerAdvancedInputBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> {
        return registerPortBlock(
            name,
            factory,
            PortBlockSpecification.Tier.ADVANCED,
            PortBlockSpecification.Mode.INPUT,
        )
    }

    fun <T : Block> registerIndustrialInputBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> {
        return registerPortBlock(
            name,
            factory,
            PortBlockSpecification.Tier.INDUSTRIAL,
            PortBlockSpecification.Mode.INPUT,
        )
    }

    fun <T : Block> registerBasicOutputBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> {
        return registerPortBlock(
            name,
            factory,
            PortBlockSpecification.Tier.BASIC,
            PortBlockSpecification.Mode.OUTPUT,
        )
    }

    fun <T : Block> registerAdvancedOutputBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> {
        return registerPortBlock(
            name,
            factory,
            PortBlockSpecification.Tier.ADVANCED,
            PortBlockSpecification.Mode.OUTPUT,
        )
    }

    fun <T : Block> registerIndustrialOutputBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
    ): BlockBuilder<T, Registrate> {
        return registerPortBlock(
            name,
            factory,
            PortBlockSpecification.Tier.INDUSTRIAL,
            PortBlockSpecification.Mode.OUTPUT,
        )
    }

    private fun <T : Block> registerPortBlock(
        name: String,
        factory: NonNullFunction<BlockBehaviour.Properties, T>,
        tier: PortBlockSpecification.Tier,
        mode: PortBlockSpecification.Mode,
    ): BlockBuilder<T, Registrate> {
        return register(name, factory)
            .addLayer { Supplier { RenderType.cutout() } }
            .item { block, properties ->
                object : BlockItem(block, properties) {
                    override fun fillItemCategory(
                        category: CreativeModeTab,
                        items: NonNullList<ItemStack>,
                    ) {
                        if (this.allowdedIn(category)) {
                            val stack = ItemStack(this)

                            val nbt = stack.orCreateTag
                            nbt.putString("mode", mode.toString())
                            nbt.putString("tier", tier.toString())
                            items.add(stack)
                        }
                    }
                }
            }
            .model { ctx, prov -> {} }
            .tab { AstralGenerators.ITEM_GROUP }
            .build()
            .blockstate { ctx, prov ->
                prov.simpleBlock(
                    ctx.entry,
                    ModelFile.UncheckedModelFile(Identifier("block/${ctx.name}")),
                )
            }
    }
}
