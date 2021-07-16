package com.huruwo.pmshook.proxy;

import android.util.Log;

import com.huruwo.pmshook.utils.LogXUtils;

public class User implements IUser {
    @Override
    public void speak(String word) {
        LogXUtils.e("User speak "+word);
    }
}
