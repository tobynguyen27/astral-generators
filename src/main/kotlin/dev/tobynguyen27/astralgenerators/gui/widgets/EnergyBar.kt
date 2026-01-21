package dev.tobynguyen27.astralgenerators.gui.widgets

import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil.formatNumbers
import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil.formatPercent
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.data.client.Texts
import dev.tobynguyen27.astralgenerators.gui.widgets.base.VerticalBar
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

class EnergyBar(override val capacity: () -> Long, override val currentValue: () -> Long) :
    VerticalBar() {

    companion object {
        private val BG = Texture(Identifier("textures/gui/widgets/widget_energy_empty.png"))
        private val FG = Texture(Identifier("textures/gui/widgets/widget_energy_full.png"))
    }

    override val barBackgroundTexture: Texture = BG
    override val barForegroundTexture: Texture = FG

    override fun addTooltip(information: TooltipBuilder) {
        val currentEnergy = currentValue()
        val maxEnergyCapacity = capacity()

        information.add(TranslatableComponent(Texts.ENERGY).withStyle(ChatFormatting.DARK_AQUA))
        information.add(
            TranslatableComponent(Texts.CAPACITY)
                .withStyle(ChatFormatting.GOLD)
                .append(
                    TextComponent(" ${formatNumbers(maxEnergyCapacity)} E")
                        .withStyle(ChatFormatting.GRAY)
                )
        )
        information.add(
            TranslatableComponent(Texts.STORED)
                .withStyle(ChatFormatting.GOLD)
                .append(
                    TextComponent(
                            " ${
                                formatNumbers(
                                    currentEnergy
                                )
                            } E (${
                                formatPercent(
                                    currentEnergy,
                                    maxEnergyCapacity,
                                )
                            })"
                        )
                        .withStyle(ChatFormatting.GRAY)
                )
        )
    }
}
