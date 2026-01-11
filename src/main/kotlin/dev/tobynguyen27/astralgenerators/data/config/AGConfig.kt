package dev.tobynguyen27.astralgenerators.data.config

import dev.tobynguyen27.astralgenerators.AstralGenerators
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = AstralGenerators.MOD_ID)
class AGConfig : ConfigData {
    @ConfigEntry.Category("boiler") @ConfigEntry.Gui.Tooltip var waterBoilingPoint = 100

    @ConfigEntry.Category("boiler") @ConfigEntry.Gui.Tooltip var steamExpansionRatio = 160

    @ConfigEntry.Category("boiler") @ConfigEntry.Gui.Tooltip var idealWaterConsumption = 81

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Category("steam_turbine")
    @ConfigEntry.Gui.Tooltip
    var dragCoefficient = 0.005

    @ConfigEntry.Category("steam_turbine") @ConfigEntry.Gui.Tooltip var acceleratorFactor = 0.001

    @ConfigEntry.Category("steam_turbine") @ConfigEntry.Gui.Tooltip var maxSteamIntake = 18000

    @ConfigEntry.Category("steam_turbine") @ConfigEntry.Gui.Tooltip var energyMultiplier = 4

    @ConfigEntry.Gui.PrefixText @ConfigEntry.Category("resolith") var ironRelayMaxConnections = 4
    @ConfigEntry.Category("resolith") var ironRelayRange = 8
    @ConfigEntry.Category("resolith") var electrumRelayMaxConnections = 8
    @ConfigEntry.Category("resolith") var electrumRelayRange = 16

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Category("resolith")
    var ironTransceiverMaxConnections = 2
    @ConfigEntry.Category("resolith") var ironTransceiverRange = 8
    @ConfigEntry.Category("resolith") var ironTransceiverTransferRate = 1000
    @ConfigEntry.Category("resolith") var electrumTransceiverMaxConnections = 3
    @ConfigEntry.Category("resolith") var electrumTransceiverTransferRate = 10000
    @ConfigEntry.Category("resolith") var electrumTransceiverRange = 16
}
