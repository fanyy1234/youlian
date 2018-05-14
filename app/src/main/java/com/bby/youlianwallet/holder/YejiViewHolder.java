package com.bby.youlianwallet.holder;

import android.view.View;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.model.Yeji;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class YejiViewHolder extends BaseViewHolder<Yeji> {
    public YejiViewHolder(View itemView) {
        super(itemView);
    }
    @Override
    public void setUpView(final Yeji model, int position, CommonAdapter adapter) {
        final TextView phone = (TextView) getView(R.id.phone);
        final TextView nickname = (TextView) getView(R.id.nick_name);
        final TextView num = (TextView) getView(R.id.num);
        final TextView ticheng = (TextView) getView(R.id.ticheng);
        final TextView time = (TextView) getView(R.id.time);
        phone.setText(model.getPhone());
        nickname.setText(model.getNickname());
        num.setText(model.getNum().toString());
        ticheng.setText(model.getTicheng().toString());
        time.setText(model.getTime());
    }
}
