package com.alexandre.android.koombea.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KoombeaUser(val uid: String, val name: String, val email: String?, val profile_pic: String, val post: KoombeaPost): Parcelable
