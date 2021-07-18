package com.cy.travelking;

import android.app.Activity;
import android.app.Application;
import android.os.Looper;
import android.widget.Toast;

import com.cy.travelking.crash.Cockroach;
import com.cy.travelking.crash.CrashLog;
import com.cy.travelking.crash.ExceptionHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

public class App extends Application {

    private static App mInstance;
    private List<Activity> mAllActivities;

    public static synchronized App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    public void addActivity(Activity act) {
        if (mAllActivities == null) {
            mAllActivities = new ArrayList<>();
        }
        mAllActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (mAllActivities != null) {
            mAllActivities.remove(act);
        }
    }

    public void exitApp() {
        if (mAllActivities != null) {
            synchronized (mAllActivities) {
                for (Activity act : mAllActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 初始化第三方控件
     */
    private void init(){
        OkGo.getInstance().init(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        installCrash();
    }

    private void installCrash() {
        final Map<String, Object> map = new HashMap<>();
        map.put("methodName", "BaseApplication.installCrash");
        final Thread.UncaughtExceptionHandler sysExcepHandler = Thread.getDefaultUncaughtExceptionHandler();
        final Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        Cockroach.install(this, new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
     /*           new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        toast.setText("系统异常，请退出重试");
                        toast.show();
                    }
                });*/
                map.put("text", "系统异常，请退出重试");
                CrashLog.saveCrashLog(map, throwable);
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                throwable.printStackTrace();//打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
             /*   toast.setText("系统异常，请退出重试");
                toast.show();*/
                map.put("text", "系统异常，请退出重试");
                CrashLog.saveCrashLog(map, throwable);
            }

            @Override
            protected void onEnterSafeMode() {
            }

            @Override
            protected void onMayBeBlackScreen(Throwable throwable) {
                throwable.printStackTrace();
                map.put("text", "黑屏关闭");
                CrashLog.saveCrashLog(map, throwable);
                Thread thread = Looper.getMainLooper().getThread();
                //黑屏时建议直接杀死app
                sysExcepHandler.uncaughtException(thread, new RuntimeException("black screen"));
            }
        });

    }
}

