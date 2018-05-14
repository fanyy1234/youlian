package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.LoginUser;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyinfoActivity extends BaseActivity implements OnClickListener {


    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.my_recommend_code)
    TextView myRecommendCode;
    @BindView(R.id.logout_btn)
    LinearLayout logoutBtn;
    @BindView(R.id.set_paypwd)
    LinearLayout setPaypwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        phone.setText(LoginUser.getLoginPhone());
        nickName.setText(LoginUser.getNickName());
        myRecommendCode.setText(LoginUser.getRecommendCode());
        logoutBtn.setOnClickListener(this);
        setPaypwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_btn:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
                MainActivity.mainActivity.finish();
                MyApplication.exitApp();
                break;
            case R.id.set_paypwd:
                Intent intent1 = new Intent(this,PayPwdActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

}
