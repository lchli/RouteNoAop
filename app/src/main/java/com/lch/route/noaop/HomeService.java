package com.lch.route.noaop;

import android.content.Context;
import android.util.Log;

import com.lch.route.noaop.lib.RouteMethod;
import com.lch.route.noaop.lib.RouteService;
import com.lch.route.noaop.lib.Router;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/7.
 */
@RouteService("home")
public class HomeService implements Router {

    @Override
    public void init(@NotNull Context context) {

    }

    @RouteMethod("he")
    public void hello(Map<String, String> params) {

        Log.e("test", "hello--------------------------");

    }
}
