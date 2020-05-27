package com.zhouxin.library.fragments.bottom

/**
 * @author zhouxin on 2020/4/16.
 * 底部bar和内容Fragment的一个Builder
 * 通过.build()来构建
 */
class ItemBuilder {

    //不能使用HashMap，因为是一个无序集合
    private val mItems = LinkedHashMap<BottomTabBean, BottomItemFragment>()

    companion object {
        internal fun builder(): ItemBuilder {
            return ItemBuilder()
        }
    }

    fun addItem(bean: BottomTabBean, fragment: BottomItemFragment):ItemBuilder {
        mItems[bean] = fragment
        return this
    }

    fun addItems(items: LinkedHashMap<BottomTabBean,BottomItemFragment>):ItemBuilder {
        mItems.putAll(items)
        return this
    }

    fun build():LinkedHashMap<BottomTabBean, BottomItemFragment> {
        return mItems
    }
}