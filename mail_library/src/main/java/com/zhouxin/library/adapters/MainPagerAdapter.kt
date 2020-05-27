package com.zhouxin.library.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.zhouxin.library.fragments.bottom.BottomItemFragment

/**
 * @author zhouxin on 2020/4/21.
 */
class MainPagerAdapter constructor(fm: FragmentManager, var fragmentList: List<BottomItemFragment>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment {
       return fragmentList[p0]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }



}