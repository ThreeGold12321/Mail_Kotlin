package com.zhouxin.library.net.callback

import android.os.Handler
import com.zhouxin.library.global.GlobalKeys
import com.zhouxin.library.global.Mall
import com.zhouxin.library.ui.loader.LoaderStyles
import com.zhouxin.library.ui.loader.MallLoader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author zhouxin on 2020/4/13.
 */
class RequestCallbacks(
        private val request: IRequest?,
        private val success: ISuccess?,
        private val failure: IFailure?,
        private val error: IError?,
        private val complete: IComplete?,
        private val loaderStyle: LoaderStyles?
) : Callback<String> {
    override fun onResponse(call: Call<String>?, response: Response<String>?) {
        if (call != null && response != null) {
            //请求是否成功的
            if (response.isSuccessful) {
                //请求是否执行了的
                if (call.isExecuted) {
                    if (success != null) {
                        if (response.body() != null) {
                            success.onSuccess(response.body()!!)
                        }
                    }
                }
            } else {
                error?.onError(response.code(), response.message())
            }
        }
        onRequestFinish()
    }

    private fun onRequestFinish() {
        val delete = Mall.getConfiguration<Long>(GlobalKeys.LOADER_DELAYED)
        if (loaderStyle != null) {
            HANDLER.postDelayed({ MallLoader.stopLoading() }, delete)
        }
    }

    override fun onFailure(call: Call<String>?, t: Throwable?) {
        failure?.OnFailure()
        request?.onRequestEnd()
    }

    companion object {
        private val HANDLER = Mall.getConfiguration<Handler>(GlobalKeys.HANDLER)
    }

}