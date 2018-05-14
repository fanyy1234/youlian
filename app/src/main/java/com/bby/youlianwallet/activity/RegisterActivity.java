package com.bby.youlianwallet.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.CountDownTimerUtils;
import com.bby.youlianwallet.util.EdittextUtil;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.Md5Util;
import com.bby.youlianwallet.util.StringUtil;
import com.bby.youlianwallet.util.ToastUtil;
//import com.squareup.okhttp.Call;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.FormEncodingBuilder;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.RequestBody;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.recommend_code)
    EditText recommendCode;
    @BindView(R.id.register_phone)
    EditText registerPhone;
    @BindView(R.id.check_code)
    EditText checkCode;
    @BindView(R.id.getcode_btn)
    Button getcodeBtn;
    @BindView(R.id.nick_name)
    EditText nickName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.repeat_password)
    EditText repeatPassword;
    @BindView(R.id.register_btn)
    LinearLayout registerBtn;
    String recommendCodeStr,phoneStr,checkCodeStr,nicknameStr,pwdStr,repeatPwdStr;
//    private static int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        EdittextUtil.enterHidden(this,recommendCode);
        EdittextUtil.enterHidden(this,registerPhone);
        EdittextUtil.enterHidden(this,password);
        EdittextUtil.enterHidden(this,checkCode);
        EdittextUtil.enterHidden(this,nickName);
        EdittextUtil.enterHidden(this,repeatPassword);
        EdittextUtil.emojiDelete(recommendCode);
        EdittextUtil.emojiDelete(nickName);
        EdittextUtil.emojiDelete(password);
        EdittextUtil.emojiDelete(repeatPassword);
        getcodeBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcode_btn:
                phoneStr = registerPhone.getText().toString().trim();
                if (phoneStr.equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入手机号",Toast.LENGTH_LONG).show();
                }
                else if(!StringUtil.isMobileNO(phoneStr)){
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号",Toast.LENGTH_LONG).show();
                }
                else {
                    getcodeBtn.setClickable(false);
                    sendCode();
                }
                break;
            case R.id.register_btn:
                phoneStr = registerPhone.getText().toString().trim();
                recommendCodeStr = recommendCode.getText().toString().trim();
                checkCodeStr = checkCode.getText().toString().trim();
                nicknameStr = nickName.getText().toString().trim();
                pwdStr = password.getText().toString().trim();
                repeatPwdStr = repeatPassword.getText().toString().trim();
                if(StringUtil.allIsNotEmpty(phoneStr,recommendCodeStr,checkCodeStr,nicknameStr,pwdStr,repeatPwdStr)){
                    if (!pwdStr.equals(repeatPwdStr)){
                        Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_LONG).show();
                    }
                    else {
                        register();
                    }
                }
                else {
                    ToastUtil.showLongToast("请输入完整信息");
                }
                break;
            default:
                break;
        }
    }

    private void sendCode() {
        retrofit2.Call<ResultDto> call = ApiClient.getApiAdapter().sendCode(phoneStr,0);
        call.enqueue(new retrofit2.Callback<ResultDto>() {
            @Override
            public void onResponse(retrofit2.Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    getcodeBtn.setClickable(true);
                    return;
                }
                com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(null, null, getcodeBtn, 60000, 1000,RegisterActivity.this);
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

    private void register() {
        retrofit2.Call<ResultDto> call = ApiClient.getApiAdapter().register(phoneStr,checkCodeStr,Md5Util.getMD5(pwdStr),recommendCodeStr,nicknameStr);
        call.enqueue(new retrofit2.Callback<ResultDto>() {
            @Override
            public void onResponse(retrofit2.Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    ToastUtil.showLongToast("注册成功");
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
