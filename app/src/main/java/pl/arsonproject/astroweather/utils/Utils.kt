package pl.arsonproject.astroweather.utils

import com.astrocalculator.AstroDateTime

fun AstroDateTime.toBestTime() = "${this.hour - this.timezoneOffset}:${this.minute}:${this.second}"

fun AstroDateTime.toBestDate() = "${this.toBestTime()} ${this.day}.${this.month}.${this.year}"