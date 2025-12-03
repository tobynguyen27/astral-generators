package dev.tobynguyen27.astralgenerators.contents.ports

import dev.tobynguyen27.astralgenerators.utils.IInventory
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class BusBlockEntity(
    type: BlockEntityType<*>,
    blockPos: BlockPos,
    blockState: BlockState,
    containerSize: Int,
) : BlockEntity(type, blockPos, blockState), IInventory, MenuProvider {

    private val items: NonNullList<ItemStack> = NonNullList.withSize(containerSize, ItemStack.EMPTY)

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
}
