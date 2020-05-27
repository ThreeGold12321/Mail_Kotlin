package com.zhouxin.mall_kotlin.fragments.detail

import android.support.v7.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zhouxin.library.ui.recycler.*
import com.zhouxin.mall_kotlin.R

/**
 * @author zhouxin on 2020/5/19.
 */
class DetailImageAdapter constructor(data:List<MultipleItemEntity>):MultipleRecyclerAdapter(data){

    companion object {
        private val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
    }

    init {
        addItemType(ItemType.GOODS_DETAIL_IMAGE,R.layout.item_image)
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity) {
        super.convert(holder, entity)
        val type = holder.itemViewType
        when(type) {
            R.id.id_goods_detail_image -> {
                val imageView = holder.getView<AppCompatImageView>(R.id.image_rv_item)
                val imageUrl = entity.getField<String>(MultipleFields.IMAGE_URL)
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView)
            }
        }
    }
}