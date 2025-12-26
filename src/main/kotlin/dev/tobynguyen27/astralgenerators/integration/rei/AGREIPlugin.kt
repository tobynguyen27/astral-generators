package dev.tobynguyen27.astralgenerators.integration.rei

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerRecipe
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.integration.rei.categories.AssemblerCategory
import dev.tobynguyen27.astralgenerators.integration.rei.displays.AssemblerDisplay
import dev.tobynguyen27.astralgenerators.registry.AGBlocks
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.util.EntryStacks

class AGREIPlugin : REIClientPlugin {
    companion object {
        val ASSEMBLER: CategoryIdentifier<AssemblerDisplay> =
            CategoryIdentifier.of<AssemblerDisplay>(Identifier("assembler"))
    }

    override fun registerCategories(registry: CategoryRegistry) {
        registry.add(AssemblerCategory())
        registry.addWorkstations(ASSEMBLER, EntryStacks.of(AGBlocks.ASSEMBLER.get()))
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        registry.registerFiller(AssemblerRecipe::class.java, ::AssemblerDisplay)
    }
}
