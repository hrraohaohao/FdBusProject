package com.hao.fdbus

import android.util.Log
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 *@author raohaohao
 *@data 2023/4/27
 *@version 1.0
 */
class BusServiceInvokeHandler<T>(var ipcManager: IPCManager?, var zClass: Class<T>) :
    InvocationHandler {
    private val TAG = BusServiceInvokeHandler::class.java.name

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        if (method?.name?.contains("toString") == true) {
            return "proxy"
        }
        Log.i(TAG, "invoke: ${method?.name}")
        val response = ipcManager?.sendRequest(zClass, method, args)
        if (response.isNullOrBlank()) {
            return response
        }
        return null
    }
}