package com.bby.youlianwallet.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.base.RefreshFlag;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.model.ResultDto;
import com.bby.youlianwallet.network.ApiClient;
import com.bby.youlianwallet.util.EdittextUtil;
import com.bby.youlianwallet.util.EntityUtil;
import com.bby.youlianwallet.util.ToastUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.beta_num)
    EditText betaNum;
    @BindView(R.id.beta_price)
    TextView betaPrice;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.paytype_select)
    LinearLayout paytypeSelect;
    @BindView(R.id.withdraw_price)
    TextView withdrawPrice;
    @BindView(R.id.withdraw_num)
    TextView withdrawNum;
    @BindView(R.id.withdraw_url)
    TextView withdrawUrl;
    @BindView(R.id.withdraw_submit)
    LinearLayout withdrawSubmit;
    @BindView(R.id.recharge_available_num)
    EditText rechargeAvailableNum;
    @BindView(R.id.withdraw_record)
    LinearLayout withdrawRecord;
    private PopupWindow popupWindow;
    private View paytype1, paytype2, paytype3;
    private TextView typetext1, typetext2, typetext3;
    private int selectedType = 4;
    private int selectedColor;
    String bitStr = "", etherStr = "", littleBitStr = "";
    String myWalletUrl = "";
    private String[] payTypeArr = {"", "比特币", "以太坊", "小比特"};
    long currentTime = 0;
    String numString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_withdraw);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();
        queryAddress();
        totalAssets();
    }

    private void initView() {
        selectedColor = ContextCompat.getColor(this, R.color.appcolor);
        EdittextUtil.enterHidden(this, betaNum);
        EdittextUtil.rechargeNumFormat(betaNum);
        paytypeSelect.setOnClickListener(this);
        withdrawSubmit.setOnClickListener(this);
        withdrawRecord.setOnClickListener(this);
        betaNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String numStr = editable.toString();
                if (numStr.equals("0")) {
                    withdrawNum.setText("0");
                } else if (numStr.equals("")) {
                    withdrawNum.setText("");
                } else {
                    long pauseTime = System.currentTimeMillis() - currentTime;
                    currentTime = System.currentTimeMillis();
                    rechargeData(Long.parseLong(numStr));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        numString = betaNum.getText().toString();
        switch (v.getId()) {
            case R.id.paytype_select:
//                showPopwindow(selectPayTypeView());
                break;
            case R.id.withdraw_submit:
                myWalletUrl = withdrawUrl.getText().toString();
                if (numString.equals("") || numString.equals("0")) {
                    Toast.makeText(getApplicationContext(), "提币数量必须大于0", Toast.LENGTH_LONG).show();
                } else if (myWalletUrl.equals("")) {
                    ToastUtil.showLongToast(payTypeArr[selectedType] + "的钱包地址为空,无法提交");
                } else {
                    withdrawals(Long.parseLong(numString));
                }
                break;
            case R.id.withdraw_record:
                Intent intent = new Intent(this,WithdrawListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void rechargeData(long paynum) {
        Call<ResultDto> call = ApiClient.getApiAdapter().rechargeData(MyApplication.getToken(), selectedType, paynum);
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    BigDecimal beitaPriceBd = jsonResult.getBigDecimal("beitaPrice");
                    BigDecimal payPriceBd = jsonResult.getBigDecimal("payPrice");
                    BigDecimal payNumberBd = jsonResult.getBigDecimal("payNumber");
                    betaPrice.setText(beitaPriceBd.toString());
                    withdrawPrice.setText(payPriceBd.toString());
                    withdrawNum.setText(payNumberBd.toString());
                } else if (response.body().getCode() == 700) {
                    ToastUtil.showLongToast(getResources().getString(R.string.token_error));
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

    private void queryAddress() {
        Call<ResultDto> call = ApiClient.getApiAdapter().queryAddress(MyApplication.getToken());
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    bitStr = jsonResult.getString("btc");
                    etherStr = jsonResult.getString("eth");
                    littleBitStr = jsonResult.getString("bit");
                    withdrawUrl.setText(bitStr);
                } else if (response.body().getCode() == 700) {
                    ToastUtil.showLongToast(getResources().getString(R.string.token_error));
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

    private void withdrawals(long paynum) {
        Call<ResultDto> call = ApiClient.getApiAdapter().withdrawals(MyApplication.getToken(), selectedType, paynum, myWalletUrl);
        call.enqueue(new Callback<ResultDto>() {
            @Override
            public void onResponse(Call<ResultDto> call, Response<ResultDto> response) {
                if (isFinish || response.body() == null) {
                    return;
                }
                JSONObject jsonResult = EntityUtil.ObjectToJson(response.body());
                if (response.body().getCode() == 0) {
                    ToastUtil.showLongToast("提交成功");
                    RefreshFlag.setAssetRefresh(true);
                    finish();
                } else if (response.body().getCode() == 700) {
                    ToastUtil.showLongToast(getResources().getString(R.string.token_error));
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
                    BigDecimal releaseDecimal = jsonResult.getBigDecimal("releaseAssets");
                    rechargeAvailableNum.setText(releaseDecimal.toString());
                } else if (response.body().getCode() == 700) {
                    ToastUtil.showLongToast(getResources().getString(R.string.token_error));
                    Intent intent = new Intent(WithdrawActivity.this, LoginActivity.class);
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

    //设置屏幕背景半透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void showPopwindow(View view) {
        backgroundAlpha(0.7f);
        popupWindow = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        popupWindow.showAtLocation(
                this.findViewById(R.id.paytype_select),
                Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    private View selectPayTypeView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_paytype, null);
        paytype1 = view.findViewById(R.id.paytype1);
        paytype2 = view.findViewById(R.id.paytype2);
        paytype3 = view.findViewById(R.id.paytype3);
        typetext1 = (TextView) view.findViewById(R.id.paytype_text1);
        typetext2 = (TextView) view.findViewById(R.id.paytype_text2);
        typetext3 = (TextView) view.findViewById(R.id.paytype_text3);
        if (selectedType == 1) {
            typetext1.setTextColor(selectedColor);
        } else if (selectedType == 2) {
            typetext2.setTextColor(selectedColor);
        } else if (selectedType == 3) {
            typetext3.setTextColor(selectedColor);
        }
        paytype1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 1;
                if (!numString.equals("") && !numString.equals("0")) {
                    rechargeData(Long.parseLong(numString));
                }
                payType.setText("比特币");
                withdrawUrl.setText(bitStr);
                popupWindow.dismiss();
            }
        });
        paytype2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 2;
                if (!numString.equals("") && !numString.equals("0")) {
                    rechargeData(Long.parseLong(numString));
                }
                payType.setText("以太坊");
                withdrawUrl.setText(etherStr);
                popupWindow.dismiss();
            }
        });
        paytype3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = 3;
                if (!numString.equals("") && !numString.equals("0")) {
                    rechargeData(Long.parseLong(numString));
                }
                payType.setText("小比特");
                withdrawUrl.setText(littleBitStr);
                popupWindow.dismiss();
            }
        });
        return view;
    }

}
