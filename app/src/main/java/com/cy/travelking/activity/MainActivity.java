package com.cy.travelking.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.navi.model.NaviLatLng;
import com.cy.travelking.R;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.constant.SpConstant;
import com.cy.travelking.entity.DriverInfo;
import com.cy.travelking.entity.PassOrder;
import com.cy.travelking.entity.Result;
import com.cy.travelking.fragment.HomeFragment;
import com.cy.travelking.fragment.MyFragment;
import com.cy.travelking.fragment.TaskFragment;
import com.cy.travelking.fragment.CharteredFragment;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.EventBusUtil;
import com.cy.travelking.util.SpUtil;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cy.travelking.constant.UrlConstant.SYNC;

public class MainActivity extends BaseActivity implements AMapLocationListener {

    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.tv_task)
    TextView tv_task;
    @BindView(R.id.tv_chartered)
    TextView tv_chartered;
    @BindView(R.id.tv_my)
    TextView tv_my;

    private HomeFragment homeFragment;
    private TaskFragment taskFragment;
    private CharteredFragment charteredFragment;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    private String[] permissions = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"
            , "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    private AMapLocationClient mLocationClient = null;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        requestCemera(permissions);
        setStatusBarColor(false, false, R.color.transparent);
        fragmentManager = getSupportFragmentManager();
        showFragment(0);
        change(tv_home, tv_task, tv_chartered, tv_my);

        initLocation();
    }

    public void initLocation() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(this);
