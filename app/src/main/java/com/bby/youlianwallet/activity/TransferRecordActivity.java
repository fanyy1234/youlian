package com.bby.youlianwallet.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.fragment.TransferRecordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransferRecordActivity extends BaseActivity implements OnClickListener {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    String[] type ={"转账","收款"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuanzhanglist);
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
            TransferRecordFragment fragment = new TransferRecordFragment();
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
        switch (v.getId()) {
            default:
                break;
        }
    }
}
