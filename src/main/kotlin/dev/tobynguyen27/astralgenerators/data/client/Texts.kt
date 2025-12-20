package dev.tobynguyen27.astralgenerators.data.client

import dev.tobynguyen27.astralgenerators.AstralGenerators

object Texts {
    const val ENERGY = "text.energy"
    const val CAPACITY = "text.capacity"
    const val STORED = "text.stored"
    const val PROGRESS = "text.progress"
    const val IDLING = "text.idling"
    const val INVALID_MULTIBLOCK = "text.multiblock.invalid"
    const val IO_BUTTON_MODE = "text.button.io.mode"
    const val AUTO_IMPORT = "text.autoimport"
    const val AUTO_EXPORT = "text.autoexport"
    const val ENABLED = "text.enabled"
    const val DISABLED = "text.disabled"
    const val TEMPERATURE = "text.temperature"
    const val MAX_TEMPERATURE = "text.maxtemperature"
    const val CURRENT_TEMPERATURE = "text.currenttemperature"

    fun register() {
        val texts =
            hashMapOf(
                ENERGY to "Energy",
                CAPACITY to "Capacity",
                STORED to "Stored",
                PROGRESS to "Progress",
                IDLING to "Idling",
                INVALID_MULTIBLOCK to "Invalid multiblock structure",
                IO_BUTTON_MODE to "{{button}} is {{status}}",
                AUTO_IMPORT to "Auto import",
                AUTO_EXPORT to "Auto export",
                ENABLED to "enabled",
                DISABLED to "disabled",
                TEMPERATURE to "Temperature",
                MAX_TEMPERATURE to "Max temperature",
                CURRENT_TEMPERATURE to "Current temperature",
            )

        texts.forEach { (k, v) -> AstralGenerators.REGISTRATE.addRawLang(k, v) }
    }
}
