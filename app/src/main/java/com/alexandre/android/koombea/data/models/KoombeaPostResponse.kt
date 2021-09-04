package com.alexandre.android.koombea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KoombeaPostResponse(val data: List<KoombeaUser>, val statusCode: Int?, val statusMessage: String): Parcelable