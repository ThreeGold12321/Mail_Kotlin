package com.zhouxin.mall_kotlin.fragments.sort.content

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.zhouxin.library.fragments.MallFragment
import com.zhouxin.library.net.RestClient
import com.zhouxin.library.net.callback.ISuccess
import com.zhouxin.mall_kotlin.R


/**
 * @author zhouxin on 2020/4/26.
 */
class ContentFragment : MallFragment() {

    private lateinit var mRecyclerView: RecyclerView
    private var mContentID = -1

    //简单工厂
    companion object {
        private val ARGS_CONTENT_ID = "CONTENT_ID"

        fun newInstance(contentId: Int): ContentFragment {
            val args = Bundle()
            args.putInt(ARGS_CONTENT_ID, contentId)
            val fragment = ContentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if(args != null) {
            mContentID = args.getInt(ARGS_CONTENT_ID)
        }
    }

    override fun setLayout(): Any {
        return R.layout.fragment_list_content
    }

    override fun onBindView(saveInstanceState: Bundle?, rootView: View) {
        mRecyclerView = findView(R.id.rv_list_content)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView.layoutManager = manager
        //初始化数据
        initData()
    }

    private fun initData() {
        RestClient.builder()
                .url("sort_content_list.php?contentId=$mContentID")
                .onSuccess(object:ISuccess{
                    override fun onSuccess(response: String) {
                        val data = SectionDateConverter().convert(response)
                        val sectionAdapter = SectionAdapter(R.layout.item_section_content,R.layout.item_section_header,data)
                        mRecyclerView.adapter = sectionAdapter
                    }

                })
                .build()
                .get()

    }
}