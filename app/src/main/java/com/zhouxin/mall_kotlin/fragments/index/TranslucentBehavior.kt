package com.zhouxin.mall_kotlin.fragments.index

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View

import com.zhouxin.library.global.GlobalKeys
import com.zhouxin.library.global.Mall
import com.zhouxin.mall_kotlin.R

/**
 * @author zhouxin on 2020/4/24.
 * 在IndexFragment的xml被反射调用
 * 沉浸式状态栏
 */
@Suppress("unused")
class TranslucentBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<Toolbar>(context, attrs) {

    companion object {
        //延长滑动距离再开始渐变
        private const val MORE = 100
    }

    //注意：这里一定要是类变量
    private var mOffset = 0

    override fun layoutDependsOn(parent: CoordinatorLayout, child: Toolbar, dependency: View): Boolean {
        //依据recyclerView来
        return dependency.id == R.id.rv_index
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: Toolbar, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        //拦截滑动事件，在onNestedScroll方法中处理
        return true
    }

    //在这里处理滑动渐变状态栏透明度
    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, toolbar: Toolbar, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        val startOffset = 0
        val context = Mall.getConfiguration<Context>(GlobalKeys.APPLICATION_CONTEXT)
        val endOffset = context.resources.getDimensionPixelOffset(R.dimen.header_height) + MORE
        mOffset += dyConsumed
        when {
            mOffset <= startOffset -> toolbar.background.alpha = 0
            mOffset in (startOffset + 1)..(endOffset - 1) -> {
                val percent = (mOffset - startOffset).toFloat() / endOffset
                val alpha = Math.round(percent * 255)
                toolbar.background.alpha = alpha
            }
            mOffset >= endOffset -> toolbar.background.alpha = 255

        }
    }


}