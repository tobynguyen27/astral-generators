package dev.tobynguyen27.astralgenerators.integration.rei.categories

import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.integration.rei.AGREIPlugin
import dev.tobynguyen27.astralgenerators.integration.rei.displays.AssemblerDisplay
import dev.tobynguyen27.astralgenerators.registry.AGBlocks
import io.github.cottonmc.cotton.gui.widget.data.Color
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

class AssemblerCategory : DisplayCategory<AssemblerDisplay> {
    override fun getCategoryIdentifier(): CategoryIdentifier<out AssemblerDisplay> =
        AGREIPlugin.ASSEMBLER

    override fun getTitle(): Component =
        TranslatableComponent(AGBlocks.ASSEMBLER.get().descriptionId)

    override fun getIcon(): Renderer = EntryStacks.of(AGBlocks.ASSEMBLER.get())

    override fun setupDisplay(display: AssemblerDisplay, bounds: Rectangle): List<Widget> {
        val itemInputs =
            display.inputEntries.filter { ingredient ->
                ingredient.any { stack -> stack.type == VanillaEntryTypes.ITEM }
            }
        val fluidInput =
            display.inputEntries.filter { ingredient ->
                ingredient.any { stack -> stack.type == VanillaEntryTypes.FLUID }
            }

        val widgets = mutableListOf<Widget>()
        widgets.add(Widgets.createRecipeBase(bounds))

        val startPoint = Point(bounds.centerX - 58, bounds.centerY - 27)

        // Input
        repeat(9) {
            val x = it % 3
            val y = it / 3

            val slot =
                Widgets.createSlot(Point(startPoint.x - 10 + x * 18, startPoint.y + 1 + y * 18))

            if (it < itemInputs.size) {
                slot.entries(itemInputs[it])
            }

            widgets.add(slot.markInput())
        }

        // Fluid
        if (fluidInput.isNotEmpty()) {
            widgets.add(
                Widgets.createSlot(Point(startPoint.x - 8 + (3 * 18), startPoint.y + 1 + 18))
                    .entries(fluidInput[0])
                    .markInput()
            )
        }

        // Progress
        widgets.add(
            Widgets.createArrow(Point(startPoint.x + 64, startPoint.y + 19))
                .animationDurationTicks(display.recipe.duration.toDouble())
        )

        // Output
        widgets.add(Widgets.createResultSlotBackground(Point(startPoint.x + 95, startPoint.y + 19)))
        widgets.add(
            Widgets.createSlot(Point(startPoint.x + 95, startPoint.y + 19))
                .entries(display.outputEntries[0])
                .disableBackground()
                .markOutput()
        )

        // Info
        widgets.add(
            Widgets.createLabel(
                    Point(bounds.maxX - 5, bounds.y + 5),
                    TextComponent("${display.recipe.energyConsumption} E/t"),
                )
                .noShadow()
                .rightAligned()
                .color(Color.GRAY_DYE.toRgb())
        )

        widgets.add(
            Widgets.createLabel(
                    Point(bounds.maxX - 5, bounds.maxY - 12),
                    TextComponent(
                        "${FormattingUtil.convertTicksToSeconds(display.recipe.duration)}s"
                    ),
                )
                .noShadow()
                .rightAligned()
                .color(Color.GRAY_DYE.toRgb())
        )

        return widgets
    }
}
