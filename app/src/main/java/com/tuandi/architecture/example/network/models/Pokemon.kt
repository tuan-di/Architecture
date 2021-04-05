package com.tuandi.architecture.example.network.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Pokemon(
    val name: String,
    val url: String
): Parcelable