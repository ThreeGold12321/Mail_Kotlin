package com.zhouxin.library.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.support.annotation.ColorInt
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 * @author zhouxin on 2020/5/20.
 */
class CircleTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {

    private val mPaint = Paint()
    private val mFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

    init {
        mPaint.color = Color.WHITE
        mPaint.isAntiAlias = true
    }

    fun setCircleBackground(@ColorInt color: Int) {
        mPaint.color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = maxHeight
        val max = Math.max(width,height)
        setMeasuredDimension(max,max)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawFilter = mFilter
        canvas.drawCircle((width/2).toFloat(),(height/2).toFloat(),(Math.max(width,height)/2).toFloat(),mPaint)
        super.onDraw(canvas)
    }
}