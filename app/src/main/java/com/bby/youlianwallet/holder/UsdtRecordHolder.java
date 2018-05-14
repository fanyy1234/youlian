package com.bby.youlianwallet.holder;

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
    @Override
    public void setUpView(final UsdtRecord model, int position, CommonAdapter adapter) {
        final TextView time = (TextView) getView(R.id.time);
        final TextView type = (TextView) getView(R.id.type);
        final TextView orderNo = (TextView) getView(R.id.order_no);
        final TextView price = (TextView) getView(R.id.price);
        final TextView num = (TextView) getView(R.id.num);
        final TextView priceSum = (TextView) getView(R.id.price_sum);

        time.setText(model.getTime());
        type.setText(model.getType());
        orderNo.setText(model.getOrderNo());
        price.setText(model.getPrice());
        num.setText(model.getNum());
        priceSum.setText(model.getSumPrice());

    }
}
