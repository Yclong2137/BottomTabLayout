package com.ttxc.framework.ui.tab

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment

data class TabSpec(
    val icon: Drawable,
    val label: String,
    val page: Class<out Fragment>,
    val normalColor: Int = Color.TRANSPARENT,
    val selectColor: Int = Color.TRANSPARENT
)

