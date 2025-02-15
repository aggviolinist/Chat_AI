package com.apps.aivision.components

import android.util.Log
import com.apps.aivision.BuildConfig

class AppLogger {
    companion object{

        fun logE(tag:String,msg:String)
        {
            if (BuildConfig.DEBUG) {
                if (msg.length > 4000) {
                    Log.e(tag, msg.substring(0, 4000))
                    logE(tag, msg.substring(4000))
                } else {
                    Log.e(tag, msg)
                }
            }
        }

        fun logD(tag:String,msg:String)
        {
            if (BuildConfig.DEBUG) {
                if (msg.length > 4000) {
                    Log.d(tag, msg.substring(0, 4000))
                    logD(tag, msg.substring(4000))
                } else {
                    Log.d(tag, msg)
                }
            }
        }

        fun logW(tag:String,msg:String)
        {
            if (BuildConfig.DEBUG) {
                if (msg.length > 4000) {
                    Log.w(tag, msg.substring(0, 4000))
                    logW(tag, msg.substring(4000))
                } else {
                    Log.w(tag, msg)
                }
            }
        }

        fun logI(tag:String,msg:String)
        {
            if (BuildConfig.DEBUG) {
                if (msg.length > 4000) {
                    Log.i(tag, msg.substring(0, 4000))
                    logI(tag, msg.substring(4000))
                } else {
                    Log.i(tag, msg)
                }
            }
        }

        fun logV(tag:String,msg:String)
        {
            if (BuildConfig.DEBUG) {
                if (msg.length > 4000) {
                    Log.v(tag, msg.substring(0, 4000))
                    logV(tag, msg.substring(4000))
                } else {
                    Log.v(tag, msg)
                }
            }
        }
    }
}