package com.hao.fdbus

import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap

/**
 *@author raohaohao
 *@data 2023/4/27
 *@version 1.0
 * 缓存中心
 */
class BusCenter() {

    var mClassMap = ConcurrentHashMap<String, Class<*>>()

    var mMethodMap = ConcurrentHashMap<String, ConcurrentHashMap<String, Method>>()

    var mInstanceObjectMap = ConcurrentHashMap<String, Any>()


    fun register(zClass: Class<*>) {
        mClassMap[zClass.name] = zClass
        val methods = zClass.declaredMethods
        methods.forEach {
            var map = mMethodMap[zClass.name]
            val concurrentHashMap = map ?: let {
                var sMap = ConcurrentHashMap<String, Method>()
                mMethodMap[zClass.name] = sMap
                sMap
            }
            val key = getMethodParameters(it)
            concurrentHashMap[key] = it
        }
    }

    private fun getMethodParameters(method: Method): String {
        var sb = StringBuilder()
        sb.append(method.name)
        val parameterTypes = method.parameterTypes
        if (parameterTypes.isEmpty()) {
            return sb.toString()
        }
        parameterTypes.forEach {
            sb.append("-").append(it.name)
        }
        return sb.toString()
    }

}