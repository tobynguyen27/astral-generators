package dev.tobynguyen27.astralgenerators.utils

import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

object TagUtils {
    fun create(name: String): TagKey<Block> {
        return TagKey.create(ResourceKey.createRegistryKey(Identifier(name)), Identifier(name))
    }

    fun create(id: String, name: String): TagKey<Block> {
        return TagKey.create(
            ResourceKey.createRegistryKey(ResourceLocation(id, name)),
            ResourceLocation(id, name),
        )
    }
}
