package com.cy.travelking.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.amap.api.navi.model.NaviLatLng;
import com.cy.travelking.App;
import com.cy.travelking.R;
import com.cy.travelking.constant.DriverStatus;
import com.cy.travelking.constant.SpConstant;
import com.cy.travelking.constant.UrlConstant;
import com.cy.travelking.entity.DriverInfo;
import com.cy.travelking.entity.Login;
import com.cy.travelking.entity.User;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class AppUtil {

    private static DriverInfo driverInfo;
    private static NaviLatLng nowLatlng;
    private static String driverStatus = DriverStatus.UNKNOWN;

    public static String getDriverStatus() {
        return driverStatus;
    }

    public static void setDriverStatus(String status) {
        driverStatus = status;
    }

    public static DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public static void setDriverInfo(DriverInfo driver) {
        driverInfo = driver;
    }

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(19[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    public static String getToken() {
        Gson gson = new Gson();
        String token = "";
        String loginStr = SpUtil.getStr(SpConstant.LOGIN_INFO);
        if (!TextUtils.isEmpty(loginStr)) {
            Login login = gson.fromJson(loginStr, Login.class);
            return login.getToken_session().getToken();
        }
        return token;
    }

    public static String getUserId() {
        Gson gson = new Gson();
        String token = "";
        String loginStr = SpUtil.getStr(SpConstant.LOGIN_INFO);
        if (!TextUtils.isEmpty(loginStr)) {
            Login login = gson.fromJson(loginStr, Login.class);
            return login.getUser().getId();
        }
        return token;
    }

    public static File getFileByUri(Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = App.getInstance().getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = App.getInstance().getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        } else {
//            Log.i(TAG, "Uri Scheme:" + uri.getScheme());
        }
        return null;
    }

    public static User getUser() {
        String loginStr = SpUtil.getStr(SpConstant.LOGIN_INFO);
        if (!TextUtils.isEmpty(loginStr)) {
            Gson gson = new Gson();
            Login login = gson.fromJson(loginStr, Login.class);
            return login.getUser();
        }
        return null;
    }

    public static NaviLatLng getNowLatlng() {
        return nowLatlng;
    }

    public static void setNowLatlng(NaviLatLng nowLatlng) {
        AppUtil.nowLatlng = nowLatlng;
    }

    public static final boolean isGpsOPen() {
        LocationManager locationManager
                = (LocationManager) App.getInstance().getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps;
    }

    public static long getBeginDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return firstDate.getTime();
    }

    public static long getEndDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        return lastDate.getTime();
    }

    public static int getYear() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    public static int getMonth() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MONTH) + 1;
    }

    public static long getToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getTomorow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    private static MediaPlayer mMediaPlayer;

    public static void alarm() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(App.getInstance(), R.raw.a);
            mMediaPlayer.setLooping(false);
        }
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    public static String replaceDomin(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String url_bak = UrlConstant.BASE_URL;
        if (url.indexOf("//") != -1) {
            String[] splitTemp = url.split("//");
            if (splitTemp.length > 1) {
                url = splitTemp[1];
            }
        }
        String[] urlTemp = url.split("/");
        for (int i = 1; i < urlTemp.length; i++) {
            url_bak = url_bak + "/" + urlTemp[i];
        }
        return url_bak;
    }

}
