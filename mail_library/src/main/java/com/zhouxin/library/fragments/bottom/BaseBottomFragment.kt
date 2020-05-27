package com.zhouxin.library.fragments.bottom

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.joanzapata.iconify.widget.IconTextView
import com.zhouxin.library.R
import com.zhouxin.library.adapters.MainPagerAdapter
import com.zhouxin.library.fragments.MallFragment

/**
 * @author zhouxin on 2020/4/16.
 * 整个Fragment的base（TableBar + Fragment）
 */
abstract class BaseBottomFragment : MallFragment(), View.OnClickListener, ViewPager.OnPageChangeListener {

    //底部Tab的icon和txt集合
    private val mTabBean = ArrayList<BottomTabBean>()
    //内容的fragment
    private val mItemFragments = ArrayList<BottomItemFragment>()
    //有序的Map<Tab,Fragment>
    private val mItems = LinkedHashMap<BottomTabBean, BottomItemFragment>()
    //当前选中的Fragment
    private var mCurrentFragment = 0
    //设置首页一打开就显示的页面
    private var mIndexFragment = 0
    //默认选中的颜色
    private var mSelectedColor = Color.RED
    //默认未选中的颜色
    private var mUnSelectedColor = Color.GRAY
    //底部Bar->LinerLayout
    private lateinit var mBottomBar: LinearLayoutCompat
    //内容区
    private lateinit var mViewPager: ViewPager
    //ViewPager的adapter
    private lateinit var mMainPagerAdapter: MainPagerAdapter

    override fun setLayout(): Any {
        return R.layout.fragment_bottom
    }


    /**
     * 设置内容
     */
    abstract fun setItems(builder: ItemBuilder): LinkedHashMap<BottomTabBean, BottomItemFragment>

    /**
     * 设置首页一打开就显示的页面
     */
    abstract fun setIndexFragment(): Int

    /**
     * 设置选中颜色
     */
    @ColorInt
    abstract fun setSelectColor(): Int

    /**
     * 设置未选中的颜色
     */
    abstract fun setUnSelectColor(): Int

    /**
     * 设置页面切换样式，如ZoomOutSlideTransformer()
     */
    abstract fun setTransformer(): ViewPager.PageTransformer?

    /**
     * 设置点击tab的时候要不要SmoothScroll
     */
    abstract fun setClickSmoothScroll(): Boolean


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIndexFragment = setIndexFragment()
        if (setSelectColor() != 0) {
            mSelectedColor = setSelectColor()
        }
        if (setUnSelectColor() != 0) {
            mUnSelectedColor = setUnSelectColor()
        }
        val builder = ItemBuilder.builder()
        val items = setItems(builder)
        mItems.putAll(items)
        for ((key, value) in mItems) {
            mTabBean.add(key)
            mItemFragments.add(value)
        }
        mMainPagerAdapter = MainPagerAdapter(activity!!.supportFragmentManager, mItemFragments)
    }

    override fun onBindView(saveInstanceState: Bundle?, rootView: View) {
        mBottomBar = findView(R.id.bottom_bar)
        mViewPager = findView(R.id.bottom_bar_fragment_container)
        val size = mItems.size
        for (i in 0 until size) {
            //根据mItems.size个数来构建icon和txt布局的View，并装进mBottomBar
            LayoutInflater.from(context).inflate(R.layout.bottom_item_icon_text, mBottomBar)
            //获取mBottomBar的子控件，子控件为RelativeLayout（里面装的icon和txt）
            val item = mBottomBar.getChildAt(i) as RelativeLayout
            //为了正确的辨认点击事件
            item.tag = i
            item.setOnClickListener(this)
            val itemIcon = item.getChildAt(0) as IconTextView
            val itemText = item.getChildAt(1) as AppCompatTextView
            //初始化相应的数据
            val bean = mTabBean[i]
            itemIcon.text = bean.icon
            itemText.text = bean.title
            if (i == mIndexFragment) {
                //设置默认被选中的那个item颜色
                itemIcon.setTextColor(mSelectedColor)
                itemText.setTextColor(mSelectedColor)
            }
        }
        mViewPager.addOnPageChangeListener(this)
        mViewPager.offscreenPageLimit = mItems.size
        if (setTransformer() != null) {
            mViewPager.setPageTransformer(false, setTransformer())
        }
        mViewPager.adapter = mMainPagerAdapter
        //因为用了ViewPager作为容器，就不用再使用supportDelegate来装fragments
//        val fragments = mItemFragments.toTypedArray<ISupportFragment>()
        //*fragments表示这个集合的所有
//        supportDelegate.loadMultipleRootFragment(R.id.bottom_bar_fragment_container, mIndexFragment, *fragments)
    }

    /**
     * 重置TabBar的颜色
     */
    private fun resetColor() {
        val count = mBottomBar.childCount
        for (i in 0 until count) {
            val item = mBottomBar.getChildAt(i) as RelativeLayout
            val itemIcon = item.getChildAt(0) as IconTextView
            val itemText = item.getChildAt(1) as AppCompatTextView
            itemIcon.setTextColor(mUnSelectedColor)
            itemText.setTextColor(mUnSelectedColor)
        }
    }

    /**
     * 修改选中的颜色
     */
    private fun changeColor(tabIndex: Int) {
        resetColor()
        val item = mBottomBar.getChildAt(tabIndex) as RelativeLayout
        val itemIcon = item.getChildAt(0) as IconTextView
        val itemText = item.getChildAt(1) as AppCompatTextView
        itemIcon.setTextColor(mSelectedColor)
        itemText.setTextColor(mSelectedColor)
    }

    /**
     * 修改选中的条目
     */
    private fun changeSelect(tabIndex: Int) {
        changeColor(tabIndex)
        //现在由ViewPager来管理，所有不用supportDelegate来管理
        //展示和隐藏内容的部分
//        supportDelegate.showHideFragment(mItemFragments[tabIndex], mItemFragments[mCurrentFragment])
        //先后顺序不能错
        mCurrentFragment = tabIndex
    }

    override fun onClick(v: View) {
        val tabIndex = v.tag as Int
        mViewPager.setCurrentItem(tabIndex, setClickSmoothScroll())
        changeSelect(tabIndex)
    }

    override fun onPageScrollStateChanged(p0: Int) {
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

    }

    override fun onPageSelected(p0: Int) {
        changeSelect(p0)
    }
}