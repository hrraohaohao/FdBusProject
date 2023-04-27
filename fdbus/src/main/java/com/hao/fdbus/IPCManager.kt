package com.hao.fdbus

import android.content.Context
import android.util.Log
import com.hao.fdbus.web.HaoWebSocketClient
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.net.URI
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 *@author raohaohao
 *@data 2023/4/27
 *@version 1.0
 */
class IPCManager(var mContext: Context) {

    private val TAG = IPCManager::class.java.name

    private var mBusCenter: BusCenter = BusCenter()
    private var myWebSocketClient: HaoWebSocketClient? = null

    private val readLock: ReentrantLock by lazy {
        ReentrantLock()
    }
    private val condition: Condition by lazy {
        readLock.newCondition()
    }

    companion object {
        @Volatile
        private var instance: IPCManager? = null
        fun getInstance(mContext: Context) =
            instance ?: synchronized(this) {
                instance ?: IPCManager(mContext).also { instance = it }
            }
    }

    //服务链接
    fun connect(ip: String, port: Int) {
        connectSocketServer(ip, port)
    }

    //服务断开
    fun disConnect() {
        myWebSocketClient?.close()
    }

    //服务注册
    fun register(zClass: Class<*>) {
        mBusCenter.register(zClass)
    }

    //服务发现
    fun <T> findService(zClass: Class<T>, vararg parameters: Any): T {
        sendRequest(zClass, null, parameters)
        return getProxy(zClass)
    }

    private fun <T> getProxy(zClass: Class<T>): T {
        val classLoader = mContext.classLoader
        return Proxy.newProxyInstance(classLoader,
            arrayOf(zClass),
            BusServiceInvokeHandler(instance, zClass)) as T
    }

    fun <T> sendRequest(
        zClass: Class<T>,
        method: Method?,
        parameters: Array<out Any>?,
    ): String? {
        var sendString = ""
        parameters?.forEach {
            sendString += "-$it"
        }
        myWebSocketClient?.send(sendString)

        readLock.lock()
        condition.await()
        try {
            var result = myWebSocketClient?.getResponse()
            Log.i(TAG, "sendRequest: $result")
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.e(TAG, "sendRequest: 没有获取到值")
        return null
    }


    private fun connectSocketServer(ip: String, port: Int) = runBlocking {
        var url = URI(ip + port)
        myWebSocketClient = HaoWebSocketClient(url, readLock, condition)
        myWebSocketClient?.connect()
    }

}