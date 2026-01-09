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
    const val NODE_SELECTED = "text.node.selected"
    const val NODE_SELECTION_REQUIRED = "text.node.select.require"
    const val NODE_BIND_SELF = "text.node.bind.self"
    const val NODE_SELECTED_NO_EXISTS = "text.node.selected.no.exists"
    const val NODE_CONNECTION_REMOVED = "text.node.connection.removed"
    const val NODE_CONNECTION_CREATED = "text.node.connection.created"
    const val NODE_LIMIT_REACHED = "text.node.limit.reached"
    const val NODE_SELECTED_CLEAR = "text.node.selected.clear"
    const val RESOLITH_CONNECTIONS = "text.resolith.connections"

    fun register() {
        val texts =
            hashMapOf(
                RESOLITH_CONNECTIONS to "Connections: %d / %d",
                NODE_LIMIT_REACHED to "Connection failed (Limit reached)",
                NODE_CONNECTION_CREATED to "Connection created",
                NODE_CONNECTION_REMOVED to "Connection removed",
                NODE_SELECTED_NO_EXISTS to "Selected Resolith no longer exists",
                NODE_BIND_SELF to "Cannot bind Resolith to itself",
                NODE_SELECTION_REQUIRED to "Sneak + Right Click on a Resolith to select it first",
                NODE_SELECTED to "Selected Resolith: %s",
                NODE_SELECTED_CLEAR to "Selected block position cleared",
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
