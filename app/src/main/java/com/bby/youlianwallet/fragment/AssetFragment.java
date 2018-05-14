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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.bby.youlianwallet.R;
import com.bby.youlianwallet.activity.LoginActivity;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.base.BaseUrl;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.RefreshFlag;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.AssetDetail;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.model.Visitable;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 资产
 *
 * @author 樊亚运
 */
public class AssetFragment extends BaseLazyFragment implements OnClickListener {

    RecyclerView recyclerView;
    CommonAdapter adapter;
    List<Visitable> models = new ArrayList<Visitable>();
    XRefreshView xRefreshView;
    LinearLayoutManager layoutManager;
    @BindView(R.id.total_asset)
    TextView totalAsset;
    @BindView(R.id.frozen_asset)
    TextView frozenAsset;
    @BindView(R.id.release_asset)
    TextView releaseAsset;
    @BindView(R.id.asset_headview)
    LinearLayout assetHeadview;
    @BindView(R.id.asset_head_imageview)
    ImageView assetHeadImageview;
    @BindView(R.id.lock_asset)
    TextView lockAsset;
    private int mLoadCount = 0;
    private Unbinder unbinder;
    private View view = null;
    //1充值2提现3利息4注册赠送5释放
    String[] typeArr = {"", "买币", "提币", "利息", "注册赠送", "释放"};
    private int pageNo = 1;
    private Boolean isBottom = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_asset, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        totalAssets();
        return view;
    }

    private void init() {

        xRefreshView = (XRefreshView) view.findViewById(R.id.xrefreshview);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_test_rv);
        recyclerView.setHasFixedSize(true);


        adapter = new CommonAdapter(models, getActivity());
        initData();
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
                        initData();
                        totalAssets();
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
                        pageNo++;
                        capitalRecord();
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

    private void initData() {
        models.clear();
        pageNo = 1;
        isBottom = false;
        capitalRecord();
//        for(int i=0;i<10;i++){
//            AssetDetail assetDetail = new AssetDetail();
//            assetDetail.setType("充值");
//            assetDetail.setTime("2018-02-02");
//            assetDetail.setBetaNum(3000l+i);
//            models.add(assetDetail);
//        }
    }

    @Override
    public void onClick(View v) {
    }

    private void totalAssets() {
        Call<ResultDto> call = ApiClient.getApiAdapter().totalAssets(MyApplication.getToken());
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    BigDecimal totalDecimal = jsonResult.getBigDecimal("totaAssets");
                    BigDecimal frozenDecimal = jsonResult.getBigDecimal("frozenAssets");
                    BigDecimal releaseDecimal = jsonResult.getBigDecimal("releaseAssets");
                    BigDecimal lockDecimal = jsonResult.getBigDecimal("lockAssets");
                    totalAsset.setText(totalDecimal.toString());
                    frozenAsset.setText(frozenDecimal.toString());
                    releaseAsset.setText(releaseDecimal.toString());
                    lockAsset.setText(lockDecimal.toString());
                } else if (response.body().getCode() == 700) {
                    ToastUtil.showLongToast(getResources().getString(R.string.token_error));
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    SysApplication.getInstance().exit();
                } else {
                    ToastUtil.showLongToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultDto> call, Throwable t) {
                ToastUtil.showLongToast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void capitalRecord() {
        Call<ResultDto> call = ApiClient.getApiAdapter().capitalRecord(MyApplication.getToken(), pageNo, BaseUrl.pageSize);
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                JSONObject jsonResult = EntityUtil.ObjectToJson2(response.body());
                if (response.body().getCode() == 0) {
                    JSONArray jsonArray = jsonResult.getJSONArray("result");
                    int length = jsonArray.size();
                    for (int i = 0; i < length; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int type = jsonObject.getInteger("type");
                        String time = jsonObject.getString("lastmodifiedTime");
                        Double num = jsonObject.getDouble("number");
                        AssetDetail assetDetail = new AssetDetail();
                        assetDetail.setType(type);
                        assetDetail.setTime(time);
                        assetDetail.setBetaNum(num);
                        models.add(assetDetail);
                    }
                    adapter.notifyDataSetChanged();
                    if (length == 0) {
                        isBottom = true;
                    }
                } else if (response.body().getCode() == 700) {
                    ToastUtil.showLongToast(getResources().getString(R.string.token_error));
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    SysApplication.getInstance().exit();
                } else {
                    ToastUtil.showLongToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultDto> call, Throwable t) {
                ToastUtil.showLongToast(getResources().getString(R.string.network_error));
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (RefreshFlag.isAssetRefresh()) {//再次切换回这个fragment时，触发刷新
            initData();
            totalAssets();
            RefreshFlag.setAssetRefresh(false);
        }
    }

    ;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
