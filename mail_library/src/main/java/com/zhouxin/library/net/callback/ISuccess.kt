package com.zhouxin.library.net.callback

/**
 * @author zhouxin on 2020/4/13.
 * 请求成功调用的接口
 */
interface ISuccess {
    fun onSuccess(response: String)
}