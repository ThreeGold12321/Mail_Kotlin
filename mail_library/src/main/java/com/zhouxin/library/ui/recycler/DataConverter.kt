package com.zhouxin.library.ui.recycler

/**
 * @author zhouxin on 2020/4/23.
 */
abstract class DataConverter {
    protected val mEntities = ArrayList<MultipleItemEntity>()

    private lateinit var mJsonData: String

    protected val jsonData: String
        get() {
            if (mJsonData.isEmpty()) {
                throw NullPointerException("Data is NULL!")
            }
            return mJsonData
        }

    abstract fun convert(): ArrayList<MultipleItemEntity>

    fun setJsonData(json: String): DataConverter {
        this.mJsonData = json
        return this
    }

    fun clearData() {
        mEntities.clear()
    }
}