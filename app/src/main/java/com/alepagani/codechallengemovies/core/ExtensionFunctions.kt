package com.alepagani.codechallengemovies.core

import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

fun String.getYearFromReleaseDate(): String {
    return this.substring(0, 4)
}

fun addTransparency(color: Int, transparency: Float): Int {
    require(transparency in 0f..1f) { "Transparency should be between 0 and 1" }

    val alpha = (255 * transparency).toInt()
    val red = color.red
    val green = color.green
    val blue = color.blue

    return android.graphics.Color.argb(alpha, red, green, blue)
}