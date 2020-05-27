package com.zhouxin.library.fragments

import com.blankj.utilcode.util.ToastUtils

/**
 * @author zhouxin on 2020/4/15.
 */
abstract class MallFragment : BaseFragment() {
    private var mTouchTime: Long = 0

    companion object {
        private const val WAIT_TIME = 2000L
    }

    protected fun exitWithDoubleClick(): Boolean {
        if (System.currentTimeMillis() - mTouchTime < WAIT_TIME) {
            _mActivity.finish()
        } else {
            mTouchTime = System.currentTimeMillis()
            ToastUtils.showShort("再点一次退出程序")
        }
        return true
    }

    //后面添加关于文件或者Camera权限的内容
}