package com.zhouxin.library.net

import android.content.Context
import com.zhouxin.library.net.callback.*
import com.zhouxin.library.ui.loader.LoaderStyles
import java.util.*

/**
 * @author zhouxin on 2020/4/13.
 * 构建RestClient并初始化参数和回调
 */
class RestClientBuilder(
        private var url: String? = null,
        private var request: IRequest? = null,
        private var success: ISuccess? = null,
        private var failure: IFailure? = null,
        private var error: IError? = null,
        private var complete: IComplete? = null,
        private var context: Context? = null,
        private var loaderStyles: LoaderStyles? = null
) {
    private val mParams = WeakHashMap<String, Any>()

    fun url(url: String): RestClientBuilder {
        this.url = url
        return this
    }

    fun params(key: String, value: Any): RestClientBuilder {
        mParams[key] = value
        return this
    }

    fun params(params: WeakHashMap<String, Any>): RestClientBuilder {
        mParams.putAll(params)
        return this
    }

    fun onRequest(iRequest: IRequest): RestClientBuilder {
        this.request = iRequest
        return this
    }

    fun onSuccess(iSuccess: ISuccess): RestClientBuilder {
        this.success = iSuccess
        return this
    }

    fun onFailure(iFailure: IFailure): RestClientBuilder {
        this.failure = iFailure
        return this
    }

    fun onError(iError: IError): RestClientBuilder {
        this.error = iError
        return this
    }

    fun onComplete(iComplete: IComplete): RestClientBuilder {
        this.complete = iComplete
        return this
    }

    fun loader(context: Context, loaderStyles: LoaderStyles): RestClientBuilder {
        this.context = context
        this.loaderStyles = loaderStyles
        return this
    }

    fun loader(context: Context?): RestClientBuilder {
        this.context = context
        //默认loader
        this.loaderStyles = LoaderStyles.BallClipRotateMultipleIndicator
        return this
    }

    fun build(): RestClient {
        return RestClient(url, mParams, request, success, failure, error, complete, context, loaderStyles)
    }

}
