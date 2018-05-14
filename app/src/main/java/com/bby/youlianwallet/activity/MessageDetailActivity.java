package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;
import com.bby.youlianwallet.util.URLImageGetter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDetailActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.desc)
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagedetail);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String titleStr = getIntent().getStringExtra("title");
        String descStr = getIntent().getStringExtra("desc");
        long id = getIntent().getExtras().getLong("id");
        noticeDetails(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    private void noticeDetails(long id) {
        retrofit2.Call<ResultDto> call = ApiClient.getApiAdapter().noticeDetails(MyApplication.getToken(),id);
        call.enqueue(new retrofit2.Callback<ResultDto>() {
            @Override
            public void onResponse(retrofit2.Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    title.setText(jsonResult.getString("title"));
                    String descStr = jsonResult.getString("content");
                    desc.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
                    desc.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
                    URLImageGetter imageGetter = new URLImageGetter(MessageDetailActivity.this,desc);
                    if(descStr!=null&&!descStr.equals("")){
                        desc.setText(Html.fromHtml(descStr, imageGetter, null));
                    }

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
            public void onFailure(retrofit2.Call<ResultDto> call, Throwable t) {
                ToastUtil.showLongToast(getResources().getString(R.string.network_error));
            }
        });
    }

}
