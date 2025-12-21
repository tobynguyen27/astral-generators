package dev.tobynguyen27.astralgenerators.integration

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import dev.tobynguyen27.astralgenerators.data.config.AGConfig
import me.shedaniel.autoconfig.AutoConfig

class ModMenu : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory {
            AutoConfig.getConfigScreen<AGConfig>(AGConfig::class.java, it).get()
        }
    }
}
