package com.zhouxin.mall_kotlin.fragments.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatButton
import android.view.View
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.daimajia.androidanimations.library.YoYo
import com.joanzapata.iconify.widget.IconTextView
import com.youth.banner.Banner
import com.zhouxin.library.fragments.MallFragment
import com.zhouxin.library.net.RestClient
import com.zhouxin.library.net.callback.ISuccess
import com.zhouxin.library.ui.banner.BannerCreator
import com.zhouxin.library.ui.widget.CircleTextView
import com.zhouxin.mall_kotlin.MainActivity
import com.zhouxin.mall_kotlin.R
import com.zhouxin.mall_kotlin.fragments.detail.animation.BezierAnimation
import com.zhouxin.mall_kotlin.fragments.detail.animation.BezierUtil
import com.zhouxin.mall_kotlin.fragments.detail.animation.ScaleUpAnimator
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @author zhouxin on 2020/5/15.
 * 商品详情的Fragment
 */
class GoodsDetailFragment : MallFragment(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener, BezierUtil.AnimationListener {

    private var mGoodsId = -1
    private lateinit var mBanner: Banner
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var mBtnAddShopCar: AppCompatButton
    private lateinit var mIconGoodsBack: IconTextView
    private lateinit var mIconShopCart: IconTextView
    private lateinit var mGoodsThumbUrl: String
    private lateinit var mCircleTextView: CircleTextView
    private var mGoodsCount: Int = 0

    companion object {
        private const val ARG_GOODS_ID = "ARG_GOODS_ID"
        private val option = RequestOptions
                .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                .centerCrop()
                .dontAnimate()
                .override(100, 100)

        fun create(goodsId: Int): GoodsDetailFragment {
            val args = Bundle()
            args.putInt(ARG_GOODS_ID, goodsId)
            val fragment = GoodsDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            mGoodsId = args.getInt(ARG_GOODS_ID)
        }
    }

    private fun initData() {
        RestClient.builder()
                .url("goods_detail.php")
                .loader(context)
                .params("goods_id", mGoodsId)
                .onSuccess(object : ISuccess {
                    override fun onSuccess(response: String) {
                        val data = JSON.parseObject(response).getJSONObject("data")
                        initBanner(data)
                        initGoodsInfo(data)
                        initPager(data)
                        setShopCartCount(data)
                    }

                }).build()
                .get()
    }

    /**
     * 初始化TabLayout
     */
    private fun initTabLayout() {
        mTabLayout.tabMode = TabLayout.MODE_FIXED
        val context = context
        if (context != null) {
            mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.app_main))
        }
        mTabLayout.tabTextColors = ColorStateList.valueOf(Color.BLACK)
        mTabLayout.setBackgroundColor(Color.WHITE)
        //关联tabLayout和Viewpager
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun initPager(data: JSONObject) {
        val adapter = TabPagerAdapter(fragmentManager, data)
        mViewPager.adapter = adapter
    }

    /**
     * 初始化商品信息
     */
    private fun initGoodsInfo(data: JSONObject) {
        val goodsData = data.toJSONString()
        //关键方法
        supportDelegate.loadRootFragment(R.id.frame_goods_info, GoodsInfoFragment.create(goodsData))
    }

    /**
     * 初始化Banner
     */
    private fun initBanner(data: JSONObject) {
        val array = data.getJSONArray("banners")
        val size = array.size
        val images = ArrayList<String>()
        for (i in 0 until size) {
            images.add(array.getString(i))
        }
        BannerCreator.setDefault(mBanner, images)
    }

    /**
     * 初始化购物车数量
     */
    private fun setShopCartCount(data: JSONObject) {
        mGoodsThumbUrl = data.getString("thumb")
        if (mGoodsCount == 0) {
            mCircleTextView.visibility = View.GONE
        }
    }

    override fun setLayout(): Any {
        return R.layout.fragment_goods_detail
    }

    override fun onBindView(saveInstanceState: Bundle?, rootView: View) {
        mBanner = findView(R.id.detail_banner)
        mTabLayout = findView(R.id.tab_layout)
        mViewPager = findView(R.id.view_pager)
        val collapsingToolbarLayout = findView<CollapsingToolbarLayout>(R.id.collapsing_toolbar_detail)
        val appBarLayout = findView<AppBarLayout>(R.id.app_bar_detail)
        //设置滑到顶部时collapsingToolbar的颜色
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE)
        appBarLayout.addOnOffsetChangedListener(this)
        mBtnAddShopCar = findView(R.id.btn_add_shop_cart)
        mBtnAddShopCar.setOnClickListener(this)
        mIconGoodsBack = findView(R.id.icon_goods_back)
        mIconGoodsBack.setOnClickListener(this)
        mIconShopCart = findView(R.id.icon_shop_cart)
        mCircleTextView = findView(R.id.tv_shopping_cart_amount)
        mCircleTextView.setCircleBackground(Color.RED)
        initData()
        initTabLayout()
    }

    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {

    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.btn_add_shop_cart -> {
                onClickAddShopCart()
            }
            R.id.icon_goods_back -> {
                (context as MainActivity).onBackPressed()
            }
        }
    }

    private fun onClickAddShopCart() {
        val animImg = CircleImageView(context)
        Glide.with(this)
                .load(mGoodsThumbUrl)
                .apply(option)
                .into(animImg)
        BezierAnimation.addCart(this, mBtnAddShopCar, mIconShopCart, animImg, this)
    }

    //购物车动画结束
    override fun onAnimationEnd() {
        YoYo.with(ScaleUpAnimator())
                .duration(500)
                .playOn(mIconShopCart)
        //加入购物车需要调用接口
        RestClient.builder()
                .url("add_shop_cart_count.php")
                .loader(context)
                .onSuccess(object : ISuccess {
                    override fun onSuccess(response: String) {
                        val isAdded = JSON.parseObject(response).getBoolean("data")
                        if (isAdded) {
                            mGoodsCount++
                            mCircleTextView.visibility = View.VISIBLE
                            mCircleTextView.text = mGoodsCount.toString()
                        }
                    }

                })
                .params("count", mGoodsCount)
                .build()
                .post()
    }
}