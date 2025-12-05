package dev.tobynguyen27.astralgenerators.contents.ports

import dev.tobynguyen27.astralgenerators.utils.IInventory
import java.util.Locale.getDefault
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class BusBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
    size: Int,
    val tier: Tier,
    val mode: Mode,
    var casingBlock: BlockState?,
) :
    BlockEntity(blockEntityType, blockPos, blockState),
    IInventory,
    MenuProvider,
    WorldlyContainer,
    RenderAttachmentBlockEntity {

    private val items: NonNullList<ItemStack> = NonNullList.withSize(size, ItemStack.EMPTY)

    override fun getItems(): NonNullList<ItemStack> {
        return items
    }

    override fun setChanged() {
        super<BlockEntity>.setChanged()
    }

    override fun saveAdditional(tag: CompoundTag) {
        ContainerHelper.saveAllItems(tag, items)

        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        ContainerHelper.loadAllItems(tag, items)

        super.load(tag)
    }

    override fun getDisplayName(): Component {
        return TranslatableComponent(blockState.block.descriptionId)
    }

    override fun getSlotsForFace(side: Direction): IntArray {
        return IntArray(containerSize) { it }
    }

    override fun canPlaceItemThroughFace(
        index: Int,
        itemStack: ItemStack,
        direction: Direction?,
    ): Boolean {
        return mode == Mode.INPUT
    }

    override fun canTakeItemThroughFace(
        index: Int,
        stack: ItemStack,
        direction: Direction,
    ): Boolean {
        return mode == Mode.OUTPUT
    }

    override fun getRenderAttachmentData(): BusModelClientData {
        return BusModelClientData(mode, tier, casingBlock)
    }

    enum class Mode {
        INPUT,
        OUTPUT;

        override fun toString(): String {
            return super.toString().lowercase(getDefault())
        }
    }

    enum class Tier {
        BASIC,
        ADVANCED,
        INDUSTRIAL;

        override fun toString(): String {
            return super.toString().lowercase(getDefault())
        }
    }
}
