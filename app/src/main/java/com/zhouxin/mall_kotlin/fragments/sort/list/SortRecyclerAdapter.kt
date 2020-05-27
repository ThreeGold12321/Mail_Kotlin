package com.zhouxin.mall_kotlin.fragments.sort.list

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.zhouxin.library.ui.recycler.*
import com.zhouxin.mall_kotlin.R
import com.zhouxin.mall_kotlin.fragments.sort.SortFragment
import com.zhouxin.mall_kotlin.fragments.sort.content.ContentFragment
import me.yokeyword.fragmentation.SupportHelper

/**
 * @author zhouxin on 2020/4/26.
 */
class SortRecyclerAdapter(data: List<MultipleItemEntity>, private val sortFragment: SortFragment) : MultipleRecyclerAdapter(data) {

    private var mPrePosition = 0

    init {
        //垂直菜单栏的布局文件
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list)
    }

    override fun convert(holder: MultipleViewHolder, entity: MultipleItemEntity) {
        super.convert(holder, entity)
        when (holder.itemViewType) {
            ItemType.VERTICAL_MENU_LIST -> {
                val text = entity.getField<String>(MultipleFields.TEXT)
                val isClick = entity.getField<Boolean>(MultipleFields.TAG)

                //取出控件
                val name = holder.getView<AppCompatTextView>(R.id.tv_vertical_item_name)
                val line = holder.getView<View>(R.id.view_line)
                val itemView = holder.itemView
                itemView.setOnClickListener {
                    val currentPosition = holder.adapterPosition
                    if (mPrePosition != currentPosition) {
                        //还原上一个item的颜色和状态
                        data[mPrePosition].setField(MultipleFields.TAG, false)
                        notifyItemChanged(mPrePosition)

                        //更新当前点击的item的状态
                        entity.setField(MultipleFields.TAG, true)
                        notifyItemChanged(currentPosition)
                        mPrePosition = currentPosition

                        val contentId = data[currentPosition].getField<Int>(MultipleFields.ID)
                        //切换Content
                        showContent(contentId)
                    }
                }
                //点击事件结束
                if (!isClick) {
                    line.visibility = View.INVISIBLE
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black))
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background))
                } else {
                    line.visibility = View.VISIBLE
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main))
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main))
                    itemView.setBackgroundColor(Color.WHITE)
                }
                holder.setText(R.id.tv_vertical_item_name,text)
            }
            else ->{

            }
        }
    }

    private fun switchContent(fragment:ContentFragment) {
        val contentFragment = SupportHelper.findFragment(sortFragment.childFragmentManager,ContentFragment::class.java)
        contentFragment.supportDelegate.replaceFragment(fragment,false)
    }

    private fun showContent(contentId:Int) {
        val fragment = ContentFragment.newInstance(contentId)
        switchContent(fragment)
    }

}