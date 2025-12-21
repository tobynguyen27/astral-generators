package dev.tobynguyen27.astralgenerators.contents.machines.assembler

import dev.tobynguyen27.astralgenerators.core.util.IInventory
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.ContainerHelper
import net.minecraft.world.MenuProvider
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import team.reborn.energy.api.base.SimpleEnergyStorage

class AssemblerBlockEntity(
    type: BlockEntityType<AssemblerBlockEntity>,
    blockPos: BlockPos,
    blockState: BlockState,
) :
    BlockEntity(type, blockPos, blockState),
    MenuProvider,
    PropertyDelegateHolder,
    ExtendedScreenHandlerFactory,
    IInventory,
    WorldlyContainer {

    companion object {
        const val ID = "assembler_entity"

        private const val IS_ENABLED_TAG = "is_enabled"

        // Energy
        private const val ENERGY_CAPACITY = 100000.toLong()
        private const val MAX_ENERGY_INSERT = 100000.toLong()
        private const val MAX_ENERGY_EXTRACT = 100000.toLong()
        private const val ENERGY_STORAGE_TAG = "assembler_energy"

        // Fluid
        private const val MAX_FLUID_CAPACITY_IN_BUCKET = 10 * FluidConstants.BUCKET // 10 buckets
        private const val FLUID_STORAGE_AMOUNT_TAG = "assembler_fluid_amount"
        private const val FLUID_STORAGE_TYPE_TAG = "assembler_fluid_type"

        // Container
        const val CONTAINER_SIZE = 10
        private val INPUT_SLOTS = IntArray(CONTAINER_SIZE - 1) { it }
        private val OUTPUT_SLOT = intArrayOf(CONTAINER_SIZE - 1)

        // Data (progress)
        const val CONTAINER_DATA_SIZE = 3

        // Progress
        const val MAX_PROGRESS_TAG = "assembler_max_progress"
        const val PROGRESS_TAG = "assembler_progress"
        const val SAVED_RECIPE_ID_TAG = "saved_recipe_id"
    }

    // Progress
    var isEnabled = 0
    var maxProgress: Int = 100
    var progress: Int = 0
    var cachedRecipe: AssemblerRecipe? = null
    var savedRecipeId: ResourceLocation? = null

    // Energy
    val energyStorage =
        object : SimpleEnergyStorage(ENERGY_CAPACITY, MAX_ENERGY_INSERT, MAX_ENERGY_EXTRACT) {
            override fun onFinalCommit() {
                setChanged()
            }
        }

    // Fluid
    val fluidStorage =
        object : SingleVariantStorage<FluidVariant>() {
            override fun getBlankVariant(): FluidVariant = FluidVariant.blank()

            override fun getCapacity(p0: FluidVariant): Long = MAX_FLUID_CAPACITY_IN_BUCKET

            override fun onFinalCommit() {
                setChanged()
            }
        }

    // Container
    private val items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY)

    val containerWrapper = InventoryStorage.of(this, null)
    val inputsStorage = containerWrapper.slots.subList(0, CONTAINER_SIZE - 1)
    val outputStorage = containerWrapper.getSlot(CONTAINER_SIZE - 1)

    override fun getItems(): NonNullList<ItemStack> {
        return items
    }

    // Data
    val containerData =
        object : ContainerData {
            override fun get(index: Int): Int {
                return when (index) {
                    0 -> maxProgress
                    1 -> progress
                    2 -> isEnabled
                    else -> -1
                }
            }

            override fun set(index: Int, value: Int) {
                when (index) {
                    1 -> progress = value
                    2 -> isEnabled = value
                }
            }

            override fun getCount(): Int {
                return CONTAINER_DATA_SIZE
            }
        }

    override fun setChanged() {
        super<BlockEntity>.setChanged()
    }

    override fun load(tag: CompoundTag) {
        isEnabled = tag.getInt(IS_ENABLED_TAG)
        energyStorage.amount = tag.getLong(ENERGY_STORAGE_TAG)

        fluidStorage.amount = tag.getLong(FLUID_STORAGE_AMOUNT_TAG)
        fluidStorage.variant = FluidVariant.fromNbt(tag.getCompound(FLUID_STORAGE_TYPE_TAG))

        ContainerHelper.loadAllItems(tag, items)

        maxProgress = tag.getInt(MAX_PROGRESS_TAG)
        progress = tag.getInt(PROGRESS_TAG)
        if (tag.contains(SAVED_RECIPE_ID_TAG)) {
            val id = tag.getString(SAVED_RECIPE_ID_TAG)
            if (id.isNotEmpty()) {
                savedRecipeId = ResourceLocation(id)
            }
        }

        super.load(tag)
    }

    override fun saveAdditional(tag: CompoundTag) {
        tag.putInt(IS_ENABLED_TAG, isEnabled)
        tag.putLong(ENERGY_STORAGE_TAG, energyStorage.amount)

        tag.putLong(FLUID_STORAGE_AMOUNT_TAG, fluidStorage.amount)
        tag.put(FLUID_STORAGE_TYPE_TAG, fluidStorage.variant.toNbt())

        ContainerHelper.saveAllItems(tag, items)

        tag.putInt(MAX_PROGRESS_TAG, maxProgress)
        tag.putInt(PROGRESS_TAG, progress)
        cachedRecipe?.let { tag.putString(SAVED_RECIPE_ID_TAG, it.id.toString()) }

        super.saveAdditional(tag)
    }

    // Menu
    override fun getDisplayName(): Component {
        return TranslatableComponent(blockState.block.descriptionId)
    }

    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return AssemblerMenu(i, inventory, ContainerLevelAccess.create(player.level, blockPos))
    }

    override fun getPropertyDelegate(): ContainerData {
        return containerData
    }

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {
        // Energy
        buf.writeLong(energyStorage.capacity)
        buf.writeLong(energyStorage.amount)

        // Fluid
        buf.writeLong(fluidStorage.capacity)
        buf.writeLong(fluidStorage.amount)
        buf.writeNbt(fluidStorage.variant.toNbt())
    }

    // Inventory interact
    override fun getSlotsForFace(side: Direction): IntArray {
        if (side == Direction.DOWN) {
            return OUTPUT_SLOT
        }

        return INPUT_SLOTS
    }

    override fun canPlaceItemThroughFace(
        index: Int,
        itemStack: ItemStack,
        direction: Direction?,
    ): Boolean {
        return direction != Direction.DOWN && index < CONTAINER_SIZE - 1
    }

    override fun canTakeItemThroughFace(
        index: Int,
        stack: ItemStack,
        direction: Direction,
    ): Boolean {
        return direction == Direction.DOWN && index == CONTAINER_SIZE - 1
    }
}
