package dev.tobynguyen27.astralgenerators.gui.widgets

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import dev.tobynguyen27.astralgenerators.data.client.Texts
import io.github.cottonmc.cotton.gui.GuiDescription
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder
import io.github.cottonmc.cotton.gui.widget.WToggleButton
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.inventory.ContainerData

class IOButton(val type: Type, val valueIndex: Int) : WToggleButton() {
    companion object {
        private val ACTIVE_AUTO_IMPORT =
            Texture(Identifier("textures/gui/widgets/widget_io_icon_auto_import.png"))
        private val ACTIVE_AUTO_EXPORT =
            Texture(Identifier("textures/gui/widgets/widget_io_icon_auto_export.png"))
        private val INACTIVE = Texture(Identifier("textures/gui/widgets/widget_io_icon.png"))
    }

    private var properties: ContainerData? = null

    override fun paint(matrices: PoseStack?, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        this.offImage = INACTIVE

        if (type == Type.ONLY_IMPORT) {
            this.onImage = ACTIVE_AUTO_IMPORT
        } else {
            this.onImage = ACTIVE_AUTO_EXPORT
        }

        this.isOn = properties?.get(valueIndex) == 0
        super.paint(matrices, x, y, mouseX, mouseY)
    }

    override fun addTooltip(tooltip: TooltipBuilder) {
        val status =
            if (this.isOn) {
                TranslatableComponent(Texts.ENABLED).withStyle(ChatFormatting.GREEN)
            } else {
                TranslatableComponent(Texts.DISABLED).withStyle(ChatFormatting.RED)
            }

        val button =
            if (type == Type.ONLY_IMPORT) {
                TranslatableComponent(Texts.AUTO_IMPORT)
            } else {
                TranslatableComponent(Texts.AUTO_EXPORT)
            }

        tooltip.add(button.append(" is ").append(status))
    }

    override fun validate(host: GuiDescription) {
        if (properties == null) {
            properties = host.propertyDelegate
        }
        super.validate(host)
    }

    enum class Type {
        ONLY_EXPORT,
        ONLY_IMPORT;

        override fun toString(): String {
            return if (this == ONLY_IMPORT) {
                TranslatableComponent(Texts.AUTO_IMPORT).contents.toString()
            } else {
                TranslatableComponent(Texts.AUTO_EXPORT).contents.toString()
            }
        }
    }
}
