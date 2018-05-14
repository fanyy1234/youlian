package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class ModelActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_model);
		SysApplication.getInstance().addActivity(this);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			default:
			break;
		}
	}

	private void getInfo() {
		Call<ResultDto> call = ApiClient.getApiAdapter().notice(MyApplication.getToken());
		call.enqueue(new Callback<ResultDto>() {
			@Override
			public void onResponse(Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
				if (isFinish || response.body() == null) {
					return;
				}
				com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson2(response.body());
				if (response.body().getCode() == 0) {
					//TODO
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
