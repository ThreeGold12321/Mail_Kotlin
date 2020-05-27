package com.zhouxin.library.ui.recycler

import android.support.annotation.ColorInt
import com.choices.divider.DividerItemDecoration

/**
 * @author zhouxin on 2020/4/23.
 * RecyclerView分割线
 */
class BaseDecoration private constructor(@ColorInt color: Int, size: Int) : DividerItemDecoration() {

    init {
        setDividerLookup(DividerLookupImpl(color, size))
    }

    companion object {
        fun create(@ColorInt color: Int, size: Int): BaseDecoration {
            return BaseDecoration(color, size)
        }
    }
}