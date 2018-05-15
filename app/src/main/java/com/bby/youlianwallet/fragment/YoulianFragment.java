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
import com.bby.youlianwallet.base.BaseUrl;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.Notice;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.model.Visitable;
import com.bby.youlianwallet.model.Youlian;
import com.bby.youlianwallet.model.YoulianHead;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 
 * 消息
 * @author 樊亚运
 *
 */
public class YoulianFragment extends BaseLazyFragment implements OnClickListener {
	private View view = null;
	private Unbinder unbinder;
	RecyclerView recyclerView;
	CommonAdapter adapter;
	List<Visitable> models = new ArrayList<Visitable>();
	LinearLayoutManager layoutManager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_youlian, container, false);
		unbinder = ButterKnife.bind(this, view);
		init();
		return view;
	}

	private void init() {
		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_test_rv);
		recyclerView.setHasFixedSize(true);

		adapter = new CommonAdapter(models, getActivity());
		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
		YoulianHead youlianHead = new YoulianHead();
		models.add(youlianHead);
		getInfo();
	}

	private void getInfo() {
		retrofit2.Call<ResultDto> call = ApiClient.getApiAdapter().consultation(MyApplication.getToken());
		call.enqueue(new retrofit2.Callback<ResultDto>() {
			@Override
			public void onResponse(retrofit2.Call<ResultDto> call, retrofit2.Response<ResultDto> response) {
				if (isFinish || response.body() == null) {
					return;
				}
				JSONObject jsonResult = EntityUtil.ObjectToJson2(response.body());
				if (response.body().getCode() == 0) {
					JSONArray jsonArray = jsonResult.getJSONArray("result");
					int length = jsonArray.size();
					for (int i = 0; i< length; i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						Youlian record = new Youlian();
						record.setId(jsonObject.getInteger("id"));
						record.setTime(jsonObject.getString("ct"));
						record.setImg(jsonObject.getString("image"));
						record.setTitle(jsonObject.getString("title"));
						record.setUrl(jsonObject.getString("url"));
						record.setDesc(jsonObject.getString("desc"));
						models.add(record);
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
	public void onClick(View v) {
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
}
