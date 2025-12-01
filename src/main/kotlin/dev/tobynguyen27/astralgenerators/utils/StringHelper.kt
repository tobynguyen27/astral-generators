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

    fun calculateFormattedPercentage(a: Number, b: Number, pattern: String = "0.00"): String {
        if (b == 0.0) return "NaN"

        val percentage = (a.toDouble() / b.toDouble()) * 100
        val format = DecimalFormat(pattern)

        return format.format(percentage)
    }
}
