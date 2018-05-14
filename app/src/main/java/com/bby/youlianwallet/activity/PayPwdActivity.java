package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.CountDownTimerUtils;
import com.bby.youlianwallet.util.EdittextUtil;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.Md5Util;
import com.bby.youlianwallet.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayPwdActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.check_code)
    EditText checkCode;
    @BindView(R.id.getcode_btn)
    Button getcodeBtn;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.repeat_password)
    EditText repeatPassword;
    @BindView(R.id.find_pwd_btn)
    LinearLayout findPwdBtn;
    String phoneStr,checkCodeStr,pwdStr,repeatPwdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pwd);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        EdittextUtil.enterHidden(this,phone);
        EdittextUtil.enterHidden(this,password);
        EdittextUtil.enterHidden(this,checkCode);
        EdittextUtil.enterHidden(this,repeatPassword);
        EdittextUtil.emojiDelete(password);
        EdittextUtil.emojiDelete(repeatPassword);
        getcodeBtn.setOnClickListener(this);
        findPwdBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcode_btn:
                phoneStr = phone.getText().toString().trim();
                repeatPwdStr = repeatPassword.getText().toString().trim();
//                if (phoneStr.equals("")) {
//                    Toast.makeText(getApplicationContext(), "请输入手机号",Toast.LENGTH_LONG).show();
//                }
//                else if(!StringUtil.isMobileNO(phoneStr)){
//                    Toast.makeText(getApplicationContext(), "请输入正确的手机号",Toast.LENGTH_LONG).show();
//                }
//                else {
//                    getcodeBtn.setClickable(false);
//                    sendCode();
//                }
                break;
            case R.id.find_pwd_btn:
                repeatPwdStr = repeatPassword.getText().toString().trim();
                if (repeatPwdStr.equals("")){
                    ToastUtil.showLongToast("请输入密码");
                }
                else if (repeatPwdStr.length()!=6){
                    ToastUtil.showLongToast("密码长度必须是6位");
                }
                else {
                    setPwd();
                }
                break;
            default:
                break;
        }
    }

    private void sendCode() {
        retrofit2.Call<ResultDto> call = ApiClient.getApiAdapter().sendCode(phoneStr,1);
        call.enqueue(new retrofit2.Callback<ResultDto>() {
            @Override
            public void onResponse(retrofit2.Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    getcodeBtn.setClickable(true);
                    return;
                }
                com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(null, null, getcodeBtn, 60000, 1000,PayPwdActivity.this);
                    mCountDownTimerUtils.start();
                } else {
                    getcodeBtn.setClickable(true);
                    ToastUtil.showLongToast(response.body().getMessage());
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResultDto> call, Throwable t) {
                getcodeBtn.setClickable(true);
                ToastUtil.showLongToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void changePwd() {
        retrofit2.Call<ResultDto> call = ApiClient.getApiAdapter().forgetPassword(phoneStr,checkCodeStr,Md5Util.getMD5(pwdStr));
        call.enqueue(new retrofit2.Callback<ResultDto>() {
            @Override
            public void onResponse(retrofit2.Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    ToastUtil.showLongToast("密码修改成功");
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
    private void setPwd() {
        retrofit2.Call<ResultDto> call = ApiClient.getApiAdapter().payPassword(MyApplication.getToken(),Md5Util.getMD5(repeatPwdStr));
        call.enqueue(new retrofit2.Callback<ResultDto>() {
            @Override
            public void onResponse(retrofit2.Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                if (response.body().getCode() == 0) {
                    ToastUtil.showLongToast("密码设置成功");
                    finish();
                } else if (response.body().getCode() == 700) {
                    ToastUtil.showLongToast(getResources().getString(R.string.token_error));
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    SysApplication.getInstance().exit();
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
