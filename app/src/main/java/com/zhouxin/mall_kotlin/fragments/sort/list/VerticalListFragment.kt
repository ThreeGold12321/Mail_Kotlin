package com.zhouxin.mall_kotlin.fragments.sort.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zhouxin.library.fragments.MallFragment
import com.zhouxin.library.net.RestClient
import com.zhouxin.library.net.callback.ISuccess
import com.zhouxin.mall_kotlin.R
import com.zhouxin.mall_kotlin.fragments.sort.SortFragment

/**
 * @author zhouxin on 2020/4/26.
 */

class VerticalListFragment : MallFragment() {

    private lateinit var mRecyclerView: RecyclerView

    override fun setLayout(): Any {
        return R.layout.fragment_vertical_list
    }

    override fun onBindView(saveInstanceState: Bundle?, rootView: View) {
        mRecyclerView = findView(R.id.rv_vertical_menu_list)
        val manager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = manager
        //屏蔽动画
        mRecyclerView.itemAnimator = null
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        RestClient.builder()
                .url("sort_list.php")
                .loader(context!!)
                .onSuccess(object : ISuccess {
                    override fun onSuccess(response: String) {
                        var data = VerticalListDataConverter()
                                .setJsonData(response)
                                .convert()
                        val adapter = SortRecyclerAdapter(data, parentFragment as SortFragment)
                        mRecyclerView.adapter = adapter
                    }

                })
                .build()
                .get()
    }

}