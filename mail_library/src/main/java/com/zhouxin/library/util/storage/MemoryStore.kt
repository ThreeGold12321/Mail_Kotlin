package com.zhouxin.library.util.storage

/**
 * @author zhouxin on 2020/4/10.
 * 全局控制的储存容器
 */
class MemoryStore private constructor() {
    private object Holder {
        internal val INSTANCE = MemoryStore()
    }

    companion object {
        val instance: MemoryStore
        get() =  Holder.INSTANCE

    }

    private val mDataMap = HashMap<String, Any>()

    fun addData(key: String, value: Any): MemoryStore {
        mDataMap[key] = value
        return this
    }

    fun addData(key: Enum<*>, value: Any): MemoryStore {
        addData(key.name, value)
        return this
    }

    fun <T> getData(key: String): T {
        @Suppress("UNCHECKED_CAST")
        return mDataMap[key] as T
    }

    fun <T> getData(key: Enum<*>): T {
        return getData(key.name)
    }
}