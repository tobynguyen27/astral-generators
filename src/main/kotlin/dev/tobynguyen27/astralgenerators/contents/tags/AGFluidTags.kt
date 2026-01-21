package dev.tobynguyen27.astralgenerators.contents.tags

import dev.tobynguyen27.sense.util.TagUtils
import net.minecraft.tags.TagKey
import net.minecraft.world.level.material.Fluid

object AGFluidTags {
    val STEAM: TagKey<Fluid> = TagUtils.createCommonTag("steam")
}
