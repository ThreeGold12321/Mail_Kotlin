package com.zhouxin.mall_kotlin.fragments.sort

import android.os.Bundle
import android.view.View
import com.zhouxin.library.fragments.bottom.BottomItemFragment
import com.zhouxin.mall_kotlin.R
import com.zhouxin.mall_kotlin.fragments.sort.content.ContentFragment
import com.zhouxin.mall_kotlin.fragments.sort.list.VerticalListFragment

/**
 * @author zhouxin on 2020/4/20.
 */
class SortFragment : BottomItemFragment() {
    override fun setLayout(): Any {
        return R.layout.fragment_sort
    }

    override fun onBindView(saveInstanceState: Bundle?, rootView: View) {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val listFragment = VerticalListFragment()
        val contentFragment = ContentFragment.newInstance(1)
        supportDelegate.loadRootFragment(R.id.vertical_list_container,listFragment)
        supportDelegate.loadRootFragment(R.id.sort_content_container,contentFragment)
    }
}