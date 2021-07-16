package com.huruwo.pmshook;

import android.app.Application;
import android.content.Context;

import com.huruwo.pmshook.hook.ServiceManagerWraper;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        //在这里替换掉PMS代理类
        ServiceManagerWraper.hookPMS(newBase);
        super.attachBaseContext(newBase);
    }
}
