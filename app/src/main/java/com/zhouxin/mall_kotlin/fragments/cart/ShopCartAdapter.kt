package com.zhouxin.mall_kotlin.fragments.cart

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.joanzapata.iconify.widget.IconTextView
import com.zhouxin.library.net.RestClient
import com.zhouxin.library.net.callback.ISuccess
import com.zhouxin.library.ui.recycler.*
import com.zhouxin.mall_kotlin.R

/**
 * @author zhouxin on 2020/5/8.
 */
class ShopCartAdapter internal constructor(data: List<MultipleItemEntity>) : MultipleRecyclerAdapter(data) {

    var mIsSelectAll = false
        private set
    var totalPrice = 0.0
        private set

    private lateinit var mICartTotalPriceListener: ICartTotalPriceListener
    fun setCartTotalPriceListener(totalPriceListener: ICartTotalPriceListener) {
        this.mICartTotalPriceListener = totalPriceListener
    }

    private lateinit var mICartItemSelectAllListener: ICartItemSelectAllListener
    fun setCartItemSelectAllListener(cartItemSelectAllListener: ICartItemSelectAllListener) {
        this.mICartItemSelectAllListener = cartItemSelectAllListener
    }

    init {
        addItemType(ItemType.SHOP_CART, R.layout.item_shop_cart)
        data.forEach {
            val entity = it
            val price = entity.getField<Double>(ShopCartItemFields.PRICE)
            val count = entity.getField<Int>(ShopCartItemFields.COUNT)
            val isSelected = entity.getField<Boolean>(ShopCartItemFields.IS_SELECTED)
            if (isSelected) {
                totalPrice += price * count
            }
        }
        //是否初始时就是全选中的状态
        run outside@{
            data.forEach {
                val entity = it
                if (!entity.getField<Boolean>(ShopCartItemFields.IS_SELECTED)) {
                    mIsSelectAll = false
                    return@outside
                }
                mIsSelectAll = true
            }
        }
    }

    companion object {
        private val OPTIONS = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .dontAnimate()
    }

    /**
     * 获取总价，用于购物出全选的时候,删除选中商品的时候
     */
    fun obtainTotalPrice(): Double {
        totalPrice = 0.0
        data.forEach {
            val entity = it
            val price = entity.getField<Double>(ShopCartItemFields.PRICE)
            val count = entity.getField<Int>(ShopCartItemFields.COUNT)
            totalPrice += price * count
        }
        return totalPrice
    }

    //设置全选
    fun setSelectAll(isSelectAll: Boolean){
        //取消全选时将价格清零
        if(!isSelectAll)
            totalPrice = 0.0
        mIsSelectAll = isSelectAll
        notifyDataSetChanged()
    }