//启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void getData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!AppUtil.isGpsOPen()) {
            new MaterialDialog.Builder(this)
                    .content("请打开GPS")
                    .cancelable(false)
                    .positiveText("确定")
                    .show();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(System.currentTimeMillis());
        if (!TextUtils.isEmpty(SpUtil.getStr(SpConstant.PASS_ORDER))) {
            PassOrder passOrder = gson.fromJson(SpUtil.getStr(SpConstant.PASS_ORDER), PassOrder.class);
            if (!day.equals(passOrder.getTime())) {
                passOrder.setTime(day);
                passOrder.setIds(new ArrayList<>());
                SpUtil.putStr(SpConstant.PASS_ORDER, gson.toJson(passOrder));
            }
        } else {
            PassOrder passOrder = new PassOrder();
            passOrder.setTime(day);
            passOrder.setIds(new ArrayList<>());
            SpUtil.putStr(SpConstant.PASS_ORDER, gson.toJson(passOrder));
        }
    }

    @OnClick({R.id.tv_home, R.id.tv_task, R.id.tv_chartered, R.id.tv_my})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_home:
                showFragment(0);
                change(tv_home, tv_task, tv_chartered, tv_my);
                EventBusUtil.post(MessageConstant.NOTIFY_HOME);
                break;
            case R.id.tv_task:
                showFragment(1);
                change(tv_task, tv_home, tv_chartered, tv_my);
                EventBusUtil.post(MessageConstant.NOTIFY_TASK);
                break;
            case R.id.tv_chartered:
                showFragment(2);
                change(tv_chartered, tv_home, tv_task, tv_my);
                EventBusUtil.post(MessageConstant.NOTIFY_TASK);
                break;
            case R.id.tv_my:
                showFragment(3);
                change(tv_my, tv_home, tv_task, tv_chartered);
                EventBusUtil.post(MessageConstant.NOTIFY_MY);
                break;
        }
    }

    private void showFragment(int page) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);
        switch (page) {
            case 0:
                // 如果fragment1已经存在则将其显示出来
                if (homeFragment != null)
                    ft.show(homeFragment);
                    // 否则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    homeFragment = new HomeFragment();
                    ft.add(R.id.main_container_content, homeFragment);
                }
                break;
            case 1:
                if (taskFragment != null)
                    ft.show(taskFragment);
                else {
                    taskFragment = new TaskFragment();
                    ft.add(R.id.main_container_content, taskFragment);
                }
                break;
            case 2:
                if (charteredFragment != null)
                    ft.show(charteredFragment);
                else {
                    charteredFragment = new CharteredFragment();
                    ft.add(R.id.main_container_content, charteredFragment);
                }
                break;
            case 3:
                if (myFragment != null) {
                    ft.show(myFragment);
                } else {
                    myFragment = new MyFragment();
                    ft.add(R.id.main_container_content, myFragment);
                }
                break;
        }
        ft.commit();
    }

    private void hideFragments(FragmentTransaction ft) {
        if (homeFragment != null)
            ft.hide(homeFragment);
        if (taskFragment != null)
            ft.hide(taskFragment);
        if (charteredFragment != null)
            ft.hide(charteredFragment);
        if (myFragment != null)
            ft.hide(myFragment);
    }

    private void change(TextView tv1, TextView tv2, TextView tv3, TextView tv4) {
        tv1.setTextColor(ContextCompat.getColor(this, R.color.titleGreen));
        tv2.setTextColor(ContextCompat.getColor(this, R.color.textGray));
        tv3.setTextColor(ContextCompat.getColor(this, R.color.textGray));
        tv4.setTextColor(ContextCompat.getColor(this, R.color.textGray));
        if (tv1.equals(tv_home)) {
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.home_click);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_home.setCompoundDrawables(null, drawable, null, null);
            Drawable drawable2 = ContextCompat.getDrawable(this, R.mipmap.task);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            tv_task.setCompoundDrawables(null, drawable2, null, null);
            Drawable drawable3 = ContextCompat.getDrawable(this, R.mipmap.chartered);
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            tv_chartered.setCompoundDrawables(null, drawable3, null, null);
            Drawable drawable4 = ContextCompat.getDrawable(this, R.mipmap.my);
            drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
            tv_my.setCompoundDrawables(null, drawable4, null, null);
            tv_home.setCompoundDrawablePadding(0);
            tv_task.setCompoundDrawablePadding(13);
            tv_chartered.setCompoundDrawablePadding(13);
            tv_my.setCompoundDrawablePadding(15);
        } else if (tv1.equals(tv_task)) {
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.home);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_home.setCompoundDrawables(null, drawable, null, null);
            Drawable drawable2 = ContextCompat.getDrawable(this, R.mipmap.task_click);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            tv_task.setCompoundDrawables(null, drawable2, null, null);
            Drawable drawable3 = ContextCompat.getDrawable(this, R.mipmap.chartered);
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            tv_chartered.setCompoundDrawables(null, drawable3, null, null);
            Drawable drawable4 = ContextCompat.getDrawable(this, R.mipmap.my);
            drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
            tv_my.setCompoundDrawables(null, drawable4, null, null);
            tv_task.setCompoundDrawablePadding(0);
            tv_home.setCompoundDrawablePadding(15);
            tv_chartered.setCompoundDrawablePadding(13);
            tv_my.setCompoundDrawablePadding(15);
        } else if (tv1.equals(tv_chartered)) {
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.home);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_home.setCompoundDrawables(null, drawable, null, null);
            Drawable drawable2 = ContextCompat.getDrawable(this, R.mipmap.task);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            tv_task.setCompoundDrawables(null, drawable2, null, null);
            Drawable drawable3 = ContextCompat.getDrawable(this, R.mipmap.chartered_click);
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            tv_chartered.setCompoundDrawables(null, drawable3, null, null);
            Drawable drawable4 = ContextCompat.getDrawable(this, R.mipmap.my);
            drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
            tv_my.setCompoundDrawables(null, drawable4, null, null);
            tv_chartered.setCompoundDrawablePadding(13);
            tv_home.setCompoundDrawablePadding(15);
            tv_task.setCompoundDrawablePadding(13);
            tv_my.setCompoundDrawablePadding(15);
        } else {
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.home);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_home.setCompoundDrawables(null, drawable, null, null);
            Drawable drawable2 = ContextCompat.getDrawable(this, R.mipmap.task);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            tv_task.setCompoundDrawables(null, drawable2, null, null);
            Drawable drawable3 = ContextCompat.getDrawable(this, R.mipmap.chartered);
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            tv_chartered.setCompoundDrawables(null, drawable3, null, null);
            Drawable drawable4 = ContextCompat.getDrawable(this, R.mipmap.my_click);
            drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
            tv_my.setCompoundDrawables(null, drawable4, null, null);
            tv_my.setCompoundDrawablePadding(0);
            tv_home.setCompoundDrawablePadding(15);
            tv_task.setCompoundDrawablePadding(13);
            tv_chartered.setCompoundDrawablePadding(13);
        }
    }

    private void requestCemera(String[] permissions) {
        PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                toast("用户拒绝位置权限！");
            }
        }, permissions);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                AppUtil.setNowLatlng(new NaviLatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
                EventBusUtil.post(MessageConstant.NOTIFY_LOCATION);
                Map<String, Object> map = new HashMap<>();
                map.put("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id());
                map.put("latitude", amapLocation.getLatitude());
                map.put("longitude", amapLocation.getLongitude());
                map.put("status", AppUtil.getDriverStatus());
                OkGo.<Result<DriverInfo>>post(SYNC)
                        .tag(this)
                        .headers("token", AppUtil.getToken())
                        .upJson(gson.toJson(map))
                        .execute(new JsonCallback<Result<DriverInfo>>() {
                                     @Override
                                     public void onSuccess(Response<Result<DriverInfo>> response) {
                                     }
                                 }
                        );
            }
        }
    }
}
