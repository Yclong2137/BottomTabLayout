package com.ttxc.framework

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.ttxc.framework.ui.tab.BottomTabLayout
import com.ttxc.framework.ui.tab.TabSpec
import com.ttxc.framework.ui.tab.edit
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomTabLayout = BottomTabLayout(this) {
            applyPageConfig = {
                var containerView: View? = null
                applyContainer = {
                    containerView = FrameLayout(context).apply {
                        id = ViewCompat.generateViewId()
                    }.layoutBy(
                        x = matchParentX(),
                        y = matchParentY(marginBottom = tabHeight)
                    )
                }
                pageChanged = { tab ->
                    Log.d(TAG, "onCreate() called with: tab = $tab")
                    supportFragmentManager.edit {
                        replace(containerView!!.id, tab.tabSpec.page.newInstance())
                    }
                }

            }


        }
        val tabs = ArrayList<TabSpec>()
        tabs.add(
            TabSpec(
                getDrawable(android.R.drawable.sym_action_email)!!,
                "首页", HomeFragment::class.java,
                selectColor = Color.BLUE,
                normalColor = Color.GRAY
            )
        )
        tabs.add(
            TabSpec(
                getDrawable(android.R.drawable.sym_action_email)!!,
                "首页", HomeFragment::class.java,
                selectColor = Color.BLUE,
                normalColor = Color.GRAY
            )
        )
        tabs.add(
            TabSpec(
                getDrawable(android.R.drawable.sym_action_email)!!,
                "首页", HomeFragment::class.java,
                selectColor = Color.BLUE,
                normalColor = Color.GRAY
            )
        )
        tabs.add(
            TabSpec(
                getDrawable(android.R.drawable.sym_action_email)!!,
                "首页", HomeFragment::class.java,
                selectColor = Color.BLUE,
                normalColor = Color.GRAY
            )
        )
        tabs.add(
            TabSpec(
                getDrawable(android.R.drawable.sym_action_email)!!,
                "首页", HomeFragment::class.java,
                selectColor = Color.BLUE,
                normalColor = Color.GRAY
            )
        )
        tabs.add(
            TabSpec(
                getDrawable(android.R.drawable.sym_action_email)!!,
                "首页", HomeFragment::class.java,
                selectColor = Color.BLUE,
                normalColor = Color.GRAY
            )
        )
        tabs.add(
            TabSpec(
                getDrawable(android.R.drawable.sym_action_email)!!,
                "首页", HomeFragment::class.java,
                selectColor = Color.BLUE,
                normalColor = Color.GRAY
            )
        )
        tabs.add(
            TabSpec(
                getDrawable(android.R.drawable.sym_action_email)!!,
                "首页", HomeFragment::class.java,
                selectColor = Color.BLUE,
                normalColor = Color.GRAY
            )
        )
        bottomTabLayout.setNewTabs(tabs)
        setContentView(bottomTabLayout)
    }
}