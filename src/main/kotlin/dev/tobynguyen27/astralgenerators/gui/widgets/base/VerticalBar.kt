package dev.tobynguyen27.astralgenerators.gui.widgets.base

import com.mojang.blaze3d.vertex.PoseStack
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.util.Mth

abstract class VerticalBar() : WWidget() {
    abstract val barBackgroundTexture: Texture
    abstract val barForegroundTexture: Texture
    abstract val capacity: () -> Long
    abstract val currentValue: () -> Long

    override fun paint(matrices: PoseStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        ScreenDrawing.texturedRect(matrices, x, y, width, height, barBackgroundTexture, -0x1)

        val maxCap = capacity()
        if (maxCap == 0L) return

        val rawRatio = (currentValue() / maxCap.toFloat()).coerceIn(0f, 1f)

        val barSize = (height * rawRatio).toInt()

        if (barSize <= 0) return

        val uvRatio = barSize.toFloat() / height.toFloat()

        ScreenDrawing.texturedRect(
            matrices,
            x,
            y + height - barSize,
            width,
            barSize,
            barForegroundTexture.image(),
            barForegroundTexture.u1(),
            Mth.lerp(uvRatio, barForegroundTexture.v2(), barForegroundTexture.v1()),
            barForegroundTexture.u2(),
            barForegroundTexture.v2(),
            -0x1,
        )
    }

    override fun canResize(): Boolean = true
}
