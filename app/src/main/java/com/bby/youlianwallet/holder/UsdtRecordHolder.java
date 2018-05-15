package com.bby.youlianwallet.holder;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.model.TransferRecord;
import com.bby.youlianwallet.model.UsdtRecord;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class UsdtRecordHolder extends BaseViewHolder<UsdtRecord> {
    public UsdtRecordHolder(View itemView) {
        super(itemView);
    }
    //1充值2提现3利息4注册赠送5释放
    String[] statusArr = {"未审核","审核通过","审核拒绝","已付款","已取消","订单超时"};
    @Override
    public void setUpView(final UsdtRecord model, int position, CommonAdapter adapter) {
        final TextView time = (TextView) getView(R.id.time);
        final TextView type = (TextView) getView(R.id.type);
        final TextView orderNo = (TextView) getView(R.id.order_no);
        final TextView price = (TextView) getView(R.id.price);
        final TextView num = (TextView) getView(R.id.num);
        final TextView priceSum = (TextView) getView(R.id.price_sum);
        final TextView name = (TextView) getView(R.id.name);
        final TextView bank = (TextView) getView(R.id.bank);
        final TextView branch = (TextView) getView(R.id.branch);
        final TextView card_no = (TextView) getView(R.id.card_no);
        final TextView remark = (TextView) getView(R.id.remark);
        final TextView name_copy = (TextView) getView(R.id.name_copy);
        final TextView bank_copy = (TextView) getView(R.id.bank_copy);
        final TextView branch_copy = (TextView) getView(R.id.branch_copy);
        final TextView card_no_copy = (TextView) getView(R.id.card_no_copy);
        final TextView remark_copy = (TextView) getView(R.id.remark_copy);
        final View cancelOrder = getView(R.id.cancel_order);
        final View payNow = getView(R.id.pay_now);
        final View buttonView = getView(R.id.button_view);

        time.setText(model.getTime());
        type.setText(model.getType());
        orderNo.setText(model.getOrderNo());
        price.setText(model.getPrice());
        num.setText(model.getNum());
        priceSum.setText(model.getSumPrice());
        name.setText(model.getName());
        bank.setText(model.getBank());
        branch.setText(model.getBranch());
        remark.setText(model.getRemark());
        card_no.setText(model.getCardNo());
        if (model.getOrderStatus()==0){
            buttonView.setVisibility(View.VISIBLE);
        }
        else {
            buttonView.setVisibility(View.GONE);
        }
        type.setText(statusArr[model.getOrderStatus()]);
        final ClipboardManager cm = (ClipboardManager) (adapter.getmContext().getSystemService(Context.CLIPBOARD_SERVICE));
        name_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cm.setText(model.getName());
            }
        });
        bank_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cm.setText(model.getBank());
            }
        });
        branch_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cm.setText(model.getBranch());
            }
        });
        remark_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cm.setText(model.getRemark());
            }
        });
        card_no_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cm.setText(model.getCardNo());
            }
        });

        cancelOrder.setOnClickListener(adapter.getOnClickListener());
        payNow.setOnClickListener(adapter.getOnClickListener());
        cancelOrder.setTag(position);
        payNow.setTag(position);

    }
}
