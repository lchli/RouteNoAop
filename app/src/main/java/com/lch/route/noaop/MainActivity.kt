package com.lch.route.noaop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.lch.route.noaop.lib.DegradeHandler
import com.lch.route.noaop.lib.RouteEngine
import kotlinx.coroutines.experimental.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            e.printStackTrace()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RouteEngine.init(this,"route.cfg")
        RouteEngine.init(this, HomeService::class.java, TestService::class.java)
        RouteEngine.setDegradeHandler(object : DegradeHandler {
            override fun onNotFound(path: String, reason: String) {
                super.onNotFound(path, reason)
                toast(reason)
            }
        })

        RouteEngine.route(MT_ONCREATE)
        RouteEngine.route(PREG_ONCREATE, mapOf("age" to "18"))

        launch(CommonPool){
            Log.e("tag",Thread.currentThread().name)
            val result = fetchPosts()
            Log.e("tag",result.await().toString())
        }

        toast("8888")
    }


    fun fetchPosts(): Deferred<List<Any>> {
        return async(CommonPool) {
            delay(5_000)
            listOf("1")
        }
    }

}
