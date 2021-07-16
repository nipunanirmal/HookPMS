package com.huruwo.pmshook.utils;

import android.text.TextUtils;
import android.util.Log;



import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class LogXUtils {

    public static String TAG = "LogXUtils";
    public static String DerviceId = "";
    private static DateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");

    //v d i w e a

    public static void init(String tag, String derviceId) {
        TAG = tag;
        DerviceId = derviceId;
    }

    public static void v(String log) {
        if (!TextUtils.isEmpty(log)) {
            Log.v(TAG, log);
        }
    }

    public static void d(String log) {
        if (!TextUtils.isEmpty(log)) {
            Log.d(TAG, log);
        }
    }

    public static void i(String log) {

        if (!TextUtils.isEmpty(log)) {
            Log.i(TAG, log);
        }

    }

    public static void w(String log) {
        if (!TextUtils.isEmpty(log)) {
            Log.w(TAG, log);
        }
    }

    public static void e(String log) {
        if (!TextUtils.isEmpty(log)) {
            Log.e(TAG, log);
        }
    }

}
