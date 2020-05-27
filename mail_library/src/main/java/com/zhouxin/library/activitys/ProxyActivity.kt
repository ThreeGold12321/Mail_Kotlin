package com.zhouxin.library.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.zhouxin.library.R
import com.zhouxin.library.fragments.MallFragment
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportActivity
import me.yokeyword.fragmentation.SupportActivityDelegate
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * @author zhouxin on 2020/4/15.
 */
abstract class ProxyActivity : AppCompatActivity(), ISupportActivity {

    private lateinit var mDelegate: SupportActivityDelegate

    abstract fun setRootFragment(): MallFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate = SupportActivityDelegate(this)
        mDelegate.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    private fun initContainer(savedInstanceState: Bundle?) {
        val container = FrameLayout(this)
        container.id = R.id.fragment_container
        setContentView(container)
        if (savedInstanceState == null) {
            mDelegate.loadRootFragment(R.id.fragment_container, setRootFragment())
        }
    }

    override fun onDestroy() {
        mDelegate.onDestroy()
        super.onDestroy()
        //内存回收
        System.gc()
        System.runFinalization()
    }

    override fun getSupportDelegate(): SupportActivityDelegate {
        return mDelegate
    }

    override fun post(runnable: Runnable?) {
        mDelegate.post(runnable)
    }

    override fun extraTransaction(): ExtraTransaction {
        return mDelegate.extraTransaction()
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return mDelegate.fragmentAnimator
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        mDelegate.fragmentAnimator = DefaultHorizontalAnimator()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return mDelegate.onCreateFragmentAnimator()
    }

    override fun onBackPressedSupport() {
        mDelegate.onBackPressedSupport()
    }

    override fun onBackPressed() {
        mDelegate.onBackPressed()
    }
}