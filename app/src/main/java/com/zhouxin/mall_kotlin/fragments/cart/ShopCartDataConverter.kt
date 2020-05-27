package com.zhouxin.mall_kotlin.fragments.cart

import com.alibaba.fastjson.JSON
import com.zhouxin.library.ui.recycler.DataConverter
import com.zhouxin.library.ui.recycler.MultipleFields
import com.zhouxin.library.ui.recycler.MultipleItemEntity
import com.zhouxin.mall_kotlin.R

/**
 * @author zhouxin on 2020/5/8.
 */
class ShopCartDataConverter : DataConverter(){
    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataList = ArrayList<MultipleItemEntity>()
        val dataArray = JSON.parseObject(jsonData).getJSONArray("data")
        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val thumb = data.getString("thumb")
            val desc = data.getString("desc")
            val title = data.getString("title")
            val id = data.getInteger("id")
            val count = data.getInteger("count")
            val price = data.getDouble("price")

            val entity = MultipleItemEntity
                    .builder()
                    .setField(MultipleFields.ITEM_TYPE, R.id.id_shop_cart)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.IMAGE_URL,thumb)
                    .setField(ShopCartItemFields.TITLE,title)
                    .setField(ShopCartItemFields.DESC,desc)
                    .setField(ShopCartItemFields.COUNT,count)
                    .setField(ShopCartItemFields.PRICE,price)
                    .setField(ShopCartItemFields.IS_SELECTED,false)
                    .setField(ShopCartItemFields.POSITION,i)
                    .build()
            dataList.add(entity)
        }
        return dataList
    }
}