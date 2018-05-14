package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.feedback_record)
    LinearLayout feedbackRecord;
    @BindView(R.id.input_string)
    EditText inputString;
    @BindView(R.id.comit_feedback)
    LinearLayout comitFeedback;
    String opinionStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        feedbackRecord.setOnClickListener(this);
        comitFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_record:
                Intent intent = new Intent(this,FeedbackRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.comit_feedback:
                opinionStr = inputString.getText().toString();
                if (opinionStr.trim().equals("")){
                    ToastUtil.showLongToast("意见不能为空");
                }
                else {
                    comitOpinion();
                }
                break;
            default:
                break;
        }
    }

    private void comitOpinion() {
        Call<ResultDto> call = ApiClient.getApiAdapter().opinion(MyApplication.getToken(),opinionStr);
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                JSONObject jsonResult = EntityUtil.ObjectToJson2(response.body());
                if (response.body().getCode() == 0) {
                    ToastUtil.showLongToast("提交成功");
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
            public void onFailure(Call<ResultDto> call, Throwable t) {
                ToastUtil.showLongToast(getResources().getString(R.string.network_error));
            }
        });
    }
}
