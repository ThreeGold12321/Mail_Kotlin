package com.zhouxin.mall_kotlin.fragments.sort.content

import android.support.v7.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhouxin.mall_kotlin.R

/**
 * @author zhouxin on 2020/4/26.
 */
class SectionAdapter(layoutResId:Int, sectionHeadResId:Int,data:List<SectionBean>) :BaseSectionQuickAdapter<SectionBean,BaseViewHolder>(layoutResId,sectionHeadResId,data){

    override fun convertHead(helper: BaseViewHolder, item: SectionBean) {
        helper.setText(R.id.header,item.header)
        helper.setVisible(R.id.more,item.isMore)
//        helper.addOnClickListener(R.id.more)
    }

    override fun convert(helper: BaseViewHolder, item: SectionBean) {
        val thumb = item.t.goodsThumb
        val name = item.t.goodsName
        helper.setText(R.id.tv,name)
        val goodsImageView = helper.getView<AppCompatImageView>(R.id.iv)
        Glide.with(mContext)
                .load(thumb)
                .into(goodsImageView)
    }

}