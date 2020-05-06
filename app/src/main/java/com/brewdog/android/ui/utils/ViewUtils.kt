package com.brewdog.android.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflateInto(@LayoutRes layoutId: Int, attach: Boolean): View {
    val inflater = LayoutInflater.from(this.context)
    return inflater.inflate(layoutId, this, attach)
}