package com.ttxc.framework.ui.tab

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.squareup.contour.ContourLayout

@SuppressLint("ViewConstructor")
class BottomTabLayout(
    context: Context,
    attrs: AttributeSet? = null,
    applyTabConfig: BottomTabConfig.() -> Unit
) :
    ContourLayout(context, attrs) {

    /**
     * TabSpecs
     */
    private val tabSpecs = ArrayList<TabSpec>()

    /**
     * Tabs
     */
    private val tabs = ArrayList<Tab>()

    /**
     * tab总宽度
     */
    private var totalTabWidth = 0

    /**
     * tab宽度
     */
    private var tabWidth = 0


    /**
     * 当前tab
     */
    private var curTab: Tab? = null

    /**
     * Tab配置
     */
    private val tabConfig by lazy { BottomTabConfig() }

    /**
     * Page配置
     */
    private val pageConfig by lazy { PageConfig() }


    /**
     * 系统默认分割线
     */
    private val divider by lazy {
        val ta = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        val drawable = ta.getDrawable(0)
        ta.recycle()
        drawable
    }

    /**
     * 设置新的tab
     */
    fun setNewTabs(tabs: List<TabSpec>) {
        this.tabSpecs.clear()
        this.tabSpecs.addAll(tabs)
        this.removeAllViews()
        this.initTabs()
    }

    private val line = View(context).apply {
        background = divider
    }


    init {
        setWillNotDraw(false)
        applyTabConfig(tabConfig)
        tabConfig.applyPageConfig(pageConfig)
        initTabs()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        tabConfig.applyBackground(this, canvas)
    }


    /**
     * 初始化tabs
     */
    private fun initTabs() {
        Log.d(TAG, "initTabs() called")
        if (tabSpecs.isNullOrEmpty()) return

        //应用容器
        this.apply(pageConfig.applyContainer)
        //Tab
        for (tabSpec in tabSpecs) {
            tabConfig.applyTabView(context, tabSpec).apply {
                val tab = Tab(tabSpec, this)
                tabs.add(tab)
                if (curTab == null) {
                    curTab = tab
                }
            }.layoutBy(
                x = leftTo {
                    val w = totalTabWidth.toXInt()
                    totalTabWidth += tabWidth
                    w
                }.widthOf {
                    tabWidth = parent.width().value / tabSpecs.size
                    tabWidth.toXInt()
                },
                y = bottomTo {
                    parent.bottom() - tabConfig.tabPadding
                }.heightOf { tabConfig.tabHeight.toYInt() - (2 * tabConfig.tabPadding).toYInt() }
            )
        }
        curTab?.select(true)

        //分割线
        this.apply(tabConfig.applyDivider)
    }

    override fun requestLayout() {
        clearHistory()
        super.requestLayout()
    }

    /**
     * 清空历史
     */
    private fun clearHistory() {
        totalTabWidth = 0
    }


    companion object {
        private const val TAG = "BottomTabLayout"
    }

    inner class Tab(val tabSpec: TabSpec, private val tabView: View) {

        init {
            tabView.setOnClickListener {
                select(true)
            }
        }

        /**
         * 选中tab
         */
        fun select(isSelected: Boolean) {
            if (this != curTab && isSelected) {
                //恢复上个tab
                curTab?.select(false)
            }
            this.tabView.isSelected = isSelected
            if (isSelected) {
                pageConfig.pageChanged(this)
            }
            curTab = this
        }

    }

    /**
     * BottomTab Config
     */
    inner class BottomTabConfig {

        /**
         * tab高度
         */
        var tabHeight = 56.dip

        /**
         * 分割线高度
         */
        var dividerHeight = 1.dip

        /**
         * tab间距
         */
        var tabPadding = 0.dip

        /**
         * Page配置
         */
        var applyPageConfig: PageConfig.() -> Unit = {}

        /**
         * 应用Tab
         */
        var applyTabView: (context: Context, tabSpec: TabSpec) -> View = { context, tabSpec ->
            BottomTabItemView(context, tabSpec = tabSpec)
        }

        /**
         * 应用TabLayout背景
         */
        var applyBackground: ContourLayout.(canvas: Canvas) -> Unit = { }


        /**
         * 应用分割线
         */
        var applyDivider: ContourLayout.() -> Unit = {
            line.layoutBy(
                x = matchParentX(),
                y = bottomTo { parent.bottom() - tabHeight.toYInt() }.heightOf { dividerHeight.toYInt() },
            )
        }

    }

    /**
     * 页面配置
     */
    inner class PageConfig {

        /**
         * 应用Fragment容器
         */
        var applyContainer: ContourLayout.() -> Unit = {}

        /**
         * Page改变
         */
        var pageChanged: (tab: Tab) -> Unit = {}

        /**
         * 选中Tab
         */
        fun select(tabSpec: TabSpec) {
            tabs.find { tabSpec == it.tabSpec }?.select(true)
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.tabs.clear()
        this.tabSpecs.clear()
    }


}