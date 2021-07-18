package com.cy.travelking.activity;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cy.travelking.R;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.constant.SpConstant;
import com.cy.travelking.entity.DriverInfo;
import com.cy.travelking.entity.Result;
import com.cy.travelking.fragment.MyFragment;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.EventBusUtil;
import com.cy.travelking.util.SpUtil;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import static com.cy.travelking.constant.UrlConstant.GET_DRIVER;

public class SplahActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.act_splash;
    }

    @Override
    protected void initData() {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "为了更好的体验，请求打开权限", "禁止", "打开权限");
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                if (TextUtils.isEmpty(SpUtil.getStr(SpConstant.LOGIN_INFO))) {
                    startActivityFinish(LoginActivity.class);
                } else {
                    OkGo.<Result<DriverInfo>>get(GET_DRIVER)
                            .tag(this)
                            .headers("token", AppUtil.getToken())
                            .params("user_id", AppUtil.getUserId())
                            .execute(new JsonCallback<Result<DriverInfo>>() {
                                         @Override
                                         public void onSuccess(Response<Result<DriverInfo>> response) {
                                             Result result = response.body();
                                             if (result.isSuccess()) {
                                                 DriverInfo driverInfo = (DriverInfo) result.getData();
                                                 AppUtil.setDriverInfo(driverInfo);
                                                 EventBusUtil.post(MessageConstant.NOTIFY_INFO);
                                                 startActivityFinish(MainActivity.class);
                                             }else {
                                                 SpUtil.putStr(SpConstant.LOGIN_INFO,"");
                                                 startActivityFinish(LoginActivity.class);
                                             }
                                         }

                                         @Override
                                         public void onError(Response<Result<DriverInfo>> response) {
                                             super.onError(response);
                                             toast("获取司机信息失败，请重新登录");
                                             SpUtil.putStr(SpConstant.LOGIN_INFO,"");
                                             startActivityFinish(LoginActivity.class);
                                         }
                                     }
                            );
                }
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                finish();
                toast("用户拒绝了权限");
            }
        }, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, true, tip);


    }
}
