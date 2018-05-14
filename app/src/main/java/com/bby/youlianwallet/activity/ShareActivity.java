package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.ToastUtil;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.qr_code)
    ImageView qrCode;
    @BindView(R.id.share_url)
    TextView shareUrl;
    private String urlString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
        recommendBeiTa();
    }

    private void initView() {
//        Bitmap bitmap = CreateQrCode.createQRImage("fanf樊亚运ssssssss",400,400,null,null);
//        qrCode.setImageBitmap(bitmap);
//        Glide.with(this).load("").into(qrCode);
//        shareUrl.setText("sssssssssssssssssssssssssssssssssssss");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    private void recommendBeiTa() {
        Call<ResultDto> call = ApiClient.getApiAdapter().recommendBeiTa(MyApplication.getToken());
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                if (response.body().getCode() == 0) {
                    String result = response.body().getResult().toString();
                    Glide.with(ShareActivity.this).load(result).into(qrCode);
                } else if(response.body().getCode() == 700) {
                    ToastUtil.showLongToast(getResources().getString(R.string.token_error));
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    SysApplication.getInstance().exit();
                }else {
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
