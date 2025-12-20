package dev.tobynguyen27.astralgenerators.gui.widgets

import com.mojang.blaze3d.vertex.PoseStack
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import io.github.cottonmc.cotton.gui.GuiDescription
import io.github.cottonmc.cotton.gui.widget.WToggleButton
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.minecraft.world.inventory.ContainerData

class PowerButton(val valueIndex: Int) : WToggleButton(ACTIVE, INACTIVE) {
    companion object {
        private val ACTIVE = Texture(Identifier("textures/gui/widgets/widget_on_icon.png"))
        private val INACTIVE = Texture(Identifier("textures/gui/widgets/widget_off_icon.png"))
    }

    private var properties: ContainerData? = null

    override fun paint(matrices: PoseStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        this.isOn = properties?.get(valueIndex) == 0
        super.paint(matrices, x, y, mouseX, mouseY)
    }

    override fun validate(host: GuiDescription) {
        if (properties == null) {
            properties = host.propertyDelegate
        }
        super.validate(host)
    }
}
