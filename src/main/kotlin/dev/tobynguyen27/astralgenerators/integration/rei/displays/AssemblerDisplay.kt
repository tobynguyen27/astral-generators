package dev.tobynguyen27.astralgenerators.integration.rei.displays

import dev.tobynguyen27.astralgenerators.contents.machines.assembler.AssemblerRecipe
import dev.tobynguyen27.astralgenerators.integration.rei.AGREIPlugin
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.util.EntryIngredients

class AssemblerDisplay(val recipe: AssemblerRecipe) : Display {
    override fun getInputEntries(): List<EntryIngredient> {
        val inputs =
            recipe.itemInputs
                .map { input ->
                    val stacks =
                        input.item.items.map { stack ->
                            val copy = stack.copy()
                            copy.count = input.amount
                            copy
                        }
                    EntryIngredients.ofItemStacks(stacks)
                }
                .toMutableList()

        inputs.add(
            EntryIngredients.of(recipe.fluidInput.fluid.fluid, recipe.fluidInput.fluid.amount)
        )

        return inputs
    }

    override fun getOutputEntries(): List<EntryIngredient> {
        return listOf(EntryIngredients.of(recipe.itemOutput.item))
    }

    override fun getCategoryIdentifier(): CategoryIdentifier<*> {
        return AGREIPlugin.ASSEMBLER
    }
}
