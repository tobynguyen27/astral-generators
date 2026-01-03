package dev.tobynguyen27.astralgenerators.contents.machines.assembler

import dev.tobynguyen27.astralgenerators.core.base.MachineBlockEntity
import dev.tobynguyen27.astralgenerators.core.blockentity.attributes.EnergyContainerAttribute
import dev.tobynguyen27.astralgenerators.core.blockentity.attributes.FluidContainerAttribute
import dev.tobynguyen27.astralgenerators.core.util.IInventory
import dev.tobynguyen27.sense.sync.annotation.Persisted
import dev.tobynguyen27.sense.sync.blockentity.AutoPersistBlockEntity
import dev.tobynguyen27.sense.sync.container.ManagedFieldContainer
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
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.ContainerHelper
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
    MachineBlockEntity(type, blockPos, blockState),
    PropertyDelegateHolder,
    ExtendedScreenHandlerFactory,
    IInventory,
    WorldlyContainer,
    AutoPersistBlockEntity,
EnergyContainerAttribute, FluidContainerAttribute{

    companion object {
        const val ID = "assembler_entity"

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
        const val SAVED_RECIPE_ID_TAG = "saved_recipe_id"
    }

    // Progress
    @Persisted var isEnabled = 0
    @Persisted var maxProgress: Int = 100
    @Persisted var progress: Int = 0
    var savedRecipeId: ResourceLocation? = null
    var cachedRecipe: AssemblerRecipe? = null

    override val fieldContainer: ManagedFieldContainer by lazy { ManagedFieldContainer(this) }
    override val self: BlockEntity = this
    override val energyContainer: SimpleEnergyStorage = createEnergyContainer(ENERGY_CAPACITY, MAX_ENERGY_EXTRACT, MAX_ENERGY_INSERT)
    override val fluidContainer: SingleVariantStorage<FluidVariant> = createFluidContainer(MAX_FLUID_CAPACITY_IN_BUCKET)

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
        super<MachineBlockEntity>.setChanged()
    }

    override fun load(tag: CompoundTag) {
        loadEnergyData(tag)
        loadFluidData(tag)

        ContainerHelper.loadAllItems(tag, items)

        if (tag.contains(SAVED_RECIPE_ID_TAG)) {
            val id = tag.getString(SAVED_RECIPE_ID_TAG)
            if (id.isNotEmpty()) {
                savedRecipeId = ResourceLocation(id)
            }
        }

        super.load(tag)
    }

    override fun saveAdditional(tag: CompoundTag) {
        saveEnergyData(tag)
        saveFluidData(tag)

        ContainerHelper.saveAllItems(tag, items)

        cachedRecipe?.let { tag.putString(SAVED_RECIPE_ID_TAG, it.id.toString()) }

        super.saveAdditional(tag)
    }

    // Menu
    override fun createMenu(i: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return AssemblerMenu(i, inventory, ContainerLevelAccess.create(player.level, blockPos))
    }

    override fun getPropertyDelegate(): ContainerData {
        return containerData
    }

    override fun writeScreenOpeningData(player: ServerPlayer, buf: FriendlyByteBuf) {
        // Energy
        buf.writeLong(energyContainer.capacity)
        buf.writeLong(energyContainer.amount)

        // Fluid
        buf.writeLong(fluidContainer.capacity)
        buf.writeLong(fluidContainer.amount)
        buf.writeNbt(fluidContainer.variant.toNbt())
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
