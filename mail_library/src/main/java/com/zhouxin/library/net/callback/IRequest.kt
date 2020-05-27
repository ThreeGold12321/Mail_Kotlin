package com.zhouxin.library.net.callback

/**
 * @author zhouxin on 2020/4/13.
 * 请求开始时和请求结束时调用的接口
 */
interface IRequest {
    fun onRequestStart()
    fun onRequestEnd()
}