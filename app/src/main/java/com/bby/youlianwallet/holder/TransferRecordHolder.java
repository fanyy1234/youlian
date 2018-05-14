package com.bby.youlianwallet.holder;

import android.view.View;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.model.TransferRecord;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class TransferRecordHolder extends BaseViewHolder<TransferRecord> {
    public TransferRecordHolder(View itemView) {
        super(itemView);
    }
    //1充值2提现3利息4注册赠送5释放
    @Override
    public void setUpView(final TransferRecord model, int position, CommonAdapter adapter) {
        final TextView account = (TextView) getView(R.id.account);
        final TextView time = (TextView) getView(R.id.time);
        final TextView moeny = (TextView) getView(R.id.moeny);
        account.setText(model.getAccount());
        time.setText(model.getTime());
        moeny.setText(model.getMoeny().toString());
    }
}
