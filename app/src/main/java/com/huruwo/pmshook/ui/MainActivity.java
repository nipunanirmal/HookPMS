package com.huruwo.pmshook.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.huruwo.pmshook.R;
import com.huruwo.pmshook.proxy.dynamic.ProxyFactory;
import com.huruwo.pmshook.proxy.IUser;
import com.huruwo.pmshook.proxy.statib.UserStaticProxy;
import com.huruwo.pmshook.proxy.User;
import com.huruwo.pmshook.utils.LogXUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSignature(this);
        testDynamicProxy();
    }

    private void testStaticProxy(){
        //不使用代理类
        String word = "我是你大爷";
        User user = new User();
        user.speak(word);

        //使用代理类
        IUser proxyInstance = new UserStaticProxy(user);
        LogXUtils.e(proxyInstance.getClass().getName());
        proxyInstance.speak(word);

    }

    public static void testDynamicProxy() {
        String word = "我是你大爷";

        User user = new User();
        user.speak(word);

        IUser proxyInstance = (IUser) new ProxyFactory(user).getProxyInstance();
        LogXUtils.e(proxyInstance.getClass().getName());
        proxyInstance.speak(word);
    }

}