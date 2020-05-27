package com.zhouxin.library.fragments.bottom

import com.blankj.utilcode.util.ToastUtils
import com.zhouxin.library.fragments.MallFragment

/**
 * @author zhouxin on 2020/4/16.
 */

abstract class BottomItemFragment: MallFragment(){
    private var mTouchTime: Long = 0

    companion object {
        private const val WAIT_TIME = 2000L
    }

    override fun onBackPressedSupport(): Boolean {
        if (System.currentTimeMillis() - mTouchTime < WAIT_TIME) {
            _mActivity.finish()
        } else {
            mTouchTime = System.currentTimeMillis()
            ToastUtils.showShort("再点一次退出程序")
        }
        return true
    }

}