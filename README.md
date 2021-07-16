## 代码内部 动态代理 hook PMS 实现签名修改

## 核心知识点梳理

代理 静态/动态代理
PMS实现原理
使用动态代理hookPMS

## 代理 静态代理 动态代理


## PMS

Context -> ContextWrapper

```
@Override
    public PackageManager getPackageManager() {
        return mBase.getPackageManager();
    }
```

```
/**
     * Set the base context for this ContextWrapper.  All calls will then be
     * delegated to the base context.  Throws
     * IllegalStateException if a base context has already been set.
     *
     * @param base The new base context for this wrapper.
     */
    protected void attachBaseContext(Context base) {
        if (mBase != null) {
            throw new IllegalStateException("Base context already set");
        }
        mBase = base;
    }
```

```
@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        if (newBase != null) {
            newBase.setAutofillClient(this);
            newBase.setContentCaptureOptions(getContentCaptureOptions());
        }
    }
```
```
@UnsupportedAppUsage
    final void attach(Context context, ActivityThread aThread,
            Instrumentation instr, IBinder token, int ident,
            Application application, Intent intent, ActivityInfo info,
            CharSequence title, Activity parent, String id,
            NonConfigurationInstances lastNonConfigurationInstances,
            Configuration config, String referrer, IVoiceInteractor voiceInteractor,
            Window window, ActivityConfigCallback activityConfigCallback, IBinder assistToken) {
        attachBaseContext(context);
        ......
        ......
        ......
```

这里卡住了 没法找到调用

```
private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
......
......
activity.attach(appContext, this, getInstrumentation(), r.token,
                        r.ident, app, r.intent, r.activityInfo, title, r.parent,
                        r.embeddedID, r.lastNonConfigurationInstances, config,
                        r.referrer, r.voiceInteractor, window, r.configCallback,
                        r.assistToken);
......
......
}
```
```
        ContextImpl appContext = createBaseContextForActivity(r);
```
```
ContextImpl appContext = ContextImpl.createActivityContext(
                this, r.packageInfo, r.activityInfo, r.token, displayId, r.overrideConfig);
```
```
@Override
    public PackageManager getPackageManager() {
        if (mPackageManager != null) {
            return mPackageManager;
        }

        final IPackageManager pm = ActivityThread.getPackageManager();
        final IPermissionManager permissionManager = ActivityThread.getPermissionManager();
        if (pm != null && permissionManager != null) {
            // Doesn't matter if we make more than one instance.
            return (mPackageManager = new ApplicationPackageManager(this, pm, permissionManager));
        }

        return null;
    }
```
```
@UnsupportedAppUsage
    public static IPackageManager getPackageManager() {
        if (sPackageManager != null) {
            return sPackageManager;
        }
        final IBinder b = ServiceManager.getService("package");
        sPackageManager = IPackageManager.Stub.asInterface(b);
        return sPackageManager;
    }
```

IPackageManager	一个AIDL的接口，用于和PMS进行进程间通信

```
public class ApplicationPackageManager extends PackageManager{

@Override
    public PackageInfo getPackageInfo(VersionedPackage versionedPackage, int flags)
            throws NameNotFoundException {
        final int userId = getUserId();
        try {
            PackageInfo pi = mPM.getPackageInfoVersioned(versionedPackage,
                    updateFlagsForPackage(flags, userId), userId);
            if (pi != null) {
                return pi;
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
        throw new NameNotFoundException(versionedPackage.toString());
    }

}
```

mPM去获得包的信息
所以我们需要将mPM替换掉就可以达到Hook的效果

最后知道我们要使用动态代理的方式替换掉这里的两个属性

ActivityThread的静态变量sPackageManager
ApplicationPackageManager对象里面的mPM变量



## 动态代理 hook

实现动态代理步骤

代理--对接口类的实现类进行二次封装

### 静态代理

写死在编译之前 对所有继承了某个接口的类的实现类做统一封装
实现方法如下
1.代理类继承被代理接口
2.代理类实现接口方法
3.代理类需要传入一个接口类的实现类
4.代理类的接口实现类直接调用传入的接口实现类的实现方法
5.并在实现类的接口方法调用前后做封装


## 动态代理

1.继承接口 InvocationHandler


注明:类名是这种 $Proxy1 形式的 就是动态代理类

## 反射+动态代理=HOOK PMS


### 刨根问底   原始的IPackageManager到底实现在哪里       IPackageManager.aidl


## 总结
1.追踪getPackageManager的调用发现具体的由IPackageManager来实现的
2.IPackageManager有两个获取路径

ActivityThread的
sPackageManager = IPackageManager.Stub.asInterface(b);

ApplicationPackageManager的
private final IPackageManager mPM;

3.动态代理实现IPackageManager接口


ActivityThread的静态变量sPackageManager
ApplicationPackageManager对象里面的mPM变量

## 参考文章

https://www.jianshu.com/p/c559852c4878
https://www.jianshu.com/p/cee163c863c4