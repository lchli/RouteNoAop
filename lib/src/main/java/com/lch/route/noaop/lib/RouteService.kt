package com.lch.route.noaop.lib

/**
 * Created by Administrator on 2017/9/7.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RouteService(val value:String)
