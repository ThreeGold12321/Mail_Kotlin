package com.zhouxin.mall_kotlin.fragments.cart

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.ViewStubCompat
import android.view.View
import com.joanzapata.iconify.widget.IconTextView
import com.zhouxin.library.fragments.bottom.BottomItemFragment
import com.zhouxin.library.net.RestClient
import com.zhouxin.library.net.callback.ISuccess
import com.zhouxin.mall_kotlin.R

/**
 * @author zhouxin on 2020/5/7.
 */
class ShopCartFragment : BottomItemFragment(), ICartTotalPriceListener, ICartItemSelectAllListener, View.OnClickListener {

    private lateinit var mAdapter: ShopCartAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTotalPrice: AppCompatTextView
    private lateinit var mSelectAllIcon: IconTextView
    private lateinit var mStubNoItem: ViewStubCompat

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        RestClient
                .builder()
                .url("shop_cart.php")
                .loader(context)
                .onSuccess(object : ISuccess {
                    override fun onSuccess(response: String) {
                        val data = ShopCartDataConverter()
                                .setJsonData(response)
                                .convert()
                        mAdapter = ShopCartAdapter(data)
                        mAdapter.setCartTotalPriceListener(this@ShopCartFragment)
                        mAdapter.setCartItemSelectAllListener(this@ShopCartFragment)
                        val manager = LinearLayoutManager(context)
                        mRecyclerView.layoutManager = manager
                        mRecyclerView.adapter = mAdapter
                        //初始化服务器选中商品的价格
                        mTotalPrice.text = mAdapter.totalPrice.toString()
                        //初始化是否全部选中
                        if (mAdapter.mIsSelectAll) {
                            mSelectAllIcon.setTextColor(ContextCompat.getColor(context!!, R.color.app_main))
                        } else {
                            mSelectAllIcon.setTextColor(Color.GRAY)
                        }

                    }
                })
                .build()
                .get()
    }

    override fun setLayout(): Any {
        return R.layout.fragment_shop_cart
    }

    override fun onBindView(saveInstanceState: Bundle?, rootView: View) {
        mRecyclerView = findView(R.id.rv_shop_cart)
        mTotalPrice = findView(R.id.tv_total_price)
        mSelectAllIcon = findView(R.id.icon_shop_car_select_all)
        mSelectAllIcon.setOnClickListener(this)
        findView<AppCompatTextView>(R.id.tv_top_shop_cart_clear).setOnClickListener(this)
        findView<AppCompatTextView>(R.id.tv_shop_cart_delete_item).setOnClickListener(this)
        mStubNoItem = findView(R.id.stub_no_item)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.icon_shop_car_select_all -> onClickSelectAll()
            R.id.tv_top_shop_cart_clear -> onClickClear()
            R.id.tv_shop_cart_delete_item -> onClickDelete()
        }
    }

    /**
     * 点击全选
     */
    private fun onClickSelectAll() {
        if (mAdapter.mIsSelectAll) {
            mSelectAllIcon.setTextColor(Color.GRAY)
            mTotalPrice.text = "0.0"
            mAdapter.setSelectAll(false)
        } else {
            mSelectAllIcon.setTextColor(ContextCompat.getColor(context!!, R.color.app_main))
            mTotalPrice.text = mAdapter.obtainTotalPrice().toString()
            mAdapter.setSelectAll(true)
        }
    }

    //清空购物车
    private fun onClickClear() {
        mAdapter.data.clear()
        mAdapter.notifyDataSetChanged()
        //适用于局部更新
//        mAdapter.notifyItemRangeChanged(0,mAdapter.itemCount)
        mTotalPrice.text = "0.0"
        mSelectAllIcon.setTextColor(Color.GRAY)
        checkItemCount()
    }

    //删除选中的
    private fun onClickDelete() {
        mAdapter.deleteSelected()
        mTotalPrice.text = "0.0"
        if (mAdapter.itemCount == 0) {
            mSelectAllIcon.setTextColor(Color.GRAY)
        }
        checkItemCount()
    }

    @SuppressLint("RestrictedApi")
    private fun checkItemCount(){
        val count = mAdapter.itemCount
        if(count == 0){
            //初始化ViewStub,不初始化的话不会显示
            val stubView = mStubNoItem.inflate()
            mRecyclerView.visibility = View.GONE
        }else {
            mRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onTotalPriceChanged(totalPrice: Double) {
        mTotalPrice.text = totalPrice.toString()
    }

    override fun onCartItemSelectAll(isSelectAll: Boolean) {
        if (isSelectAll) {
            mSelectAllIcon.setTextColor(ContextCompat.getColor(context!!, R.color.app_main))
        } else {
            mSelectAllIcon.setTextColor(Color.GRAY)
        }
    }
}