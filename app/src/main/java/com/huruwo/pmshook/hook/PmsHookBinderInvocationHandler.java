package com.huruwo.pmshook.hook;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import com.huruwo.pmshook.utils.LogXUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PmsHookBinderInvocationHandler implements InvocationHandler {
    public static String TAG ="LogX";
    //应用正确的签名信息
    private String SIGN;
    private String appPkgName = "";
    private Object base;

    public PmsHookBinderInvocationHandler(Object base, String sign, String appPkgName, int hashCode) {
        try {
            this.base = base;
            this.SIGN = sign;
            this.appPkgName = appPkgName;
        } catch (Exception e) {
            LogXUtils.e("error:"+Log.getStackTraceString(e));
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i(TAG, method.getName());
        //查看是否是getPackageInfo方法
        if("getPackageInfo".equals(method.getName())){
            String pkgName = (String)args[0];
            Integer flag = (Integer)args[1];
            //是否是获取我们需要hook apk的签名
            if(flag == PackageManager.GET_SIGNATURES && appPkgName.equals(pkgName)){
                //将构造方法中传进来的新的签名覆盖掉原来的签名
                Signature sign = new Signature(SIGN);
                PackageInfo info = (PackageInfo) method.invoke(base, args);
                info.signatures[0] = sign;
                return info;
            }
        }
        return method.invoke(base, args);
    }
}
