package com.zhouxin.library.global

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.Utils
import com.zhouxin.library.util.storage.MemoryStore

/**
 * @author zhouxin on 2020/4/13.
 */
object Mall {

    val configurator: Configurator
        get() = Configurator.instance

    fun init(context: Context): Configurator {
        MemoryStore.instance
                .addData(GlobalKeys.APPLICATION_CONTEXT, context.applicationContext)
        //初始化工具类
        Utils.init(context as Application?)
        return Configurator.instance
    }

    fun <T> getConfiguration(key: String): T {
        return configurator.getConfiguration(key)
    }

    fun <T> getConfiguration(key: Enum<GlobalKeys>): T {
        return getConfiguration(key.name)
    }
}