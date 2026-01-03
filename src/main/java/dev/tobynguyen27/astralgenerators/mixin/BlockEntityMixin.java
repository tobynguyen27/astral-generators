package dev.tobynguyen27.astralgenerators.mixin;

import dev.tobynguyen27.astralgenerators.core.blockentity.attributes.EnergyContainerAttribute;
import dev.tobynguyen27.astralgenerators.core.blockentity.attributes.FluidContainerAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin {

    @Inject(method = "saveAdditional", at = @At(value = "RETURN"))
    private void injectSaveAdditional(CompoundTag tag, CallbackInfo ci) {
        if (this instanceof EnergyContainerAttribute blockEntity) {
            blockEntity.saveEnergyData(tag);
        }
        if (this instanceof FluidContainerAttribute blockEntity) {
            blockEntity.saveFluidData(tag);
        }
    }

    @Inject(method = "load", at = @At(value = "RETURN"))
    private void injectLoad(CompoundTag tag, CallbackInfo ci) {
        if (this instanceof EnergyContainerAttribute blockEntity) {
            blockEntity.loadEnergyData(tag);
        }
        if (this instanceof FluidContainerAttribute blockEntity) {
            blockEntity.loadFluidData(tag);
        }
    }
}
