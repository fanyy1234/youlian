package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoulianDetailActivity extends BaseActivity implements OnClickListener {


    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_youlian_detail);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title.setText(getIntent().getStringExtra("title"));
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

}
