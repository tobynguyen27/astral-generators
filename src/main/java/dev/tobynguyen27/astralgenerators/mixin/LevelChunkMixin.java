package dev.tobynguyen27.astralgenerators.mixin;

import dev.tobynguyen27.astralgenerators.core.multiblock.level.ChunkEventListeners;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin extends ChunkAccess {
    public LevelChunkMixin(
            ChunkPos pos,
            UpgradeData upgradeData,
            LevelHeightAccessor heightLimitView,
            Registry<Biome> biome,
            long inhabitedTime,
            @Nullable LevelChunkSection[] sectionArrayInitializer,
            @Nullable BlendingData blendingData) {
        super(pos, upgradeData, heightLimitView, biome, inhabitedTime, sectionArrayInitializer, blendingData);
        throw new AssertionError();
    }

    @Shadow
    @Final
    final Level level;

    @SuppressWarnings("rawtypes")
    @Inject(method = "setBlockState", at = @At("HEAD"))
    private void onSetBlockState(BlockPos pos, BlockState state, boolean moved, CallbackInfoReturnable cir) {
        if (!level.isClientSide()) {
            ChunkEventListeners.onBlockStateChange(level, this.chunkPos, pos);
        }
    }
}
