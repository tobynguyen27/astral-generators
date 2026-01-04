package dev.tobynguyen27.astralgenerators.core.blockentity.attributes

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage

interface FluidContainerAttribute : Attribute {

    val fluidContainer: SingleVariantStorage<FluidVariant>

    fun createFluidContainer(capacity: Long): SingleVariantStorage<FluidVariant> {
        return object : SingleVariantStorage<FluidVariant>() {
            override fun getBlankVariant(): FluidVariant = FluidVariant.blank()

            override fun getCapacity(variant: FluidVariant): Long = capacity

            override fun onFinalCommit() = self.setChanged()
        }
    }
}
