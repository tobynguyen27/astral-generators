package dev.tobynguyen27.astralgenerators.gui.widgets

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.contents.lang.Texts
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil.formatBuckets
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil.formatPercent
import dev.tobynguyen27.astralgenerators.utils.FormattingUtil.toEnglishName
import dev.tobynguyen27.astralgenerators.utils.Identifier
import dev.tobynguyen27.codebebelib.math.MathHelper
import dev.tobynguyen27.codebebelib.render.CCRenderState
import dev.tobynguyen27.codebebelib.render.RenderUtils
import dev.tobynguyen27.codebebelib.vec.Cuboid6
import dev.tobynguyen27.codebebelib.vec.Matrix4
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.github.fabricators_of_create.porting_lib.util.FluidStack
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LightTexture
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent

class FluidBar(
    val fluidVariant: () -> FluidVariant,
    val maxCapacity: () -> Long,
    val currentValue: () -> Long,
) : WWidget() {

    private val bg = Identifier("textures/gui/widgets/widget_fluid_empty.png")

    private val bar = Identifier("textures/gui/widgets/widget_fluid_empty.png")

    override fun canResize(): Boolean = true

    override fun paint(matrices: PoseStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        // Draw tank
        ScreenDrawing.texturedRect(matrices, x, y, this.getWidth(), this.getHeight(), this.bg, -1)

        val currentValue = this.currentValue()

        // Draw fluid
        if (fluidVariant().isBlank || currentValue <= 0) {
            return
        }

        val maxValue = maxCapacity().toDouble()
        val percent = MathHelper.clip(currentValue / maxValue, 0.0, 1.0)

        val startX = x + width - 1
        val startY = y + height - 1
        val endX = x + 1
        val endY = y + 1

        val bound =
            Cuboid6(
                startX.toDouble(),
                startY.toDouble(),
                0.0,
                endX.toDouble(),
                endY.toDouble(),
                0.0,
            )

        val ccr = CCRenderState.instance()
        ccr.reset()
        ccr.brightness = LightTexture.FULL_BRIGHT

        val mat = Matrix4(matrices)

        val renderType = RenderUtils.getFluidRenderType()
        val fluidVariant = fluidVariant()
        val fluidStack = FluidStack(fluidVariant, currentValue.toLong(), fluidVariant.nbt)

        val bufferSource = Minecraft.getInstance().renderBuffers().bufferSource()

        RenderUtils.renderFluidCuboid(
            ccr,
            mat,
            renderType,
            bufferSource,
            fluidStack,
            bound,
            percent,
            16.0,
        )

        bufferSource.endBatch()
    }

    override fun addTooltip(information: TooltipBuilder) {
        val current = currentValue()
        val max = maxCapacity()

        val fluidName = toEnglishName(fluidVariant().fluid.registryName.path)

        information.add(TextComponent(fluidName).withStyle(ChatFormatting.DARK_AQUA))
        information.add(
            TranslatableComponent(Texts.CAPACITY)
                .withStyle(ChatFormatting.GOLD)
                .append(TextComponent(" ${formatBuckets(max)}").withStyle(ChatFormatting.GRAY))
        )

        information.add(
            TranslatableComponent(Texts.STORED)
                .withStyle(ChatFormatting.GOLD)
                .append(
                    TextComponent(
                            " ${formatBuckets(
                            current
                        )} (${formatPercent(
                            current,
                            max,
                        )})"
                        )
                        .withStyle(ChatFormatting.GRAY)
                )
        )
    }
}
