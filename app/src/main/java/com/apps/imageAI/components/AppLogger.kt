package com.apps.imageAI.components

import android.util.Log
import com.apps.imageAI.BuildConfig


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

    }
}