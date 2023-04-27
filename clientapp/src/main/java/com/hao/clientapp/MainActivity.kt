package com.hao.clientapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.databinding.DataBindingUtil
import com.hao.clientapp.databinding.ActivityMainBinding
import com.hao.fdbus.IPCManager
import com.hao.fdbusproject.interfaces.IHaoService
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.name

    private lateinit var binding: ActivityMainBinding
    var ipcManager: IPCManager? = null
    var mIHaoService: IHaoService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ipcManager = IPCManager.Companion.getInstance(this)
    }

    fun connect(view: View) {
        Log.i(TAG, "connect: ")
        ipcManager?.connect("ws://192.168.32.108:", 8081)
    }

    fun find(view: View) {
        mIHaoService = ipcManager?.findService(IHaoService::class.java, 11, "12")
    }

    fun request(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            sendRequest()
        }
    }

    private suspend fun sendRequest() = coroutineScope {
        Log.i(TAG, "sendRequest: ${mIHaoService?.getGirls()}")
    }


    override fun onDestroy() {
        super.onDestroy()
        ipcManager?.disConnect()
    }

}