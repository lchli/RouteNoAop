package com.lch.route.noaop.lib

/**
 * Created by Administrator on 2017/9/7.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RouteMethod(val value:String)