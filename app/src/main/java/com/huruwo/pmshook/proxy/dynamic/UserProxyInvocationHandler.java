package com.huruwo.pmshook.proxy.dynamic;

import android.util.Log;

import com.huruwo.pmshook.utils.LogXUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UserProxyInvocationHandler implements InvocationHandler {

   private Object target;


    public UserProxyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("speak")){
            LogXUtils.e("before User speak");
            method.invoke(target,args);
            LogXUtils.e("after User speak");
        }
        return null;
    }
}
