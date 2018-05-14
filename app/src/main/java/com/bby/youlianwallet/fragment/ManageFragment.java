package com.bby.youlianwallet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.activity.AboutusActivity;
import com.bby.youlianwallet.activity.FeedbackActivity;
import com.bby.youlianwallet.activity.LoginActivity;
import com.bby.youlianwallet.activity.MyYejiActivity;
import com.bby.youlianwallet.activity.MyinfoActivity;
import com.bby.youlianwallet.activity.RechargeActivity;
import com.bby.youlianwallet.activity.ShareActivity;
import com.bby.youlianwallet.activity.TransferMoneyActivity;
import com.bby.youlianwallet.activity.USTDActivity;
import com.bby.youlianwallet.activity.WalletAddressActivity;
import com.bby.youlianwallet.activity.WithdrawActivity;
import com.bby.youlianwallet.base.LoginUser;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 管理
 *
 * @author 樊亚运
 */
public class ManageFragment extends BaseLazyFragment implements OnClickListener {
    @BindView(R.id.head_img)
    CircleImageView headImg;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.recharge_btn)
    LinearLayout rechargeBtn;
    @BindView(R.id.withdraw_btn)
    LinearLayout withdrawBtn;
    @BindView(R.id.aboutus_btn)
    LinearLayout aboutusBtn;
    @BindView(R.id.myinfo)
    LinearLayout myinfo;
    @BindView(R.id.wallet_address)
    LinearLayout walletAddress;
    @BindView(R.id.share_app)
    LinearLayout shareApp;
    @BindView(R.id.logout_btn)
    LinearLayout logoutBtn;
    @BindView(R.id.tansfer_moeny)
    LinearLayout tansferMoeny;
    @BindView(R.id.my_yeji)
    LinearLayout myYeji;
    @BindView(R.id.feedback_btn)
    LinearLayout feedbackBtn;
    @BindView(R.id.ustd_btn)
    LinearLayout ustdBtn;
    private View view = null;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        ustdBtn.setOnClickListener(this);
        rechargeBtn.setOnClickListener(this);
        withdrawBtn.setOnClickListener(this);
        aboutusBtn.setOnClickListener(this);
        myinfo.setOnClickListener(this);
        shareApp.setOnClickListener(this);
        walletAddress.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        tansferMoeny.setOnClickListener(this);
        myYeji.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
        userName.setText(LoginUser.getLoginPhone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ustd_btn:
                Intent intent0 = new Intent(getActivity(), USTDActivity.class);
                startActivity(intent0);
                break;
            case R.id.recharge_btn:
                Intent intent1 = new Intent(getActivity(), RechargeActivity.class);
                startActivity(intent1);
                break;
            case R.id.withdraw_btn:
                Intent intent2 = new Intent(getActivity(), WithdrawActivity.class);
                startActivity(intent2);
                break;
            case R.id.aboutus_btn:
                Intent intent3 = new Intent(getActivity(), AboutusActivity.class);
                startActivity(intent3);
                break;
            case R.id.myinfo:
                Intent intent4 = new Intent(getActivity(), MyinfoActivity.class);
                startActivity(intent4);
                break;
            case R.id.wallet_address:
                Intent intent5 = new Intent(getActivity(), WalletAddressActivity.class);
                startActivity(intent5);
                break;
            case R.id.share_app:
                Intent intent6 = new Intent(getActivity(), ShareActivity.class);
                startActivity(intent6);
                break;
            case R.id.tansfer_moeny:
                Intent intent7 = new Intent(getActivity(), TransferMoneyActivity.class);
                startActivity(intent7);
                break;
            case R.id.my_yeji:
                Intent intent8 = new Intent(getActivity(), MyYejiActivity.class);
                startActivity(intent8);
                break;
            case R.id.feedback_btn:
                Intent intent9 = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent9);
                break;
            case R.id.logout_btn:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                MyApplication.exitApp();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
