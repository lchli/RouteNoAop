package com.lch.route.noaop.lib

import android.util.Log

/**
 * Created by Administrator on 2017/9/7.
 */
interface DegradeHandler {

    fun onNotFound(path: String, reason: String) {
        Log.e("DegradeHandler", "route not found,it be degrade,path=$path,reason=$reason")
    }
}