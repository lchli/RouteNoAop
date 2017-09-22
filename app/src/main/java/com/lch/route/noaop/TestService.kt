package com.lch.route.noaop

import android.content.Context
import android.util.Log
import com.lch.route.noaop.lib.RouteEngine
import com.lch.route.noaop.lib.RouteMethod
import com.lch.route.noaop.lib.RouteService
import com.lch.route.noaop.lib.Router

/**
 * Created by Administrator on 2017/9/7.
 */
@RouteService("test")
class TestService : Router {

    override fun init(context: Context) {

    }

    @RouteMethod("t")
    fun tes(params: Map<String, String>) {
        Log.e("test", "i am a route test=============================$params")
        RouteEngine.context.toast("i am a route test=============================$params")
    }


}