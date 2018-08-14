package com.cxt.diningplan.extend

import android.view.View
import android.view.ViewGroup

val ViewGroup.childes: List<View>
    get() = (0 until childCount).map { getChildAt(it) }

@Suppress("unused")
val List<Boolean>.isAllTrue
    get() = !this.contains(false)