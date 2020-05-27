package com.zhouxin.mall_kotlin.fragments.sort.list

import com.alibaba.fastjson.JSON
import com.zhouxin.library.ui.recycler.DataConverter
import com.zhouxin.library.ui.recycler.ItemType
import com.zhouxin.library.ui.recycler.MultipleFields
import com.zhouxin.library.ui.recycler.MultipleItemEntity

/**
 * @author zhouxin on 2020/4/26.
 */

class VerticalListDataConverter : DataConverter() {

    override fun convert(): ArrayList<MultipleItemEntity> {
        val dataList = ArrayList<MultipleItemEntity>()
        var dataArray = JSON.parseObject(jsonData)
                .getJSONObject("data")
                .getJSONArray("list")

        val size = dataArray.size
        for (i in 0 until size) {
            val data = dataArray.getJSONObject(i)
            val id = data.getInteger("id")
            val name = data.getString("name")

            val entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)
                    .build()

            dataList.add(entity)
        }
        //默认设置第一个类型被选中
        dataList[0].setField(MultipleFields.TAG, true)
        return dataList
    }

}