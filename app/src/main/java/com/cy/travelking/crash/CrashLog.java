package com.cy.travelking.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.cy.travelking.App;
import com.cy.travelking.entity.ExceptionInfo;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 */
public class CrashLog {
    public static final String TAG = "CrashLog";

    public static void saveCrashLog(Map<String,Object> mem, Throwable ex) {
        ExceptionInfo info =new ExceptionInfo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        info.setOpeDate(sdf.format(System.currentTimeMillis()));
        try {
            info.setSystemVersion(Build.VERSION.RELEASE);
            PackageManager pm = App.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(App.getInstance().getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                info.setVersionName(versionName);
                info.setVersionCode(versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, String> infos = new TreeMap<>();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
        Gson gson = new Gson();
        info.setDeviceInfo(gson.toJson(infos));
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        info.setDetail(result);
        if(null != mem && !"".equals(mem)){
            info.setMem(gson.toJson(mem));
        }
        //保存崩溃日志到本地crash文件夹
        Map<String, String> map = collectDeviceInfo(App.getInstance());
        saveCrashInfo2File(App.getInstance(), ex, map);
    }


    private static Map<String, String> collectDeviceInfo(Context ctx) {
        Map<String, String> infos = new TreeMap<>();
        try {

            infos.put("systemVersion", Build.VERSION.RELEASE);
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
        return infos;
    }

    private static void saveCrashInfo2File(Context context, Throwable ex, Map<String, String> infos) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        try {
            long timestamp = System.currentTimeMillis();
            String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".txt";
            String cachePath = crashLogDir(context);

            File dir = new File(cachePath);
            dir.mkdirs();
            FileOutputStream fos = new FileOutputStream(cachePath + fileName);
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e) {
        }
    }

    public static String crashLogDir(Context context) {
        return Environment.getExternalStorageDirectory() + File.separator + "crash" + File.separator;
//        return context.getCacheDir().getPath() + File.separator + "crash" + File.separator;
    }
}
