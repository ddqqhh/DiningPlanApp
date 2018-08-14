@file:Suppress("unused")

package com.cxt.diningplan.extend

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

fun String.toDate(format: String): LocalDate = LocalDate.parse(this, DateTimeFormatter.ofPattern(format))

fun String.toTime(format: String): LocalTime = LocalTime.parse(this, DateTimeFormatter.ofPattern(format))

fun String.toDateTime(format: String): LocalDateTime = LocalDateTime.parse(this, DateTimeFormatter.ofPattern(format))

fun LocalDate.toString(format: String): String = this.format(DateTimeFormatter.ofPattern(format))

fun LocalTime.toString(format: String): String = this.format(DateTimeFormatter.ofPattern(format))

fun LocalDateTime.toString(format: String): String = this.format(DateTimeFormatter.ofPattern(format))