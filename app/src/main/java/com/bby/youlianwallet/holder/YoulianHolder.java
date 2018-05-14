package com.bby.youlianwallet.holder;

import android.view.View;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.model.UsdtRecord;
import com.bby.youlianwallet.model.Youlian;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class YoulianHolder extends BaseViewHolder<Youlian> {
    public YoulianHolder(View itemView) {
        super(itemView);
    }
    @Override
    public void setUpView(final Youlian model, int position, CommonAdapter adapter) {
        int id = model.getId();

    }
}
