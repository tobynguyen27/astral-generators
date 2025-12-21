package dev.tobynguyen27.astralgenerators.data.config

import dev.tobynguyen27.astralgenerators.AstralGenerators

object ConfigTexts {
    const val BOILER = "boiler"
    const val STEAM_TURBINE = "steam_turbine"

    const val WATER_BOILING_POINT = "waterBoilingPoint"
    const val STEAM_EXPANSION_RATIO = "steamExpansionRatio"
    const val IDEAL_WATER_CONSUMPTION = "idealWaterConsumption"
    const val DRAG_COEFFICIENT = "dragCoefficient"
    const val ACCELERATOR_FACTOR = "acceleratorFactor"
    const val MAX_STEAM_INTAKE = "maxSteamIntake"
    const val ENERGY_MULTIPLIER = "energyMultiplier"

    fun register() {
        val texts = hashMapOf("title" to AstralGenerators.MOD_NAME)
        val categories = hashMapOf(BOILER to "Boiler", STEAM_TURBINE to "Steam Turbine")
        val options =
            hashMapOf(
                WATER_BOILING_POINT to "Water Boiling Point",
                STEAM_EXPANSION_RATIO to "Steam Expansion Ratio",
                IDEAL_WATER_CONSUMPTION to "Ideal Water Consumption",
                DRAG_COEFFICIENT to "Drag Coefficient",
                ACCELERATOR_FACTOR to "Accelerator Factor",
                ENERGY_MULTIPLIER to "Energy Multiplier",
                MAX_STEAM_INTAKE to "Max Steam Intake",
            )
        val tooltips =
            hashMapOf(
                WATER_BOILING_POINT to
                    "Boiling temperature of water, measured on the Celsius scale",
                STEAM_EXPANSION_RATIO to "How much Steam should be produced from 1 Water",
                IDEAL_WATER_CONSUMPTION to
                    "How much Water should be consumed when reach 100% efficiency",
                ENERGY_MULTIPLIER to "Produce energy = current rotor speed * energy multiplier",
                MAX_STEAM_INTAKE to "Amount of Steam can be consumed per tick",
                DRAG_COEFFICIENT to
                    "Rotor speed loss because of friction = drag coefficient * current rotor speed",
                ACCELERATOR_FACTOR to
                    "Amount of rotor speed which is produced from X Steam = X Steam * accelerator factor",
            )

        options.forEach { (k, v) ->
            AstralGenerators.REGISTRATE.addRawLang(
                "text.autoconfig.${AstralGenerators.MOD_ID}.option.$k",
                v,
            )
        }
        tooltips.forEach { (k, v) ->
            AstralGenerators.REGISTRATE.addRawLang(
                "text.autoconfig.${AstralGenerators.MOD_ID}.option.$k.@Tooltip",
                v,
            )
        }
        categories.forEach { (k, v) ->
            AstralGenerators.REGISTRATE.addRawLang(
                "text.autoconfig.${AstralGenerators.MOD_ID}.category.$k",
                v,
            )
        }
        texts.forEach { (k, v) ->
            AstralGenerators.REGISTRATE.addRawLang(
                "text.autoconfig.${AstralGenerators.MOD_ID}.$k",
                v,
            )
        }
        AstralGenerators.REGISTRATE.addRawLang(
            "text.autoconfig.${AstralGenerators.MOD_ID}.option.$DRAG_COEFFICIENT.@PrefixText",
            "At 100% : MAX_STEAM_INTAKE * ACCELERATOR_FACTOR = MAX_RPM * DRAG_COEFFICIENT",
        )
    }
}
