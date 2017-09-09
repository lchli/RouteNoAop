package com.lch.route.noaop

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.lch.route.noaop.lib.DegradeHandler
import com.lch.route.noaop.lib.RouteEngine

class MainActivity : AppCompatActivity() {

    fun Context.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

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

        RouteEngine.route("myrp://com.lch.test/home/he")
        RouteEngine.route("myrp://com.lch.test/test/t")
    }


}
