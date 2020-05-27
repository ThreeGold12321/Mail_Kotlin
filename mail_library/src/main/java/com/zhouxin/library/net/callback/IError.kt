package com.zhouxin.library.net.callback

/**
 * @author zhouxin on 2020/4/13.
 * 请求错误调用的接口
 */
interface IError {
    fun onError(code: Int, msg: String)
}