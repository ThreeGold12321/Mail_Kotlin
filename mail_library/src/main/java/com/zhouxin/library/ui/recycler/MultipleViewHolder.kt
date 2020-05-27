package com.zhouxin.library.ui.recycler

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author zhouxin on 2020/4/23.
 */
class MultipleViewHolder constructor(view: View) : BaseViewHolder(view) {

    companion object {
        fun create(view: View): MultipleViewHolder {
            return MultipleViewHolder(view)
        }
    }
}