    //删除选中的条目
    fun deleteSelected(){
        val deleteEntities = ArrayList<MultipleItemEntity>()
        data.forEach {
            if (it.getField(ShopCartItemFields.IS_SELECTED)) {
                deleteEntities.add(it)
            }
        }
        deleteEntities.forEach {
            data.remove(it)
        }
        totalPrice = 0.0
        notifyDataSetChanged()
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity) {
        super.convert(holder, entity)
        when (holder.itemViewType) {
            R.id.id_shop_cart -> {
                //取出值
                val thumb = entity.getField<String>(MultipleFields.IMAGE_URL)
                val title = entity.getField<String>(ShopCartItemFields.TITLE)
                val desc = entity.getField<String>(ShopCartItemFields.DESC)
                val count = entity.getField<Int>(ShopCartItemFields.COUNT)
                val price = entity.getField<Double>(ShopCartItemFields.PRICE)

                //取出控件
                val imgThumb = holder.getView<AppCompatImageView>(R.id.image_item_shop_cart)
                val tvTitle = holder.getView<AppCompatTextView>(R.id.tv_item_shop_cart_title)
                val tvDesc = holder.getView<AppCompatTextView>(R.id.tv_item_shop_cart_desc)
                val tvPrice = holder.getView<AppCompatTextView>(R.id.tv_item_shop_cart_price)
                val iconMinus = holder.getView<IconTextView>(R.id.icon_item_minus)
                val tvCount = holder.getView<AppCompatTextView>(R.id.tv_shop_cart_count)
                val iconPlus = holder.getView<IconTextView>(R.id.icon_item_plus)
                val iconIsSelected = holder.getView<IconTextView>(R.id.icon_item_shop_cart)

                //赋值
                tvTitle.text = title
                tvDesc.text = desc
                tvPrice.text = price.toString()
                tvCount.text = count.toString()
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb)

                //设置为没有选中
                entity.setField(ShopCartItemFields.IS_SELECTED, mIsSelectAll)

                val isSelected = entity.getField<Boolean>(ShopCartItemFields.IS_SELECTED)
                if (isSelected) {
                    iconIsSelected.setTextColor(ContextCompat.getColor(mContext, R.color.app_main))
                } else {
                    iconIsSelected.setTextColor(Color.GRAY)
                }

                //选中商品
                iconIsSelected.setOnClickListener {
                    val currentSelected = entity.getField<Boolean>(ShopCartItemFields.IS_SELECTED)
                    if (currentSelected) {
                        iconIsSelected.setTextColor(Color.GRAY)
                        entity.setField(ShopCartItemFields.IS_SELECTED, false)
                        totalPrice -= entity.getField<Double>(ShopCartItemFields.PRICE) * entity.getField<Int>(ShopCartItemFields.COUNT)
                        mICartTotalPriceListener.onTotalPriceChanged(totalPrice)
                    } else {
                        iconIsSelected.setTextColor(ContextCompat.getColor(mContext, R.color.app_main))
                        entity.setField(ShopCartItemFields.IS_SELECTED, true)
                        totalPrice += entity.getField<Double>(ShopCartItemFields.PRICE) * entity.getField<Int>(ShopCartItemFields.COUNT)
                        mICartTotalPriceListener.onTotalPriceChanged(totalPrice)
                    }

                    //判断是否全部选中
                    run outside@{
                        data.forEach {
                            val entity = it
                            if (!entity.getField<Boolean>(ShopCartItemFields.IS_SELECTED)) {
                                mIsSelectAll = false
                                return@outside
                            }
                            mIsSelectAll = true
                        }
                    }
                    mICartItemSelectAllListener.onCartItemSelectAll(mIsSelectAll)


                }

                //减商品
                iconMinus.setOnClickListener {
                    val currentCount = entity.getField<Int>(ShopCartItemFields.COUNT)
                    if (Integer.parseInt(tvCount.text.toString()) > 1) {
                        //加减事件之后调用一次API
                        RestClient
                                .builder()
                                .url("shop_cart_count.php")
                                .params("count", currentCount)
                                .onSuccess(object : ISuccess {
                                    override fun onSuccess(response: String) {
                                        var countNum = Integer.parseInt(tvCount.text.toString())
                                        countNum--
                                        tvCount.text = countNum.toString()
                                        entity.setField(ShopCartItemFields.COUNT, countNum)
                                        if (entity.getField(ShopCartItemFields.IS_SELECTED)) {
                                            totalPrice -= entity.getField<Double>(ShopCartItemFields.PRICE)
                                            mICartTotalPriceListener.onTotalPriceChanged(totalPrice)
                                        }
                                    }
                                })
                                .build()
                                .post()
                    }
                }

                //加商品
                iconPlus.setOnClickListener {
                    val currentCount = entity.getField<Int>(ShopCartItemFields.COUNT)
                    //加减事件之后调用一次API
                    RestClient
                            .builder()
                            .url("shop_cart_count.php")
                            .params("count", currentCount)
                            .onSuccess(object : ISuccess {
                                override fun onSuccess(response: String) {
                                    var countNum = Integer.parseInt(tvCount.text.toString())
                                    countNum++
                                    tvCount.text = countNum.toString()
                                    entity.setField(ShopCartItemFields.COUNT, countNum)
                                    if (entity.getField(ShopCartItemFields.IS_SELECTED)) {
                                        totalPrice += entity.getField<Double>(ShopCartItemFields.PRICE)
                                        mICartTotalPriceListener.onTotalPriceChanged(totalPrice)
                                    }
                                }
                            })
                            .build()
                            .post()
                }
            }
        }
    }

}