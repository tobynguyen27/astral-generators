package dev.tobynguyen27.astralgenerators.gui.widgets

import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil.formatPercent
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.data.client.Texts
import dev.tobynguyen27.astralgenerators.gui.widgets.base.VerticalBar
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

class TemperatureBar(override val capacity: () -> Long, override val currentValue: () -> Long) :
    VerticalBar() {

    companion object {
        private val BG =
            Texture(Identifier("textures/gui/widgets/widget_temperature_bar_empty.png"))
        private val FG = Texture(Identifier("textures/gui/widgets/widget_temperature_bar_full.png"))
    }

    override val barBackgroundTexture: Texture = BG
    override val barForegroundTexture: Texture = FG

    override fun addTooltip(information: TooltipBuilder) {
        val currentTemperature = currentValue()
        val maxTemperature = capacity()

        information.add(
            TranslatableComponent(Texts.TEMPERATURE).withStyle(ChatFormatting.DARK_AQUA)
        )
        information.add(
            TranslatableComponent(Texts.MAX_TEMPERATURE)
                .withStyle(ChatFormatting.GOLD)
                .append(" ")
                .append(
                    TextComponent(FormattingUtil.formatTemperature(maxTemperature))
                        .withStyle(ChatFormatting.GRAY)
                )
        )
        information.add(
            TranslatableComponent(Texts.CURRENT_TEMPERATURE)
                .withStyle(ChatFormatting.GOLD)
                .append(
                    TextComponent(
                            " ${
                                FormattingUtil.formatTemperature(currentTemperature)
                            } (${
                                formatPercent(
                                    currentTemperature,
                                    maxTemperature,
                                )
                            })"
                        )
                        .withStyle(ChatFormatting.GRAY)
                )
        )
    }
}
