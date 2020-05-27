package com.zhouxin.mall_kotlin

import android.app.Application
import com.joanzapata.iconify.fonts.FontAwesomeModule
import com.joanzapata.iconify.fonts.IoniconsModule
import com.zhouxin.library.global.Mall

/**
 * @author zhouxin on 2020/4/13.
 */
class MallApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Mall.init(this)
                .withLoaderDelayed(0)
                .withIcon(FontAwesomeModule())
                .withIcon(IoniconsModule())
                .withAPiHost("http://mock.fulingjie.com/mock/api/")
                .configure()
    }


}