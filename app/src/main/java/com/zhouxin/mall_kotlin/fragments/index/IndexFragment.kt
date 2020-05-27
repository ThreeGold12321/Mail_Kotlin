package com.zhouxin.mall_kotlin.fragments.index

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.zhouxin.library.fragments.bottom.BottomItemFragment
import com.zhouxin.library.net.RestClient
import com.zhouxin.library.net.callback.ISuccess
import com.zhouxin.library.ui.recycler.BaseDecoration
import com.zhouxin.library.ui.recycler.MultipleRecyclerAdapter
import com.zhouxin.mall_kotlin.R
import com.zhouxin.mall_kotlin.fragments.MallMainFragment

/**
 * @author zhouxin on 2020/4/20.
 */
class IndexFragment : BottomItemFragment() {

    private lateinit var mRecyclerView: RecyclerView

    override fun setLayout(): Any {
        return R.layout.fragment_index
    }

    override fun onBindView(saveInstanceState: Bundle?, rootView: View) {
        mRecyclerView = findView(R.id.rv_index)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //让toolBar初始状态为透明
        val toolbar = view.findViewById<Toolbar>(R.id.tb_index)
        toolbar.background.alpha = 0
    }

    //惰性的加载数据和UI
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initRecyclerView()
        initData()
    }

    private fun initRecyclerView() {
        val manager = GridLayoutManager(context, 4)
        mRecyclerView.layoutManager = manager
        mRecyclerView.addItemDecoration(BaseDecoration.create(Color.LTGRAY, 5))
    }

    private fun initData() {
        RestClient.builder()
                .url("index.php")
                .loader(context!!)
                .onSuccess(object : ISuccess {
                    override fun onSuccess(response: String) {
                        val adapter = MultipleRecyclerAdapter.create(IndexDataConverter().setJsonData(response))
                        mRecyclerView.adapter = adapter
                        val mallBottom = preFragment as MallMainFragment
                        mRecyclerView.addOnItemTouchListener(IndexItemClickListner.create(mallBottom))
                    }
                })
                .build()
                .get()
    }
}