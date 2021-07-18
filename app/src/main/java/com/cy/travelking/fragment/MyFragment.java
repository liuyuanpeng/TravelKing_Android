package com.cy.travelking.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.cy.travelking.R;
import com.cy.travelking.activity.HistoryOrderAcitvity;
import com.cy.travelking.activity.LoginActivity;
import com.cy.travelking.activity.SettledListAcitvity;
import com.cy.travelking.activity.WebActivity;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.constant.SpConstant;
import com.cy.travelking.constant.UrlConstant;
import com.cy.travelking.entity.DriverInfo;
import com.cy.travelking.entity.Login;
import com.cy.travelking.entity.MessageEvent;
import com.cy.travelking.entity.Result;
import com.cy.travelking.entity.SettledTotal;
import com.cy.travelking.entity.Upload;
import com.cy.travelking.entity.User;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.SpUtil;
import com.cy.travelking.widget.TitleBar;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;
import static com.cy.travelking.constant.UrlConstant.SETTLED_LIST;
import static com.cy.travelking.constant.UrlConstant.UPLOAD;
import static com.cy.travelking.constant.UrlConstant.USER_UPDATE;

/**
 * Author: CY
 * Date: 2019/4/22
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.titleBar)
    public TitleBar titleBar;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_star1)
    ImageView mIvStar1;
    @BindView(R.id.iv_star2)
    ImageView mIvStar2;
    @BindView(R.id.iv_star3)
    ImageView mIvStar3;
    @BindView(R.id.iv_star4)
    ImageView mIvStar4;
    @BindView(R.id.iv_star5)
    ImageView mIvStar5;
    @BindView(R.id.tv_money)
    TextView mTvMoney;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        titleBar.setTitle("个人中心");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setLeftVisible(false);
/*        titleBar.setBackgroundResource(R.color.titleGreen);
        titleBar.setLeftImageResource(R.mipmap.arrow_back);*/
        notifyInfo();
    }

    public void notifyInfo() {
        try {
            DriverInfo driverInfo = AppUtil.getDriverInfo();
            if (driverInfo != null) {
                if (driverInfo.getUser() != null) {
                    mTvName.setText(driverInfo.getUser().getName());
                    int evaluate = driverInfo.getDriver().getEvaluate();
                    mIvStar1.setImageResource(evaluate == 0 ? R.mipmap.star_off : evaluate == 1 ? R.mipmap.star_half : R.mipmap.star_on);
                    mIvStar2.setImageResource(evaluate < 3 ? R.mipmap.star_off : evaluate == 3 ? R.mipmap.star_half : R.mipmap.star_on);
                    mIvStar3.setImageResource(evaluate < 5 ? R.mipmap.star_off : evaluate == 5 ? R.mipmap.star_half : R.mipmap.star_on);
                    mIvStar4.setImageResource(evaluate < 7 ? R.mipmap.star_off : evaluate == 7 ? R.mipmap.star_half : R.mipmap.star_on);
                    mIvStar5.setImageResource(evaluate < 9 ? R.mipmap.star_off : evaluate == 9 ? R.mipmap.star_half : R.mipmap.star_on);
                    if (TextUtils.isEmpty(driverInfo.getUser().getAvatar())) {
                        Glide.with(mContext).load(R.mipmap.head_photo).into(mIvAvatar);
                    } else {
                        Glide.with(mContext).load(driverInfo.getUser().getAvatar()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mIvAvatar);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getSettled();
        }
    }

    public void getSettled() {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_user_id", AppUtil.getDriverInfo().getDriver().getUser_id());
        map.put("start", AppUtil.getToday());
        map.put("end", AppUtil.getTomorow());
        OkGo.<Result<SettledTotal>>post(SETTLED_LIST)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<Result<SettledTotal>>() {
                             @Override
                             public void onSuccess(Response<Result<SettledTotal>> response) {
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     SettledTotal settledTotal = (SettledTotal) result.getData();
                                     mTvMoney.setText("今日收入" + settledTotal.getTotal() + "元");
                                 } else {
                                     toast(result.getMessage());
                                 }
                             }

                             @Override
                             public void onError(Response<Result<SettledTotal>> response) {
                                 super.onError(response);
                             }
                         }
                );
    }

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_INFO:
                notifyInfo();
                break;
            case MessageConstant.NOTIFY_MY:
                getSettled();
                break;
        }
    }

    @OnClick({R.id.tv_logout, R.id.rl_info, R.id.rl_money, R.id.rl_history_order, R.id.rl_about})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                SpUtil.putStr(SpConstant.LOGIN_INFO, "");
                startActivity(new Intent(mContext, LoginActivity.class));
                ((Activity) mContext).finish();
                break;
            case R.id.rl_info:
                changeAvatar();
                break;
            case R.id.rl_money:
                startActivity(SettledListAcitvity.class);
                break;
            case R.id.rl_history_order:
                startActivity(HistoryOrderAcitvity.class);
                break;
            case R.id.rl_about:
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("title","关于我们");
                intent.putExtra("url", UrlConstant.ABOUT_DRIVER);
                startActivity(intent);
                break;
        }
    }

    public void changeAvatar() {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "请求访问存储卡", "禁止", "打开权限");
        PermissionsUtil.requestPermission(mContext, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                Matisse.from(MyFragment.this)
                        .choose(MimeType.ofImage())
                        .capture(true)
                        .captureStrategy(
                                new CaptureStrategy(true, "com.cy.travelking.fileprovider", "pic"))
                        .countable(true)
                        .maxSelectable(1)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new PicassoEngine())
                        .theme(R.style.Matisse_Zhihu)
                        .forResult(1001);
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                toast("用户拒绝了访问存储卡");
            }
        }, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, true, tip);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            if (mSelected.size() > 0) {
                try {
                    File file = AppUtil.getFileByUri(mSelected.get(0));
                    Luban.with(mContext)
                            .load(file)
                            .ignoreBy(5)
                            .filter(new CompressionPredicate() {
                                @Override
                                public boolean apply(String path) {
                                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                                }
                            })
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    showLoading();
                                }

                                @Override
                                public void onSuccess(File file) {
                                    updateAvatar(file);
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                    updateAvatar(file);
                                }
                            }).launch();


                } catch (Exception e) {
                    dismissLoading();
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateAvatar(File file) {
        OkGo.<Result<Upload>>post(UPLOAD)
                .tag(this)
                .headers("token", AppUtil.getToken())
                .params("file_type", "IMAGE")
                .params("file_meta_data", "{}")
                .params("file", file)
                .execute(new JsonCallback<Result<Upload>>() {
                             @Override
                             public void onSuccess(Response<Result<Upload>> response) {
                                 Result result = response.body();
                                 if (result.isSuccess()) {
                                     Upload upload = (Upload) result.getData();
                                     Map<String, Object> map = new HashMap<>();
                                     map.put("avatar", upload.getPath());
                                     User user = AppUtil.getDriverInfo().getUser();
                                     if (user != null) {
                                         map.put("mobile", user.getMobile());
                                         map.put("name", user.getName());
                                         map.put("user_id", user.getId());
                                     }
                                     OkGo.<Result<Login>>post(USER_UPDATE)
                                             .tag(this)
                                             .headers("token", AppUtil.getToken())
                                             .upJson(gson.toJson(map))
                                             .execute(new JsonCallback<Result<Login>>() {
                                                          @Override
                                                          public void onSuccess(Response<Result<Login>> response) {
                                                              dismissLoading();
                                                              Result result = response.body();
                                                              if (result.isSuccess()) {
                                                                  Login login = (Login) result.getData();
                                                                  AppUtil.getDriverInfo().getUser().setAvatar(login.getUser().getAvatar());
                                                                  notifyInfo();
                                                              } else {
                                                                  toast(result.getMessage());
                                                              }
                                                          }

                                                          @Override
                                                          public void onError(Response<Result<Login>> response) {
                                                              super.onError(response);
                                                              dismissLoading();
                                                              toastNetErr();
                                                          }
                                                      }
                                             );
                                 } else {
                                     dismissLoading();
                                     toast(result.getMessage());
                                 }

                             }

                             @Override
                             public void onError(Response<Result<Upload>> response) {
                                 super.onError(response);
                                 dismissLoading();
                                 toastNetErr();
                             }
                         }
                );
    }

}
