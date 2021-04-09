package com.tuandi.architecture.extensions

/**
 * Function checking your device has google play service!!!
 *
 */
//private fun checkGooglePlayServices(): Boolean {
//    val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
//    return if (status != ConnectionResult.SUCCESS) {
//        Log.e(TAG, "Error")
//        // ask user to update google play services and manage the error.
//        false
//    } else {
//        // 3
//        Log.i(TAG, "Google play services updated")
//        true
//    }
//}/


/**
 * Helper to force a when statement to assert all options are matched in a when statement.
 *
 * By default, Kotlin doesn't care if all branches are handled in a when statement. However, if you
 * use the when statement as an expression (with a value) it will force all cases to be handled.
 *
 * This helper is to make a lightweight way to say you meant to match all of them.
 *
 * Usage:
 *
 * ```
 * when(sealedObject) {
 *     is OneType -> //
 *     is AnotherType -> //
 * }.checkAllMatched
 */
val <T> T.checkAllMatched: T
    get() = this
