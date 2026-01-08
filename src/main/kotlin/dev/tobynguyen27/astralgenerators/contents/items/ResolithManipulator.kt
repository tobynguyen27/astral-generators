package dev.tobynguyen27.astralgenerators.contents.items

import dev.tobynguyen27.astralgenerators.contents.resolith.ResolithNode
import dev.tobynguyen27.astralgenerators.data.client.Texts
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class ResolithManipulator(properties: Item.Properties) : Item(properties) {
    companion object {
        const val ID = "resolith_manipulator"
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val clickedPos = context.clickedPos
        val player = context.player!!
        val manipulator = context.itemInHand

        if (level.isClientSide) return InteractionResult.SUCCESS

        val clickedBlockEntity = level.getBlockEntity(clickedPos)

        if (clickedBlockEntity !is ResolithNode) return InteractionResult.PASS

        if (player.isShiftKeyDown) {
            storeSelection(manipulator, clickedPos)
            player.displayClientMessage(
                TranslatableComponent(Texts.NODE_SELECTED, clickedPos.toShortString())
                    .withStyle(ChatFormatting.GREEN),
                false,
            )
            return InteractionResult.SUCCESS
        }

        if (!hasSelection(manipulator)) {
            player.displayClientMessage(
                TranslatableComponent(Texts.NODE_SELECTION_REQUIRED).withStyle(ChatFormatting.RED),
                false,
            )
            return InteractionResult.FAIL
        }

        val selectedPos = getSelection(manipulator)!!

        if (selectedPos == clickedPos) {
            player.displayClientMessage(
                TranslatableComponent(Texts.NODE_BIND_SELF).withStyle(ChatFormatting.YELLOW),
                false,
            )
            return InteractionResult.FAIL
        }

        val selectedBlockEntity = level.getBlockEntity(selectedPos)
        if (selectedBlockEntity !is ResolithNode) {
            player.displayClientMessage(
                TranslatableComponent(Texts.NODE_SELECTED_NO_EXISTS).withStyle(ChatFormatting.RED),
                false,
            )
            clearSelection(manipulator)
            return InteractionResult.FAIL
        }

        if (selectedBlockEntity.isConnectedTo(clickedPos)) {
            ResolithNode.disconnect(level, selectedPos, clickedPos)
            player.displayClientMessage(
                TranslatableComponent(Texts.NODE_CONNECTION_REMOVED)
                    .withStyle(ChatFormatting.GREEN),
                false,
            )
        } else {
            val success: Boolean =
                ResolithNode.attemptHandshake(selectedBlockEntity, clickedBlockEntity)
            if (success) {
                player.displayClientMessage(
                    TranslatableComponent(Texts.NODE_CONNECTION_CREATED)
                        .withStyle(ChatFormatting.GREEN),
                    false,
                )
            } else {
                player.displayClientMessage(
                    TranslatableComponent(Texts.NODE_LIMIT_REACHED).withStyle(ChatFormatting.RED),
                    false,
                )
            }
        }

        return InteractionResult.SUCCESS
    }

    override fun use(
        level: Level,
        player: Player,
        usedHand: InteractionHand,
    ): InteractionResultHolder<ItemStack> {
        if (!level.isClientSide && player.isShiftKeyDown) {
            val holdingItem = player.getItemInHand(usedHand)
            if (hasSelection(holdingItem)) {
                clearSelection(holdingItem)
                player.displayClientMessage(
                    TranslatableComponent(Texts.NODE_SELECTED_CLEAR)
                        .withStyle(ChatFormatting.GREEN),
                    false,
                )
                return InteractionResultHolder.success(holdingItem)
            }
        } else if (!level.isClientSide && !player.isShiftKeyDown) {
            val holdingItem = player.getItemInHand(usedHand)
            if (hasSelection(holdingItem)) {
                player.displayClientMessage(
                    TranslatableComponent(
                            Texts.NODE_SELECTED,
                            getSelection(holdingItem)!!.toShortString(),
                        )
                        .withStyle(ChatFormatting.GREEN),
                    false,
                )
                return InteractionResultHolder.success(holdingItem)
            }
        }

        return super.use(level, player, usedHand)
    }

    fun storeSelection(manipulator: ItemStack, selectedPos: BlockPos) {
        val tag = manipulator.orCreateTag
        tag.put("SelectedPos", NbtUtils.writeBlockPos(selectedPos))
    }

    fun hasSelection(manipulator: ItemStack) =
        manipulator.hasTag() && manipulator.tag!!.contains("SelectedPos")

    fun getSelection(manipulator: ItemStack): BlockPos? {
        val tag = manipulator.tag
        if (tag != null && tag.contains("SelectedPos")) {
            return NbtUtils.readBlockPos(tag.getCompound("SelectedPos"))
        }
        return null
    }

    fun clearSelection(manipulator: ItemStack) {
        if (hasSelection(manipulator)) {
            manipulator.tag!!.remove("SelectedPos")
        }
    }
}
