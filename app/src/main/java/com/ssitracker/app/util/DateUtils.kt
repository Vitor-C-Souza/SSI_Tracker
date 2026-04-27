package com.ssitracker.app.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

object DateUtils {
    /**
     * Formata uma data no formato "Sexta-feira, Abril 24" (dependendo do Locale).
     * Padrão: "EEEE, MMMM d"
     */
    fun formatToFullDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH)
        return date.format(formatter)
    }

    /**
     * Formata uma data no formato "Monday, April 27, 2026".
     * Padrão: "EEEE, MMMM d, yyyy"
     */
    fun formatToFullDateWithYear(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH)
        return date.format(formatter)
    }

    fun formatToFullDateWithoutDayOfWeek(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
        return date.format(formatter)
    }

    fun getRelativeDayLabel(date: LocalDate): String {
        val today = LocalDate.now()
        return when {
            date == today -> "Today"
            date == today.minusDays(1) -> "Yesterday"
            else -> date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        }
    }
}
