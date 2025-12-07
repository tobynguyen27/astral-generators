package dev.tobynguyen27.astralgenerators.gui.widgets

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.contents.lang.Texts
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil.formatNumbers
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil.formatPercent
import dev.tobynguyen27.astralgenerators.utils.Identifier
import dev.tobynguyen27.codebebelib.math.MathHelper
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.util.Mth

class EnergyBar(val maxCapacity: () -> Long, val currentValue: () -> Long) : WWidget() {

    private val bg = Texture(Identifier("textures/gui/widgets/widget_energy_empty.png"))
    private val bar = Texture(Identifier("textures/gui/widgets/widget_energy_full.png"))

    override fun canResize(): Boolean {
        return true
    }

    override fun paint(matrices: PoseStack?, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        ScreenDrawing.texturedRect(matrices, x, y, getWidth(), getHeight(), bg, -0x1)

        val maxVal = maxCapacity().toFloat()
        var percent = MathHelper.clip(currentValue() / maxVal, 0f, 1f)

        val barMax = height
        percent = ((percent * barMax).toInt()) / barMax.toFloat()

        val barSize = (barMax * percent).toInt()
        if (barSize <= 0) return

        val left = x
        var top = y + height
        top -= barSize

        ScreenDrawing.texturedRect(
            matrices,
            left,
            top,
            width,
            barSize,
            bar.image(),
            bar.u1(),
            Mth.lerp(percent, bar.v2(), bar.v1()),
            bar.u2(),
            bar.v2(),
            -0x1,
        )
    }

    override fun addTooltip(information: TooltipBuilder) {
        val currentEnergy = currentValue()
        val maxEnergyCapacity = maxCapacity()

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
