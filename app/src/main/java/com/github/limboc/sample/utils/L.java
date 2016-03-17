package com.github.limboc.sample.utils;

import android.util.Log;

import com.github.limboc.sample.BuildConfig;

/**
 * Created by Chen on 2016/3/16.
 */
public class L {

    public static void i(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.e(tag, msg);
        }
    }

}
