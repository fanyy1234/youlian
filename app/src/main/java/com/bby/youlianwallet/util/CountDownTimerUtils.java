package com.bby.youlianwallet.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.bby.youlianwallet.R;

/**
 * “修改密码”页面，按钮上的倒计时效果
 * @author 樊亚运
 *
 */
public class CountDownTimerUtils extends CountDownTimer{
	private Button theButton;
	private View view1;
	private View view2;
	private Context mContext;
	public CountDownTimerUtils(View view1,View view2,Button button,long millisInFuture, long countDownInterval,Context mContext) {
		super(millisInFuture, countDownInterval);
		this.theButton = button;
		this.mContext = mContext;
//		this.view1 = view1;
//		this.view2 = view2;
	}

	@SuppressLint("ResourceAsColor") @Override
	public void onTick(long millisUntilFinished) {
		theButton.setClickable(false); //设置不可点击
		theButton.setText(millisUntilFinished / 1000 + "");  //设置倒计时时间  
		theButton.setTextColor(R.color.fontgrey);
		theButton.setBackgroundResource(R.drawable.btn_background_white);
	}

	@SuppressLint("ResourceAsColor") @Override
	public void onFinish() {
		theButton.setText("获取验证码");
		theButton.setTextColor(mContext.getResources().getColor(R.color.white));
		theButton.setBackgroundResource(R.drawable.btn_background_appcolor);
		theButton.setClickable(true);
//		theButton.setVisibility(View.GONE);
//		view1.setVisibility(View.VISIBLE);
//		view2.setVisibility(View.VISIBLE);
	}

}
