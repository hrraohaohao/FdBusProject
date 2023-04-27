package com.hao.fdbus.web

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.nio.ByteBuffer
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 *@author raohaohao
 *@data 2023/4/27
 *@version 1.0
 */
class HaoWebSocketClient(url: URI, var readLock: ReentrantLock, var condition: Condition) :
    WebSocketClient(url) {
    var TAG = HaoWebSocketClient::class.java.name

    var result: String = ""

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.i(TAG, "onOpen:$ 链接")
    }

    override fun onMessage(message: String?) {
        Log.i(TAG, "收到客户端请求 onMessage: $message")
        if (message != null) {
            result = message
        }
        try {
            readLock.unlock()
            condition.signal()
        } catch (e: Exception) {
            Log.e(TAG, "onMessage: 锁异常")
            e.printStackTrace()
        }
    }


    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.i(TAG, "onClose")
    }

    override fun onError(ex: Exception?) {
        Log.e(TAG, "onError: ${ex?.message}")
    }


    fun getResponse(): String {
        return result
    }


}