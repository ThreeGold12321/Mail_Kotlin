package com.zhouxin.library.ui.recycler

import com.choices.divider.Divider
import com.choices.divider.DividerItemDecoration

/**
 * @author zhouxin on 2020/4/23.
 */
class DividerLookupImpl(private val color: Int, private val size: Int) : DividerItemDecoration.DividerLookup {
    //横向分割线
    override fun getHorizontalDivider(position: Int): Divider {
        return Divider.Builder()
                .color(color)
                .size(size)
                .build()
    }

    //纵向分割线
    override fun getVerticalDivider(position: Int): Divider {
        return Divider.Builder()
                .color(color)
                .size(size)
                .build()
    }
}