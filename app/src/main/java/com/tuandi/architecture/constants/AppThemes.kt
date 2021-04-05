package com.tuandi.architecture.constants

import androidx.appcompat.app.AppCompatDelegate


enum class AppThemes(val rawValue: String, val themeRes: Int) {
    LIGHT("light", AppCompatDelegate.MODE_NIGHT_YES),
    DARK("dark", AppCompatDelegate.MODE_NIGHT_NO),
    BLACK("auto", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

    companion object {
        fun fromString(raw: String): AppThemes {
            return AppThemes.values().single { it.rawValue == raw }
        }

        fun toString(value: AppThemes): String = value.rawValue
    }
}
