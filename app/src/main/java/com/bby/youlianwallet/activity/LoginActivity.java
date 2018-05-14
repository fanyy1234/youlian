package com.bby.youlianwallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.LoginUser;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EdittextUtil;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.Md5Util;
import com.bby.youlianwallet.util.StringUtil;
import com.bby.youlianwallet.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.register_btn)
    LinearLayout registerBtn;
    @BindView(R.id.forget_pwd_btn)
    LinearLayout forgetPwdBtn;
    private View loginBtn;
    String phoneStr,pwdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();

        SharedPreferences sp = getSharedPreferences("login", Activity.MODE_PRIVATE);
        String token = sp.getString("token", "");
        if (!token.equals("")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initView() {
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        forgetPwdBtn.setOnClickListener(this);
        EdittextUtil.emojiDelete(loginPassword);
        EdittextUtil.enterHidden(this,loginPhone);
        EdittextUtil.enterHidden(this,loginPassword);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                phoneStr = loginPhone.getText().toString().trim();
                pwdStr = loginPassword.getText().toString().trim();
                if (StringUtil.allIsNotEmpty(phoneStr,pwdStr)){
                    login();
                }
                else {
                    ToastUtil.showLongToast("请输入完整信息");
                }
                break;
            case R.id.register_btn:
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.forget_pwd_btn:
                Intent forgetPwdIntent = new Intent(getApplicationContext(), ForgetPwdActivity.class);
                startActivity(forgetPwdIntent);
                break;
            default:
                break;
        }
    }

    private void login() {
        retrofit2.Call<ResultDto> call = ApiClient.getApiAdapter().login(phoneStr,Md5Util.getMD5(pwdStr));
        call.enqueue(new retrofit2.Callback<ResultDto>() {
            @Override
            public void onResponse(retrofit2.Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    LoginUser.setLoginPhone(jsonResult.getString("mobile"));
                    LoginUser.setNickName(jsonResult.getString("username"));
                    LoginUser.setRecommendCode(jsonResult.getString("recommendedCode"));
                    SharedPreferences sp = getSharedPreferences("login", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token", jsonResult.getString("token"));
                    editor.putString("mobile", jsonResult.getString("mobile"));
                    editor.putString("username", jsonResult.getString("username"));
                    editor.putString("recommendedCode", jsonResult.getString("recommendedCode"));
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showLongToast(response.body().getMessage());
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResultDto> call, Throwable t) {
                ToastUtil.showLongToast(getResources().getString(R.string.network_error));
            }
        });
    }
}

