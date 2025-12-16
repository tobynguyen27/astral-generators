package dev.tobynguyen27.astralgenerators.registry

import dev.tobynguyen27.astralgenerators.AstralGenerators.MOD_ID
import dev.tobynguyen27.astralgenerators.AstralGenerators.REGISTRATE
import dev.tobynguyen27.astralgenerators.core.util.FormattingUtil
import dev.tobynguyen27.astralgenerators.core.util.Identifier
import net.minecraft.core.Registry
import net.minecraft.sounds.SoundEvent

object AGSounds {

    val BOILER_WORKING = registerSound("boiler")

    private fun registerSound(name: String): SoundEvent {
        val id = Identifier(name)

        REGISTRATE.addRawLang("subtitle.$MOD_ID.$name", FormattingUtil.toEnglishName(name))

        return Registry.register(Registry.SOUND_EVENT,id, SoundEvent(id))
    }

    fun register() {}

}
