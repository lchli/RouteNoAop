package com.lch.route.noaop;

import android.util.Log;

import com.lch.route.noaop.lib.RouteMethod;
import com.lch.route.noaop.lib.RouteService;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/7.
 */
@RouteService("home")
public class HomeService {

    @RouteMethod("he")
    public void hello(Map<String, String> params) {

        Log.e("test", "hello--------------------------");

    }
}
