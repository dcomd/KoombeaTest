package com.alexandre.android.koombea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KoombeaPost(val id: Int, val date: String, val pics: List<String>): Parcelable