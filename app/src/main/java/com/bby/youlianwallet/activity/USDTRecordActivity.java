package com.bby.youlianwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.fragment.OrderListFragment;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class USDTRecordActivity extends BaseActivity implements OnClickListener {
	@BindView(R.id.tab_layout)
	TabLayout tabLayout;
	@BindView(R.id.view_pager)
	ViewPager viewPager;
	String[] type ={"未完成","已完成","已取消"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usdt_record);
		SysApplication.getInstance().addActivity(this);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
		tabLayout.setupWithViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(2);
		viewPager.setCurrentItem(0);
	}
	public class TabAdapter extends FragmentPagerAdapter {
		public TabAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return type.length;
		}

		@Override
		public Fragment getItem(int position) {
			OrderListFragment fragment = new OrderListFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("status",position);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return type[position];
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			default:
			break;
		}
	}

}
