package dev.tobynguyen27.astralgenerators.contents.ports.buses

import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockEntity
import dev.tobynguyen27.astralgenerators.contents.ports.PortBlockSpecification
import dev.tobynguyen27.astralgenerators.core.util.IInventory
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class BusBlockEntity(
    blockEntityType: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
    size: Int,
    tier: PortBlockSpecification.Tier,
    mode: PortBlockSpecification.Mode,
    casingBlock: BlockState?,
) :
    PortBlockEntity(blockEntityType, blockPos, blockState, tier, mode, casingBlock),
    IInventory,
    MenuProvider,
    WorldlyContainer,
    RenderAttachmentBlockEntity,
    PropertyDelegateHolder {

    private val items: NonNullList<ItemStack> = NonNullList.withSize(size, ItemStack.EMPTY)

    val containerWrapper: InventoryStorage = InventoryStorage.of(this, null)
    val storage: List<SingleSlotStorage<ItemVariant>> = containerWrapper.slots

    override fun getItems(): NonNullList<ItemStack> {
        return items
    }

    override fun setChanged() {
        super<PortBlockEntity>.setChanged()
    }

    override fun saveAdditional(tag: CompoundTag) {
        ContainerHelper.saveAllItems(tag, items)

        super.saveAdditional(tag)
    }

    override fun load(tag: CompoundTag) {
        ContainerHelper.loadAllItems(tag, items)

        super.load(tag)
    }

    override fun getPropertyDelegate(): ContainerData {
        return containerData
    }

    override fun getSlotsForFace(side: Direction): IntArray {
        return IntArray(containerSize) { it }
    }

    override fun canPlaceItemThroughFace(
        index: Int,
        itemStack: ItemStack,
        direction: Direction?,
    ): Boolean {
        return mode == PortBlockSpecification.Mode.INPUT
    }

    override fun canTakeItemThroughFace(
        index: Int,
        stack: ItemStack,
        direction: Direction,
    ): Boolean {
        return mode == PortBlockSpecification.Mode.OUTPUT
    }
}
