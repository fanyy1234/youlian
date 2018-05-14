package com.bby.youlianwallet.holder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.activity.MessageDetailActivity;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.base.MyApplication;
import com.bby.youlianwallet.model.Notice;
import com.bumptech.glide.Glide;

/**
 * Created by fanyy on 2018/4/7.
 */

public class NoticeViewHolder extends BaseViewHolder<Notice> {
    public NoticeViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final Notice model, int position, final CommonAdapter adapter) {
        final TextView title = (TextView) getView(R.id.title);
        final TextView time = (TextView) getView(R.id.ct);
        final ImageView img = (ImageView) getView(R.id.notice_img);
        final View item = getView(R.id.notice_item);
        title.setText(model.getTitle());
        time.setText(model.getCt());
        ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
        layoutParams.width = MyApplication.screenWidth/5;
        layoutParams.height = MyApplication.screenWidth/5;
        img.setLayoutParams(layoutParams);
        Glide.with(adapter.getmContext()).load(model.getImage()).into(img);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adapter.getmContext(), MessageDetailActivity.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("id",model.getId());
                adapter.getmContext().startActivity(intent);
            }
        });
    }
}
