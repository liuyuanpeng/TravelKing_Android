package com.cy.travelking.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cy.travelking.App;
import com.cy.travelking.constant.SpConstant;

public class SpUtil {
    public SpUtil() {
    }

    public static SharedPreferences getSharedPreferences(Context context, String spName) {
        SharedPreferences sp = context.getSharedPreferences(spName, 0);
        return sp;
    }

    public static void clear() {
        Editor editor = getSharedPreferences(App.getInstance(), SpConstant.FILE_NAME).edit();
        editor.clear();
        editor.commit();
    }


    public static void putInt(String nodeName, int value) {
        Editor editor = getSharedPreferences(App.getInstance(), SpConstant.FILE_NAME).edit();
        editor.putInt(nodeName, value).commit();
    }

    public static void putLong(String nodeName, long value) {
        Editor editor = getSharedPreferences(App.getInstance(), SpConstant.FILE_NAME).edit();
        editor.putLong(nodeName, value).commit();
    }


    public static void putBoolean(String nodeName, boolean value) {
        Editor editor = getSharedPreferences(App.getInstance(), SpConstant.FILE_NAME).edit();
        editor.putBoolean(nodeName, value).commit();
    }

    public static void putStr(String nodeName, String value) {
        Editor editor = getSharedPreferences(App.getInstance(), SpConstant.FILE_NAME).edit();
        editor.putString(nodeName, value).commit();
    }

    public static String getStr(String nodeName) {
        return getStr(nodeName, "");
    }

    public static String getStr(String nodeName, String defaultValue) {
        return getSharedPreferences(App.getInstance(), SpConstant.FILE_NAME).getString(nodeName, defaultValue);
    }

    public static boolean getBoolean(String nodeName) {
        return getBoolean(nodeName, false);
    }

    public static boolean getBoolean(String nodeName, boolean defult) {
        return getSharedPreferences(App.getInstance(), SpConstant.FILE_NAME).getBoolean(nodeName, defult);
    }

    public static int getInt(String nodeName) {
        return getInt(nodeName, 0);
    }

    public static int getInt(String nodeName, int defaultValue) {
        return getSharedPreferences(App.getInstance(), SpConstant.FILE_NAME).getInt(nodeName, defaultValue);
    }

    public static long getLong(String nodeName) {
        return getLong(nodeName, 0);
    }

    public static long getLong(String nodeName, long defaultValue) {
        return getSharedPreferences(App.getInstance(), SpConstant.FILE_NAME).getLong(nodeName, defaultValue);
    }

    public static void putSharePreFloat(Context context, String spName, String nodeName, float value) {
        Editor editor = getSharedPreferences(context, spName).edit();
        editor.putFloat(nodeName, value).commit();
    }

    public static Float getSharePreFloat(Context context, String spName, String nodeName, float defaultValue) {
        return getSharedPreferences(context, spName).getFloat(nodeName, defaultValue);
    }

}
