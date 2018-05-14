package com.bby.youlianwallet.holder;

import android.view.View;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.model.AssetDetail;
import com.bby.youlianwallet.util.TimeUtil;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class AssetDetailViewHolder extends BaseViewHolder<AssetDetail> {
    public AssetDetailViewHolder(View itemView) {
        super(itemView);
    }
    //1充值2提现3利息4注册赠送5释放
    String[] typeArr = {"", "买币", "提币", "利息", "注册赠送", "释放","转账","收款","业绩"};
    @Override
    public void setUpView(final AssetDetail model, int position, CommonAdapter adapter) {
        final TextView type = (TextView) getView(R.id.type);
        final TextView time = (TextView) getView(R.id.time);
        final TextView beta_num = (TextView) getView(R.id.beta_num);
        int typeInt = model.getType();
        type.setText(typeArr[typeInt]);
        time.setText(TimeUtil.getFormatTime(model.getTime()));
        if (typeInt==2){
            beta_num.setTextColor(adapter.getmContext().getResources().getColor(R.color.appcolor));
        }
        else {
            beta_num.setTextColor(adapter.getmContext().getResources().getColor(R.color.orange));
        }
        beta_num.setText(model.getBetaNum().toString());
    }
}
