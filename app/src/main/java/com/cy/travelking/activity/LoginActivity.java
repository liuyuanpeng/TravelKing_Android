package com.cy.travelking.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cy.travelking.R;
import com.cy.travelking.constant.MessageConstant;
import com.cy.travelking.constant.SpConstant;
import com.cy.travelking.constant.UrlConstant;
import com.cy.travelking.entity.Captcha;
import com.cy.travelking.entity.DriverInfo;
import com.cy.travelking.entity.Login;
import com.cy.travelking.entity.Result;
import com.cy.travelking.okgo.JsonCallback;
import com.cy.travelking.util.AppUtil;
import com.cy.travelking.util.EventBusUtil;
import com.cy.travelking.util.SpUtil;
import com.cy.travelking.widget.CountDownTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cy.travelking.constant.UrlConstant.CAPTCHA;
import static com.cy.travelking.constant.UrlConstant.CAPTCHA_LOGIN;
import static com.cy.travelking.constant.UrlConstant.CHECK;
import static com.cy.travelking.constant.UrlConstant.GET_DRIVER;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_get_code)
    CountDownTextView mTvGetCode;
    @BindView(R.id.iv_check)
    ImageView mIvCheck;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    private Captcha captcha;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_login;
    }

    @Override
    protected void initData() {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (AppUtil.isMobileNO(mEtPhone.getText().toString())) {
                    mTvGetCode.setTextColor(getResources().getColor(R.color.titleGreen));
                    mTvGetCode.setBackground(getResources().getDrawable(R.drawable.border_corner_green));
                } else {
                    mTvGetCode.setTextColor(getResources().getColor(R.color.code_gray));
                    mTvGetCode.setBackground(getResources().getDrawable(R.drawable.border_corner_code_gray));
                }
                changeLoginBtnStatus();
            }
        });

        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changeLoginBtnStatus();
            }
        });

        mTvGetCode.setNormalText("???????????????")
                .setCountDownText("", "???????????????")
                .setCloseKeepCountDown(false)//?????????????????????????????????
                .setCountDownClickable(false)//?????????????????????????????????????????????
                .setShowFormatTime(true)//?????????????????????
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        toast("?????????????????????????????????????????????");
                    }
                });
    }

    public void changeLoginBtnStatus() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString()) || !AppUtil.isMobileNO(mEtPhone.getText().toString()) || TextUtils.isEmpty(mEtCode.getText().toString())) {
            mTvLogin.setBackground(getResources().getDrawable(R.drawable.corner_gray));
        } else {
            mTvLogin.setBackground(getResources().getDrawable(R.drawable.corner_green));
        }
    }

    @OnClick({R.id.tv_get_code, R.id.ll_provision, R.id.tv_provision, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getCode();
//                check();
                break;
            case R.id.ll_provision:
                if (mIvCheck.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.icon_uncheck).getConstantState())) {
                    mIvCheck.setImageResource(R.drawable.icon_checked);
                } else {
                    mIvCheck.setImageResource(R.drawable.icon_uncheck);
                }
                break;
            case R.id.tv_provision:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("title", "??????????????????");
                intent.putExtra("url", UrlConstant.CLAUSE_DRIVER);
                startActivity(intent);
                break;
            case R.id.tv_login:
                login();
                break;
        }
    }

    public void check() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            toast("????????????????????????");
            return;
        }
        if (!AppUtil.isMobileNO(mEtPhone.getText().toString())) {
            toast("?????????????????????");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("username", mEtPhone.getText().toString());
        showLoading();
        OkGo.<Result<Captcha>>get(CHECK)
                .tag(this)
                .params("username", mEtPhone.getText().toString())
                .execute(new JsonCallback<Result<Captcha>>() {
                             @Override
                             public void onSuccess(Response<Result<Captcha>> response) {
                            /*     Result result = response.body();
                                 captcha = (Captcha)result.getData();
                                 if (result.isSuccess()) {
                                     toast("?????????????????????");
                                 } else {
                                     toast(result.getMessage());
                                 }*/
                             }

                             @Override
                             public void onError(Response<Result<Captcha>> response) {
                                 super.onError(response);
                                 dismissLoading();
                                 toastNetErr();
                             }
                         }
                );
    }

    public void getCode() {
        if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
            toast("????????????????????????");
            return;
        }
        if (!AppUtil.isMobileNO(mEtPhone.getText().toString())) {
            toast("?????????????????????");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("captcha_type", "MOBILE");
        map.put("description", "?????????????????????");
        map.put("expires", 300);
        map.put("username", mEtPhone.getText().toString());
        OkGo.<Result<Captcha>>post(CAPTCHA)
                .tag(this)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<Result<Captcha>>() {
                             @Override
                             public void onSuccess(Response<Result<Captcha>> response) {
                                 Result result = response.body();
                                 captcha = (Captcha) result.getData();
                                 if (result.isSuccess()) {
                                     mTvGetCode.startCountDown(59);
                                     toast("?????????????????????");
                                 } else {
                                     toast(result.getMessage());
                                 }
                             }

                             @Override
                             public void onError(Response<Result<Captcha>> response) {
                                 super.onError(response);
                                 toastNetErr();
                             }
                         }
                );
    }

    public void login() {
        if (mTvLogin.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.corner_green).getConstantState())) {
            if (mIvCheck.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.icon_uncheck).getConstantState())) {
                toast("??????????????????????????????????????????");
            } else {
                if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                    toast("????????????????????????");
                    return;
                }
                if (!AppUtil.isMobileNO(mEtPhone.getText().toString())) {
                    toast("?????????????????????");
                    return;
                }
                if (TextUtils.isEmpty(mEtCode.getText().toString())) {
                    toast("????????????????????????");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("captcha", mEtCode.getText().toString());
                map.put("captcha_session_id", captcha == null ? "" : captcha.getCaptcha_session_id());
                map.put("username", mEtPhone.getText().toString());
                showLoading();
                OkGo.<Result<Login>>post(CAPTCHA_LOGIN)
                        .tag(this)
                        .upJson(gson.toJson(map))
                        .execute(new JsonCallback<Result<Login>>() {
                                     @Override
                                     public void onSuccess(Response<Result<Login>> response) {
                                         Result result = response.body();
                                         if (result.isSuccess()) {
                                             Login login = (Login) result.getData();
                                             SpUtil.putStr(SpConstant.LOGIN_INFO, gson.toJson(login));

                                             OkGo.<Result<DriverInfo>>get(GET_DRIVER)
                                                     .tag(this)
                                                     .headers("token", AppUtil.getToken())
                                                     .params("user_id", AppUtil.getUserId())
                                                     .execute(new JsonCallback<Result<DriverInfo>>() {
                                                                  @Override
                                                                  public void onSuccess(Response<Result<DriverInfo>> response) {
                                                                      dismissLoading();
                                                                      Result result = response.body();
                                                                      if (result.isSuccess()) {
                                                                          DriverInfo driverInfo = (DriverInfo) result.getData();
                                                                          AppUtil.setDriverInfo(driverInfo);
                                                                          EventBusUtil.post(MessageConstant.NOTIFY_INFO);
                                                                          startActivityFinish(MainActivity.class);
                                                                      }
                                                                  }

                                                                  @Override
                                                                  public void onError(Response<Result<DriverInfo>> response) {
                                                                      super.onError(response);
                                                                      dismissLoading();
                                                                      toast(result.getMessage());
                                                                  }
                                                              }
                                                     );
                                         } else {
                                             dismissLoading();
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
            }
        }
    }
}
