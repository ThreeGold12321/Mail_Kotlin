package com.zhouxin.mall_kotlin.fragments.detail

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zhouxin.library.fragments.MallFragment
import com.zhouxin.library.ui.recycler.ItemType
import com.zhouxin.library.ui.recycler.MultipleFields
import com.zhouxin.library.ui.recycler.MultipleItemEntity
import com.zhouxin.mall_kotlin.R

/**
 * @author zhouxin on 2020/5/19.
 * 商品详情里面的图片Fragment(包含商品详情和参数规格)
 */
class ImageFragment : MallFragment() {

    private lateinit var mRecyclerView: RecyclerView

    companion object {
        private const val ARG_PICTURES = "ARG_PICTURES"

        fun create(pictures: ArrayList<String>): ImageFragment {
            val args = Bundle()
            args.putStringArrayList(ARG_PICTURES,pictures)
            val fragment = ImageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun initImages(){
        val arguments = arguments
        if(arguments != null) {
            val pictures = arguments.getStringArrayList(ARG_PICTURES)
            val entities = ArrayList<MultipleItemEntity>()
            val size:Int
            if(pictures != null){
                size = pictures.size
                for(i in 0 until size){
                    val imageUrl = pictures[i]
                    val entity = MultipleItemEntity.builder()
                            .setItemType(ItemType.GOODS_DETAIL_IMAGE)
                            .setField(MultipleFields.IMAGE_URL,imageUrl)
                            .build()
                    entities.add(entity)
                    val adapter = DetailImageAdapter(entities)
                    mRecyclerView.adapter = adapter
                }
            }

        }
    }

    override fun setLayout(): Any {
        return R.layout.fragment_image
    }

    override fun onBindView(saveInstanceState: Bundle?, rootView: View) {
        mRecyclerView = findView(R.id.rv_image_container)
        val manager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = manager
        initImages()
    }
}