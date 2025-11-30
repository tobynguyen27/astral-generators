package dev.tobynguyen27.astralgenerators.utils

import java.text.DecimalFormat
import java.util.Locale.getDefault

object StringHelper {
    /**
     * Convert string to readable English name
     *
     * Example: `steel_ingot` -> `Steel Ingot`
     */
    fun toEnglishName(str: String): String =
        str.lowercase(getDefault()).split("_").joinToString(" ") {
            it.replaceFirstChar { c -> c.uppercaseChar() }
        }

    fun toReadableNumberString(number: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number)
    }

    /**
     * Calculates the percentage of [a] relative to [b] and returns it as a formatted string.
     *
     * The result is formatted to two decimal places (e.g., "25.00"). If [b] is 0.0, the function
     * returns `"NaN"` to avoid division by zero.
     */
    fun calculateFormattedPercentage(a: Double, b: Double): String {
        if (b == 0.0) return "NaN"
        val percentage = (a / b) * 100
        val format = DecimalFormat("0.00")
        return "${format.format(percentage)}"
    }
}
