package dev.tobynguyen27.astralgenerators.data.config

import me.shedaniel.autoconfig.AutoConfig

object ConfigHolder {

    val CONFIG: AGConfig
        get() = AutoConfig.getConfigHolder<AGConfig>(AGConfig::class.java).get()

    fun register() {}
}
