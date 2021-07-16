package com.huruwo.pmshook.proxy.statib;

import android.util.Log;

import com.huruwo.pmshook.proxy.IUser;
import com.huruwo.pmshook.utils.LogXUtils;

public class UserStaticProxy implements IUser {

    IUser iUser;

    public UserStaticProxy(IUser iUser) {
        this.iUser = iUser;
    }

    @Override
    public void speak(String word) {
        LogXUtils.e("before User speak");
        iUser.speak(word);
        LogXUtils.e("after User speak");

    }
}
