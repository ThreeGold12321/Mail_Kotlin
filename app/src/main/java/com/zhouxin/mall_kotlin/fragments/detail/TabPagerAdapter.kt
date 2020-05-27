package com.zhouxin.mall_kotlin.fragments.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.alibaba.fastjson.JSONObject

/**
 * @author zhouxin on 2020/5/19.
 */
class TabPagerAdapter(fm: FragmentManager?, data: JSONObject) : FragmentStatePagerAdapter(fm) {

    private val mTabItems = ArrayList<String>()
    private val mPictures = ArrayList<ArrayList<String>>()

    init {
        val tabs = data.getJSONArray("tabs")
        val size = tabs.size
        for (i in 0 until size) {
            val eachTab = tabs.getJSONObject(i)
            val name = eachTab.getString("name")
            val pictureUrls = eachTab.getJSONArray("pictures")
            val eachTabPictureArray = ArrayList<String>()
            //存储每一个图片
            val pictureSize = pictureUrls.size
            for (j in 0 until pictureSize) {
                eachTabPictureArray.add(pictureUrls.getString(j))
            }
            mTabItems.add(name)
            mPictures.add(eachTabPictureArray)
        }
    }

    override fun getItem(pos: Int): Fragment? {
        if (pos == 0) {
            return ImageFragment.create(mPictures[0])
        } else if (pos == 1) {
            return ImageFragment.create(mPictures[1])
        }
        return null
    }

    override fun getCount(): Int {
        return mTabItems.size
    }

    /**
     * 设置tabLayout的名字
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return mTabItems[position]
    }

}