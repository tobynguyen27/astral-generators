package dev.tobynguyen27.astralgenerators.gui.widgets

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil.formatPercent
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.data.client.Texts
import dev.tobynguyen27.codebebelib.math.MathHelper
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.util.Mth

class TemperatureBar(val maxCapacity: () -> Long, val currentValue: () -> Long) : WWidget() {

    companion object {
        private val BG =
            Texture(Identifier("textures/gui/widgets/widget_temperature_bar_empty.png"))
        private val BAR =
            Texture(Identifier("textures/gui/widgets/widget_temperature_bar_full.png"))
    }

    override fun canResize(): Boolean {
        return true
    }

    override fun paint(matrices: PoseStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        ScreenDrawing.texturedRect(matrices, x, y, getWidth(), getHeight(), BG, -0x1)

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
            BAR.image(),
            BAR.u1(),
            Mth.lerp(percent, BAR.v2(), BAR.v1()),
            BAR.u2(),
            BAR.v2(),
            -0x1,
        )
    }

    override fun addTooltip(information: TooltipBuilder) {
        val currentTemperature = currentValue()
        val maxTemperature = maxCapacity()

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
