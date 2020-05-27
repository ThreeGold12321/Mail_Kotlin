package com.zhouxin.mall_kotlin.fragments

import android.graphics.Color
import android.support.v4.view.ViewPager
import com.zhouxin.library.fragments.bottom.BaseBottomFragment
import com.zhouxin.library.fragments.bottom.BottomItemFragment
import com.zhouxin.library.fragments.bottom.BottomTabBean
import com.zhouxin.library.fragments.bottom.ItemBuilder
import com.zhouxin.mall_kotlin.fragments.cart.ShopCartFragment
import com.zhouxin.mall_kotlin.fragments.index.IndexFragment
import com.zhouxin.mall_kotlin.fragments.sort.SortFragment

/**
 * @author zhouxin on 2020/4/20.
 */
class MallMainFragment : BaseBottomFragment() {

    override fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemFragment> {
        val items = LinkedHashMap<BottomTabBean, BottomItemFragment>()
        items[BottomTabBean("{fa-home}", "首页")] = IndexFragment()
        items[BottomTabBean("{fa-sort}", "分类")] = SortFragment()
        items[BottomTabBean("{fa-shopping-cart}", "购物车")] = ShopCartFragment()
        items[BottomTabBean("{fa-user}", "我的")] = SortFragment()
        return builder.addItems(items).build()
    }

    override fun setIndexFragment(): Int {
        return 0
    }

    override fun setSelectColor(): Int {
        return Color.parseColor("#ffff8800")
    }

    override fun setUnSelectColor(): Int {
        //return 0 表示使用默认颜色
        return 0
    }

    override fun setTransformer(): ViewPager.PageTransformer? {
//        return ZoomOutSlideTransformer()
        //返回null 表示不设置Transformer
        return null
    }

    override fun setClickSmoothScroll(): Boolean {
        return false
    }

}