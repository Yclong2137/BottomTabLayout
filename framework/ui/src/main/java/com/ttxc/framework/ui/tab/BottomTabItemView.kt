package com.ttxc.framework.ui.tab

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import com.squareup.contour.ContourLayout

@SuppressLint("ViewConstructor")
class BottomTabItemView(context: Context, private val tabSpec: TabSpec, attrs: AttributeSet? = null) :
    ContourLayout(context, attrs) {


    /**
     * tab外间距
     */
    private val tabMargin = 6.dip

    private val labelView = TextView(context).apply {
        gravity = Gravity.CENTER
        text = tabSpec.label
        setTextColor(
            generateColorStateList(
                enabledColor = tabSpec.normalColor,
                selectedColor = tabSpec.selectColor,
            )
        )
    }

    private val iconView = ImageView(context).apply {
        setImageDrawable(tabSpec.icon)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw() called with: canvas = $canvas")
        drawTabBackground(canvas)
    }

    /**
     * 绘制tab背景
     */
    open fun drawTabBackground(canvas: Canvas?) {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    this.scaleX = 0.8f
                    this.scaleY = 0.8f
                }
                MotionEvent.ACTION_UP -> {
                    this.scaleX = 1f
                    this.scaleY = 1f
                }
                MotionEvent.ACTION_CANCEL -> {
                    this.scaleX = 1f
                    this.scaleY = 1f
                }
                else -> {

                }
            }
        }
        return super.onTouchEvent(event)

    }


    init {
        setBackgroundColor(Color.TRANSPARENT)
        iconView.layoutBy(
            x = matchParentX(),
            y = topTo { parent.top() + tabMargin }.bottomTo { parent.height() / 5 * 3 }
        )
        labelView.layoutBy(
            x = matchParentX(),
            y = topTo { iconView.bottom() }.bottomTo { parent.bottom() - tabMargin }
        )
    }


    companion object {
        private const val TAG = "BottomTabItemView"
    }

}