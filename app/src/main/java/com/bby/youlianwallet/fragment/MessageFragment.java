package com.bby.youlianwallet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.bby.youlianwallet.R;
import com.bby.youlianwallet.activity.LoginActivity;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.Notice;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.model.Visitable;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 
 * 消息
 * @author 樊亚运
 *
 */
public class MessageFragment extends BaseLazyFragment implements OnClickListener {
	private View view = null;
	private Unbinder unbinder;
	RecyclerView recyclerView;
	CommonAdapter adapter;
	List<Visitable> models = new ArrayList<Visitable>();
	XRefreshView xRefreshView;
	LinearLayoutManager layoutManager;
	private SimpleDateFormat format3=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_message, container, false);
		unbinder = ButterKnife.bind(this, view);
		init();
		return view;
	}

	private void init() {
		xRefreshView = (XRefreshView) view.findViewById(R.id.xrefreshview);
		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_test_rv);
		recyclerView.setHasFixedSize(true);

		adapter = new CommonAdapter(models, getActivity());
		notice();
		// 设置静默加载模式
//        xRefreshView1.setSilenceLoadMore();
		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		// 静默加载模式不能设置footerview
		recyclerView.setAdapter(adapter);
		//设置刷新完成以后，headerview固定的时间
		xRefreshView.setPinnedTime(1000);
		xRefreshView.setMoveForHorizontal(true);
		xRefreshView.setPullLoadEnable(true);
		xRefreshView.setAutoLoadMore(false);
		adapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
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
						models.clear();
						notice();
						xRefreshView.stopRefresh();
					}
				});
			}

			@Override
			public void onLoadMore(boolean isSilence) {
				new Handler().postDelayed(new Runnable() {
					public void run() {
//                        for (int i = 0; i < 6; i++) {
//                            adapter.insert(new Person("More ", mLoadCount + "21"),
//                                    adapter.getAdapterItemCount());
//                        }
//                        mLoadCount++;
//                        if (mLoadCount >= 3) {//模拟没有更多数据的情况
//                            xRefreshView.setLoadComplete(true);
//                        } else {
//                            // 刷新完成必须调用此方法停止加载
						xRefreshView.stopLoadMore(true);
//                            //当数据加载失败 不需要隐藏footerview时，可以调用以下方法，传入false，不传默认为true
//                            // 同时在Footerview的onStateFinish(boolean hideFooter)，可以在hideFooter为false时，显示数据加载失败的ui
////                            xRefreshView1.stopLoadMore(false);
//
//                        }
					}
				}, 1000);
			}
		});
	}

	@Override
	public void onClick(View v) {
	}

	private void notice() {
		retrofit2.Call<ResultDto> call = ApiClient.getApiAdapter().notice(MyApplication.getToken());
		call.enqueue(new retrofit2.Callback<ResultDto>() {
			@Override
			public void onResponse(retrofit2.Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
				if (isFinish || response.body() == null) {
					return;
				}
				com.alibaba.fastjson.JSONObject jsonResult = EntityUtil.ObjectToJson2(response.body());
				if (response.body().getCode() == 0) {
					JSONArray jsonArray = jsonResult.getJSONArray("result");
					int length = jsonArray.size();
					for (int i = 0; i< length; i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String time = format3.format(new Date(jsonObject.getLong("ct")));
						String title = jsonObject.getString("title");
						String img = jsonObject.getString("image");
						Notice notice = new Notice();
						notice.setCt(time);
						notice.setTitle(title);
						notice.setImage(img);
						notice.setId(jsonObject.getLong("id"));
						models.add(notice);
					}
					adapter.notifyDataSetChanged();
				} else if(response.body().getCode() == 700) {
					ToastUtil.showLongToast(getResources().getString(R.string.token_error));
					Intent intent = new Intent(getActivity(),LoginActivity.class);
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

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
