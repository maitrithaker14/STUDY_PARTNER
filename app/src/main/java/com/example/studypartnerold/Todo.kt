package com.example.studypartnerold

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(
    val id: String? = null,
    val title: String? = null,
    val desc: String? = null,
    val due:String?= null
) : Parcelable
