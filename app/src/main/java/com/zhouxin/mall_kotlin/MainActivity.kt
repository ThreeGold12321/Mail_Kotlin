package com.zhouxin.mall_kotlin

import android.os.Bundle
import com.zhouxin.library.activitys.ProxyActivity
import com.zhouxin.library.fragments.MallFragment
import com.zhouxin.mall_kotlin.fragments.MallMainFragment

class MainActivity : ProxyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    override fun setRootFragment(): MallFragment {
        return MallMainFragment()
    }

}
