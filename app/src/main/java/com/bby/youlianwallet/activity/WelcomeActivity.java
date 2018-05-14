package com.bby.youlianwallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class WelcomeActivity extends BaseActivity implements OnClickListener {

	private int num = 1;
	private Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			// 需要做的事:发送消息
			num++;
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (num % 4 == 0) {
					timer.cancel();
					Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
				}
				super.handleMessage(msg);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);

		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		SharedPreferences sp = getSharedPreferences("login", Activity.MODE_PRIVATE);
		String token = sp.getString("token", "");
		if (!token.equals("")){
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		else {
			timer.schedule(task,1000,500);

		}
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
