package com.lch.route.noaop

import android.content.Context
import android.widget.Toast

/**
 * Created by Administrator on 2017/9/13.
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

operator fun Context.plus(context: Context) {

}

