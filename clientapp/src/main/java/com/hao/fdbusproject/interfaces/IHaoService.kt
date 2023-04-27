package com.hao.fdbusproject.interfaces

import com.hao.fdbus.annotion.ClassId

/**
 *@author raohaohao
 *@data 2023/4/27
 *@version 1.0
 */
@ClassId("com.hao.fdbusproject.interfaces.IHaoService")
interface IHaoService {
    fun getGirls(): String
}