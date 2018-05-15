package com.bby.youlianwallet.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;
import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletAddressActivity extends MPermissionsActivity implements OnClickListener {

    @BindView(R.id.bit_address)
    EditText bitAddress;
    @BindView(R.id.bit_btn)
    Button bitBtn;
    @BindView(R.id.ether_address)
    EditText etherAddress;
    @BindView(R.id.ether_btn)
    Button etherBtn;
    @BindView(R.id.littlebit_address)
    EditText littlebitAddress;
    @BindView(R.id.littlebit_btn)
    Button littlebitBtn;
    @BindView(R.id.address_submit_btn)
    LinearLayout addressSubmitBtn;
    String bitStr = "", etherStr = "", littleBitStr = "",usdtStr="";
    @BindView(R.id.usdt_address)
    EditText usdtAddress;
    @BindView(R.id.usdt_btn)
    Button usdtBtn;

    private JSONObject jsonObject;//用于接收服务端返回的结果
    private Handler mHandler = new Handler() {//用于处理服务端返回的结果
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walletaddress);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
        queryAddress();
    }

    private void initView() {
        bitBtn.setOnClickListener(this);
        etherBtn.setOnClickListener(this);
        littlebitBtn.setOnClickListener(this);
        usdtBtn.setOnClickListener(this);
        addressSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bit_btn:
                requestPermission(new String[]{Manifest.permission.CAMERA}, 1);
                break;
            case R.id.ether_btn:
                requestPermission(new String[]{Manifest.permission.CAMERA}, 2);
                break;
            case R.id.littlebit_btn:
                requestPermission(new String[]{Manifest.permission.CAMERA}, 3);
                break;
            case R.id.usdt_btn:
                requestPermission(new String[]{Manifest.permission.CAMERA}, 4);
                break;
            case R.id.address_submit_btn:
                bitStr = bitAddress.getText().toString().trim();
                etherStr = etherAddress.getText().toString().trim();
                littleBitStr = littlebitAddress.getText().toString().trim();
                usdtStr = usdtAddress.getText().toString().trim();
                changeAddress();
                break;
            default:
                break;
        }
    }

    private void queryAddress() {
        Call<ResultDto> call = ApiClient.getApiAdapter().queryAddress(MyApplication.getToken());
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    bitStr = jsonResult.getString("btc");
                    etherStr = jsonResult.getString("eth");
                    littleBitStr = jsonResult.getString("bit");
                    usdtStr = jsonResult.getString("usdt");
                    bitAddress.setText(bitStr);
                    etherAddress.setText(etherStr);
                    littlebitAddress.setText(littleBitStr);
                    usdtAddress.setText(usdtStr);
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

    private void changeAddress() {
        Call<ResultDto> call = ApiClient.getApiAdapter().changeAddress(MyApplication.getToken(), bitStr, etherStr, littleBitStr,usdtStr);
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    ToastUtil.showLongToast("修改成功");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            bitStr = data.getStringExtra("walletAddress");
            bitAddress.setText(bitStr);
        }
        if (requestCode == 1 && resultCode == 2) {
            etherStr = data.getStringExtra("walletAddress");
            etherAddress.setText(etherStr);
        }
        if (requestCode == 1 && resultCode == 3) {
            littleBitStr = data.getStringExtra("walletAddress");
            littlebitAddress.setText(littleBitStr);
        }
        if (requestCode == 1 && resultCode == 4) {
            usdtStr = data.getStringExtra("walletAddress");
            usdtAddress.setText(usdtStr);
        }
    }

    /**
     * 权限成功回调函数
     *
     * @param requestCode
     */
    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 1:
                Intent intent1 = new Intent(this, CaptureActivity.class);
                intent1.putExtra("flag", 1);
                startActivityForResult(intent1, 1);
                break;
            case 2:
                Intent intent2 = new Intent(this, CaptureActivity.class);
                intent2.putExtra("flag", 2);
                startActivityForResult(intent2, 1);
                break;
            case 3:
                Intent intent3 = new Intent(this, CaptureActivity.class);
                intent3.putExtra("flag", 3);
                startActivityForResult(intent3, 1);
                break;
            case 4:
                Intent intent4 = new Intent(this, CaptureActivity.class);
                intent4.putExtra("flag", 4);
                startActivityForResult(intent4, 1);
                break;
        }

    }
}
