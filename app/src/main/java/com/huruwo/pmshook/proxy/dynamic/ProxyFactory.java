package com.huruwo.pmshook.proxy.dynamic;

import android.util.Log;

import com.huruwo.pmshook.proxy.IUser;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理生产类
 */
public class ProxyFactory {
    // 维护一个目标对象 也就是被代理的接口实例
    private Object target;

    /**
     * 把接口实例传输进来
     * @param target
     */
    public ProxyFactory(Object target) {
        this.target = target;
    }

    /**
     * 统一规划 代理方法的实现
     * @return
     */
    public Object getProxyInstance() {

        if(target instanceof IUser) {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(), new UserProxyInvocationHandler(target));
        }
        else {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(), (proxy, method, args) -> {
                        method.invoke(target, args);
                        return null;
                    });
        }
    }
}
