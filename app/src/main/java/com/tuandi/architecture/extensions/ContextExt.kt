package com.tuandi.architecture.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.annotation.StringRes


private var toast: Toast? = null

fun Context.toast(message: String? = null) {
    toast?.cancel()
    if (message == null) {
        return
    }
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .apply {
            show()
        }
}

fun Context.toast(@StringRes message: Int) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .apply {
            show()
        }
}

fun Context.isNetworkAvailable() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
        getNetworkCapabilities(activeNetwork)?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }

//fun isNetworkAvailable(context: Context?): Boolean {
//    if (context == null) return false
//    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        if (capabilities != null) {
//            when {
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
//                    return true
//                }
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
//                    return true
//                }
//                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
//                    return true
//                }
//            }
//        }
//    } else {
//        val activeNetworkInfo = connectivityManager.activeNetworkInfo
//        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
//            return true
//        }
//    }
//    return false
//}