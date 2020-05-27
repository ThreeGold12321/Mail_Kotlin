package com.zhouxin.library.net

import com.zhouxin.library.global.GlobalKeys
import com.zhouxin.library.global.Mall
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author zhouxin on 2020/4/13.
 * 创建Retrofit的各个实例
 */
object RestCreator {
   private object OkhttpClientHolder {
       private const val TIME_OUT = 60
       private val BUILDER = OkHttpClient.Builder()
       internal val OK_HTTP_CLIENT = BUILDER
               .connectTimeout(TIME_OUT.toLong(),TimeUnit.SECONDS)
               .build()
   }

    private object RetrofitHolder {
        private val BASE_URL = Mall.getConfiguration<String>(GlobalKeys.API_HOST)
        internal val RETROFIT_CLIENT = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkhttpClientHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }

    private object RestServiceHolder {
        internal val REST_SERVICE = RetrofitHolder
                .RETROFIT_CLIENT
                .create(RestService::class.java)
    }

    val restService: RestService
        get() = RestServiceHolder.REST_SERVICE
}