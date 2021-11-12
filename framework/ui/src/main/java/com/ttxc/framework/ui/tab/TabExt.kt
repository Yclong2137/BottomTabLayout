package com.ttxc.framework.ui.tab

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun generateColorStateList(
    enabledColor: Int = Color.TRANSPARENT,
    selectedColor: Int = Color.TRANSPARENT
): ColorStateList {
    val states = arrayOf(
        intArrayOf(android.R.attr.state_selected),
        intArrayOf(),
        )
    val colors = intArrayOf(
        selectedColor, // selected
        enabledColor // enabled
    )
    return ColorStateList(states, colors)
}

fun FragmentManager.edit(
    allowingStateLoss: Boolean = false,
    action: FragmentTransaction.() -> Unit
) {
    val beginTransaction = this.beginTransaction().apply(action)
    if (allowingStateLoss) {
        beginTransaction.commitAllowingStateLoss()
    } else {
        beginTransaction.commit()
    }

}