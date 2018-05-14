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
import com.bby.youlianwallet.base.RefreshFlag;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferMoneyActivity extends BaseActivity implements OnClickListener {


    @BindView(R.id.transfer_phone)
    EditText transferPhone;
    @BindView(R.id.transfer_num)
    EditText transferNum;
    @BindView(R.id.pay_password)
    EditText payPassword;
    @BindView(R.id.transfer_submit)
    LinearLayout transferSubmit;
    String phoneStr = "";
    String numStr = "";
    String pwdStr = "";
    @BindView(R.id.transfer_record)
    LinearLayout transferRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfermoney);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        EdittextUtil.priceFormat(transferNum);
        transferSubmit.setOnClickListener(this);
        transferRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.transfer_submit:
                phoneStr = transferPhone.getText().toString().trim();
                numStr = transferNum.getText().toString();
                pwdStr = payPassword.getText().toString().trim();

                if (StringUtil.allIsNotEmpty(phoneStr, numStr, phoneStr)) {
                    if (!StringUtil.isMobileNO(phoneStr)) {
                        ToastUtil.showLongToast("请输入正确的手机号");
                    } else {
                        transferAccounts();
                    }
                } else {
                    ToastUtil.showLongToast("请输入完整信息");
                }
                break;
            case R.id.transfer_record:
                Intent intent = new Intent(this,TransferRecordActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void transferAccounts() {
        Call<ResultDto> call = ApiClient.getApiAdapter().transferAccounts(MyApplication.getToken(), phoneStr, Double.parseDouble(numStr), Md5Util.getMD5(pwdStr));
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                JSONObject jsonResult = EntityUtil.ObjectToJson2(response.body());
                if (response.body().getCode() == 0) {
                    ToastUtil.showLongToast("转账成功");
                    RefreshFlag.setAssetRefresh(true);
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
