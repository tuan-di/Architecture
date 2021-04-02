package com.tuandi.architecture.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


private var toast: Toast? = null

fun Context?.toast(message: String) {
    if (this == null) {
        return
    }
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .apply {
            show()
        }
}

fun Context?.toast(@StringRes message: Int) {
    if (this == null) {
        return
    }
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .apply {
            show()
        }
}