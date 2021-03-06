package com.lch.route.noaop.lib

import android.content.Context
import android.net.Uri
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.properties.Delegates

/**
 * Created by Administrator on 2017/9/7.
 */
object RouteEngine {

    private val modToClassObject: MutableMap<String, Router> = mutableMapOf()
    private val methodCache: MutableMap<String, String> = mutableMapOf()
    private val TAG = "RouteEngine"
    private var mDegradeHandler: DegradeHandler? = null
    var context: Context by Delegates.notNull()


    fun init(ctx: Context, configName: String) {
        context = ctx

        val ins = try {
            context.assets.open(configName)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }
        val reader = BufferedReader(InputStreamReader(ins))

        while (true) {
            var className: String = reader.readLine() ?: break
            className = className.trim()
            val clas = Class.forName(className)
            val anno = clas.getAnnotation(RouteService::class.java)
            if (anno != null) {
                val router = clas.newInstance() as? Router ?: throw RuntimeException("route service[$className] must implements Router interface. ")
                router.init(ctx)
                modToClassObject.put(anno.value, router)
            }
        }

        reader.close()
    }

    fun init(ctx: Context, vararg moduleClasses: Class<*>) {
        context = ctx

        for (cls in moduleClasses) {
            val anno = cls.getAnnotation(RouteService::class.java)
            if (anno != null) {
                val router = cls.newInstance() as? Router ?: throw RuntimeException("route service[${cls.name}] must implements Router interface. ")
                router.init(ctx)
                modToClassObject.put(anno.value, router)
            }
        }

    }


    fun setDegradeHandler(degradeHandler: DegradeHandler) {

        mDegradeHandler = degradeHandler
    }


    fun route(path: String, params: Map<String, String>? = null): Any? {
        try {

            val uri = Uri.parse(path)
            val segs = uri.pathSegments
            if (segs == null || segs.size != 2) {
                val msg = "route path is not valid:$path"
                Logg.e(TAG, msg)
                notFound(path, msg)
                return null
            }
            val moduleNameInPath = segs[0]
            val methodNameInPath = segs[1]

            val queryNames = uri.queryParameterNames
            val map = mutableMapOf<String, String>()
            if (queryNames != null && !queryNames.isEmpty()) {
                for (param in queryNames) {
                    map.put(param, uri.getQueryParameter(param))
                }
            }
            if (params != null) {
                map.putAll(params)
            }

            val router = modToClassObject[moduleNameInPath]
            if (router == null) {
                val msg = "route cannot find match class $moduleNameInPath in $path"
                Logg.e(TAG, msg)
                notFound(path, msg)
                return null
            }
            val clazz = router.javaClass

            val methodPath = "$moduleNameInPath/$methodNameInPath"
            val realMethodName = methodCache[methodPath] ?: findMethodInClass(methodNameInPath, clazz)
            if (realMethodName == null) {
                val msg = "route cannot find match method $methodNameInPath in $router for $path"
                Logg.e(TAG, msg)
                notFound(path, msg)
                return null
            }
            methodCache.put(methodPath, realMethodName)

            val method = try {
                clazz.getDeclaredMethod(realMethodName, Map::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                notFound(path, e.message + "")
                return null
            }
            method.isAccessible = true

            return method.invoke(router, map)

        } catch (e: Exception) {
            e.printStackTrace()
            notFound(path, e.message + "")
        }

        return null
    }

    fun getModule(moduleName: String): Any? {
        return modToClassObject[moduleName]
    }


    private fun findMethodInClass(methodNameInPath: String, clazz: Class<*>): String? {

        val methods = clazz.declaredMethods ?: return null
        for (method in methods) {
            val anno = method.getAnnotation(RouteMethod::class.java) ?: continue
            if (anno.value == methodNameInPath) {
                return method.name
            }
        }

        return null
    }

    private fun notFound(path: String, reason: String) {
        mDegradeHandler?.onNotFound(path, reason)
    }

}