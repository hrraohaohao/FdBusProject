package com.hao.fdbus

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.hao.fdbus.web.AppWebSocketServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 *@author raohaohao
 *@data 2023/4/27
 *@version 1.0
 */
class BusManager : Service() {
    var mAppWebSocketServer: AppWebSocketServer? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            start()
        }
    }

    fun start() {
        mAppWebSocketServer = AppWebSocketServer(8081)
    }

}