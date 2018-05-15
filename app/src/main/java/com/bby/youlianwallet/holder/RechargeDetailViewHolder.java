package com.bby.youlianwallet.holder;

import android.view.View;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.model.RechargeDetail;

/**
 * Created by fanyy on 2018/4/8.
 */

public class RechargeDetailViewHolder extends BaseViewHolder<RechargeDetail> {
    private static String[] typeArr = {"","比特币","以太坊","小比特","USDT"};
    private static String[] statusArr = {"审核中","已完成","已拒绝",""};
    private static int[] statusColorArr = {R.color.black,R.color.appcolor,R.color.red,R.color.black};
    public RechargeDetailViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final RechargeDetail model, int position, CommonAdapter adapter) {
        final TextView type = (TextView) getView(R.id.pay_type);
        final TextView time = (TextView) getView(R.id.time);
        final TextView beta_num = (TextView) getView(R.id.beta_num);
        final TextView order_status = (TextView) getView(R.id.order_status);
        final TextView payAddress = (TextView) getView(R.id.pay_address);
        type.setText(typeArr[model.getType()]);
        time.setText(model.getTime());
        beta_num.setText(model.getBetaNum().toString());
        int status = model.getOrderStatus();
        order_status.setText(statusArr[status]);
        order_status.setTextColor(adapter.getmContext().getResources().getColor(statusColorArr[status]));
        payAddress.setText(model.getPayAddress());
    }
}
