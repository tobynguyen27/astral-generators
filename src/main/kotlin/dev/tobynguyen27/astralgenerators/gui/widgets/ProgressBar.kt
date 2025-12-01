package dev.tobynguyen27.astralgenerators.gui.widgets

import dev.tobynguyen27.astralgenerators.contents.lang.Texts
import dev.tobynguyen27.astralgenerators.utils.StringHelper.calculateFormattedPercentage
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WBar
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation

class ProgressBar(bg: ResourceLocation, bar: ResourceLocation, field: Int, maxField: Int) :
    WBar(bg, bar, field, maxField, Direction.RIGHT) {
    override fun addTooltip(information: TooltipBuilder) {
        val current = properties.get(field)
        val max = properties.get(max)

        if (current == 0) {
            information.add(TranslatableComponent(Texts.IDLING).withStyle(ChatFormatting.GRAY))
            return
        }

        information.add(
            TranslatableComponent(Texts.PROGRESS)
                .withStyle(ChatFormatting.GRAY)
                .append(TextComponent(" ${calculateFormattedPercentage(current, max, "0")}%"))
        )
    }
}
