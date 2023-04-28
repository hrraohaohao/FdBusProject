package com.hao.fdbusproject

import com.hao.fdbusproject.interfaces.IHaoService

/**
 *@author raohaohao
 *@data 2023/4/27
 *@version 1.0
 */
class HaoService : IHaoService {


    override fun getGirls(): String {
        return "so many girls"
    }
}