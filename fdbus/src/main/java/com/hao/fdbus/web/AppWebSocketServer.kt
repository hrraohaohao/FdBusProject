package com.hao.fdbus.web

import android.util.Log
import org.java_websocket.WebSocket
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.handshake.ServerHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.URI

/**
 *@author raohaohao
 *@data 2023/4/27
 *@version 1.0
 */
class AppWebSocketServer(port: Int) : WebSocketServer(InetSocketAddress(port)) {
    private val TAG = AppWebSocketServer::javaClass.name


    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        Log.i(TAG, "onOpen: ${conn?.remoteSocketAddress?.address?.hostAddress}")
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        Log.i(TAG, "onClose: ")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        Log.i(TAG, "服务端收到: $message")
        conn?.send("so many girls")
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        Log.e(TAG, "onError: ${ex?.message}")

    }

    override fun onStart() {
        Log.e(TAG, "onStart")
    }


    fun dealRequest(request: String) {


    }

}