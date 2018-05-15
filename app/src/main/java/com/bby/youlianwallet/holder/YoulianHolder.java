package com.bby.youlianwallet.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.activity.YoulianDetailActivity;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.model.UsdtRecord;
import com.bby.youlianwallet.model.Youlian;
import com.bumptech.glide.Glide;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class YoulianHolder extends BaseViewHolder<Youlian> {
    public YoulianHolder(View itemView) {
        super(itemView);
    }
    @Override
    public void setUpView(final Youlian model, int position, final CommonAdapter adapter) {
        final ImageView img = (ImageView) getView(R.id.img);
        final View rootView = getView(R.id.root_view);
        Glide.with(adapter.getmContext()).load(model.getImg()).into(img);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adapter.getmContext(), YoulianDetailActivity.class);
                intent.putExtra("url",model.getUrl());
                adapter.getmContext().startActivity(intent);
            }
        });
    }
}
