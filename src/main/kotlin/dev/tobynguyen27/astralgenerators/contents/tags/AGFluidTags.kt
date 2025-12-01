package dev.tobynguyen27.astralgenerators.contents.tags

import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid

object AGFluidTags {
    val STEAM: TagKey<Fluid> =
        TagKey.create(
            ResourceKey.createRegistryKey(ResourceLocation("c", "steam")),
            ResourceLocation("c", "steam"),
        )
}
