package dev.tobynguyen27.astralgenerators.core.util

object SIUtils {

    const val STANDARD_TEMPERATURE = 273.15

    fun convertKelvinToCelsius(k: Double): Double {
        return k - STANDARD_TEMPERATURE;
    }

    fun convertKelvinToFahrenheit(k: Double): Double {
        return ((k - STANDARD_TEMPERATURE) * 1.8) + 32
    }

    fun formatTemperature(temp: Double, unit: TemperatureUnit): String {
        return when (unit) {
            TemperatureUnit.CELSIUS -> "$temp ℃"
            TemperatureUnit.FAHRENHEIT -> "$temp °F"
            TemperatureUnit.KELVIN -> "$temp K"
        }
    }

    enum class TemperatureUnit {
        KELVIN,
        CELSIUS,
        FAHRENHEIT
    }

}
