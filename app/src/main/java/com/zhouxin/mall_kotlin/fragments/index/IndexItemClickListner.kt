package com.zhouxin.mall_kotlin.fragments.index

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.SimpleClickListener
import com.zhouxin.library.ui.recycler.MultipleFields
import com.zhouxin.library.ui.recycler.MultipleItemEntity
import com.zhouxin.mall_kotlin.fragments.MallMainFragment
import com.zhouxin.mall_kotlin.fragments.detail.GoodsDetailFragment

/**
 * @author zhouxin on 2020/5/15.
 * 首页点击事件
 */

class IndexItemClickListner private constructor(private val fragment: MallMainFragment) : SimpleClickListener() {

    companion object {
        fun create(fragment: MallMainFragment) : SimpleClickListener {
            return IndexItemClickListner(fragment)
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }

    override fun onItemChildLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val entity = baseQuickAdapter.data[position] as MultipleItemEntity
        val goodsId = entity.getField<Int>(MultipleFields.ID)
        val goodsDetailFragment = GoodsDetailFragment.create(goodsId)
        fragment.start(goodsDetailFragment)
    }

}