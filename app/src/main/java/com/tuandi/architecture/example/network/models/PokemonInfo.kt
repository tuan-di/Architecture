package com.tuandi.architecture.example.network.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val base_experience: Int
) : Parcelable