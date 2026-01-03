package dev.tobynguyen27.astralgenerators.core.blockentity.attributes

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage
import net.minecraft.nbt.CompoundTag

interface FluidContainerAttribute : Attribute {

    val fluidContainer: SingleVariantStorage<FluidVariant>

    fun saveFluidData(tag: CompoundTag) {
        tag.putLong("fluidAmount", fluidContainer.amount)
        tag.put("fluidVariant", fluidContainer.variant.toNbt())
    }

    fun loadFluidData(tag: CompoundTag) {
        fluidContainer.amount = tag.getLong("fluidAmount")
        fluidContainer.variant = FluidVariant.fromNbt(tag.getCompound("fluidVariant"))
    }

    fun createFluidContainer(capacity: Long): SingleVariantStorage<FluidVariant> {
        return object : SingleVariantStorage<FluidVariant>() {
            override fun getBlankVariant(): FluidVariant = FluidVariant.blank()

            override fun getCapacity(variant: FluidVariant): Long = capacity

            override fun onFinalCommit() = self.setChanged()
        }
    }
}
