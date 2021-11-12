package com.ttxc.framework

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.contour.ContourLayout
import kotlin.random.Random

class HomeFragment : Fragment() {


    inner class ContainerView(context: Context, attrs: AttributeSet?) :
        ContourLayout(context, attrs) {

        private val tipView = TextView(context).apply {
            text = "${Random.Default.nextInt()}"
            gravity= Gravity.CENTER
            setTextColor(Color.RED)
        }

        init {
            tipView.layoutBy(
                x = matchParentX(),
                y = matchParentY()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ContainerView(requireContext(), null)
    }

}