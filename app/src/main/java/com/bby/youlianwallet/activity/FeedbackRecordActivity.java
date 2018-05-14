package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.bby.youlianwallet.R;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.base.BaseUrl;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.FeedBack;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.model.Visitable;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class FeedbackRecordActivity extends BaseActivity implements OnClickListener {
	RecyclerView recyclerView;
	CommonAdapter adapter;
	List<Visitable> models = new ArrayList<Visitable>();
	XRefreshView xRefreshView;
	LinearLayoutManager layoutManager;
	private int pageNo = 1;
	private Boolean isBottom = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_record);
		SysApplication.getInstance().addActivity(this);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		xRefreshView = (XRefreshView) findViewById(R.id.xrefreshview);
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view_test_rv);
		recyclerView.setHasFixedSize(true);


		adapter = new CommonAdapter(models, this);
		initData();
		// 设置静默加载模式
//        xRefreshView1.setSilenceLoadMore();
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		// 静默加载模式不能设置footerview
		recyclerView.setAdapter(adapter);
		//设置刷新完成以后，headerview固定的时间
		xRefreshView.setPinnedTime(1000);
		xRefreshView.setMoveForHorizontal(true);
		xRefreshView.setPullLoadEnable(true);
		xRefreshView.setAutoLoadMore(false);
		adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
		xRefreshView.enableReleaseToLoadMore(true);
		xRefreshView.enableRecyclerViewPullUp(true);
		xRefreshView.enablePullUpWhenLoadCompleted(true);
		//设置静默加载时提前加载的item个数
//        xefreshView1.setPreLoadCount(4);
		//设置Recyclerview的滑动监听
		xRefreshView.setOnRecyclerViewScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
			}
		});
		xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
			@Override
			public void onRefresh(boolean isPullDown) {
				new Handler().post(new Runnable() {
					@Override
					public void run() {
						initData();
						xRefreshView.stopRefresh();
					}
				});
			}

			@Override
			public void onLoadMore(boolean isSilence) {
				new Handler().postDelayed(new Runnable() {
					public void run() {
						pageNo++;
						record();
						if (isBottom == true) {//模拟没有更多数据的情况
							xRefreshView.setLoadComplete(true);
						} else {
//                            // 刷新完成必须调用此方法停止加载
							xRefreshView.stopLoadMore(true);
//                            //当数据加载失败 不需要隐藏footerview时，可以调用以下方法，传入false，不传默认为true
//                            // 同时在Footerview的onStateFinish(boolean hideFooter)，可以在hideFooter为false时，显示数据加载失败的ui
////                            xRefreshView1.stopLoadMore(false);

						}
					}
				}, 1000);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			default:
			break;
		}
	}
	private void initData() {
		models.clear();
		pageNo = 1;
		isBottom = false;
		record();
	}
	private void record() {
		Call<ResultDto> call = ApiClient.getApiAdapter().opinionReply(MyApplication.getToken(),pageNo, BaseUrl.pageSize);
		call.enqueue(new Callback<ResultDto>() {
			@Override
			public void onResponse(Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
				if (isFinish || response.body() == null) {
					return;
				}
				com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson2(response.body());
				if (response.body().getCode() == 0) {
					JSONArray jsonArray = jsonResult.getJSONArray("result");
					int length = jsonArray.size();
					for (int i = 0; i < length; i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						int state = jsonObject.getInteger("state");
						String reply = jsonObject.getString("reply");
						String content = jsonObject.getString("content");
						String time = jsonObject.getString("createTime");
						FeedBack feedBack = new FeedBack();
						feedBack.setAnswer(reply);
						feedBack.setQuestion(content);
						feedBack.setState(state);
						feedBack.setTime(time);
						models.add(feedBack);
					}
					adapter.notifyDataSetChanged();
					if (length == 0) {
						isBottom = true;
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
			public void onFailure(Call<ResultDto> call, Throwable t) {
				ToastUtil.showLongToast(getResources().getString(R.string.network_error));
			}
		});
	}
}
