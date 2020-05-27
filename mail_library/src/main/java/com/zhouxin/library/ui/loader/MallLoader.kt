package com.zhouxin.library.ui.loader

import android.content.Context
import android.support.v7.app.AppCompatDialog
import android.view.Gravity
import com.blankj.utilcode.util.ScreenUtils
import com.wang.avi.AVLoadingIndicatorView
import com.wang.avi.Indicator
import com.wang.avi.indicators.BallClipRotateMultipleIndicator
import com.zhouxin.library.R

/**
 * @author zhouxin on 2020/4/14.
 * loading Dialog
 */
object MallLoader {
    //缩放级别
    private const val LOADER_SIZE_SCALE = 8
    //偏移级别
    private const val LOADER_OFFSET_SCALE = 10
    //Dialog容器，用于管理Dialog
    private val LOADERS = ArrayList<AppCompatDialog>()
    //默认loader
    private val DEFAULT_LOADER = BallClipRotateMultipleIndicator()

    private fun createDialog(
            context: Context?
            , avLoadingIndicatorView: AVLoadingIndicatorView
    ): AppCompatDialog {
        val dialog = AppCompatDialog(context, R.style.dialog)
        //获取屏幕宽高
        val screenWidth = ScreenUtils.getScreenWidth()
        val screenHeight = ScreenUtils.getScreenHeight()
        val dialogWindow = dialog.window
        dialog.setContentView(avLoadingIndicatorView)
        if (dialogWindow != null) {
            val lp = dialogWindow.attributes
            lp.width = screenWidth / LOADER_SIZE_SCALE
            lp.height = screenHeight / LOADER_SIZE_SCALE
            lp.height = lp.height + screenHeight / LOADER_OFFSET_SCALE
            lp.gravity = Gravity.CENTER
        }
        LOADERS.add(dialog)
        return dialog
    }

    fun showLoading(context: Context?, type: Enum<LoaderStyles>) {
        showLoading(context, type.name)
    }

    fun showLoading(context: Context?, type: String) {
        val avLoadingIndicatorView = AVLoadingIndicatorView(context)
        avLoadingIndicatorView.setIndicator(type)
        createDialog(context, avLoadingIndicatorView).show()
    }

    fun showLoading(context: Context?, indicator: Indicator = DEFAULT_LOADER) {
        val avLoadingIndicatorView = AVLoadingIndicatorView(context)
        avLoadingIndicatorView.indicator = indicator
        createDialog(context, avLoadingIndicatorView)
    }

    fun stopLoading(){
        for (dialog in LOADERS) {
            if(dialog.isShowing) {
                dialog.cancel()
            }
        }
        LOADERS.clear()
    }
}