package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class USTDActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.usdt_yue)
    TextView usdtYue;
    @BindView(R.id.usdt_price)
    TextView usdtPrice;
    @BindView(R.id.usdt_num)
    EditText usdtNum;
    @BindView(R.id.sum_money_)
    TextView sumMoney;
    @BindView(R.id.confirm_buy)
    LinearLayout confirmBuy;
    @BindView(R.id.usdt_record)
    LinearLayout usdtRecord;
    private long bugNum = 1;
    long currentTime = 0;
    String numString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustd);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
        getInfo();
    }

    private void initView() {
        confirmBuy.setOnClickListener(this);
        usdtRecord.setOnClickListener(this);
        usdtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String numStr = editable.toString();
                if (numStr.equals("0")) {
                    sumMoney.setText("0");
                } else if (numStr.equals("")) {
                    sumMoney.setText("");
                } else {
                    long pauseTime = System.currentTimeMillis() - currentTime;
                    currentTime = System.currentTimeMillis();
                    if (pauseTime > 400) {
                        bugNum = Long.parseLong(numStr);
                        getInfo();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_buy:
                numString = usdtNum.getText().toString();
                if (numString.equals("") || numString.equals("0")) {
                    Toast.makeText(getApplicationContext(), "充值数量必须大于0", Toast.LENGTH_LONG).show();
                }
                else {
                    bugNum = Long.parseLong(numString);
                }
                break;
            case R.id.usdt_record:
                Intent intent1 = new Intent(this,USDTRecordActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    private void getInfo() {
        Call<ResultDto> call = ApiClient.getApiAdapter().buyUsdtData(MyApplication.getToken(),bugNum);
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                JSONObject jsonResult = EntityUtil.ObjectToJson2(response.body());
                if (response.body().getCode() == 0) {
                    //TODO
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
    private void buyUsdt() {
        Call<ResultDto> call = ApiClient.getApiAdapter().buyUsdt(MyApplication.getToken(),bugNum);
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                JSONObject jsonResult = EntityUtil.ObjectToJson2(response.body());
                if (response.body().getCode() == 0) {
                    //TODO
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
