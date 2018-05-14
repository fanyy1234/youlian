package com.bby.youlianwallet.holder;

import android.view.View;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.adapter.CommonAdapter;
import com.bby.youlianwallet.model.FeedBack;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class FeedbackViewHolder extends BaseViewHolder<FeedBack> {
    public FeedbackViewHolder(View itemView) {
        super(itemView);
    }
    String[] strArr = {"未回复","已回复","已查阅",""};
    @Override
    public void setUpView(final FeedBack model, int position, CommonAdapter adapter) {
        final TextView question = (TextView) getView(R.id.question);
        final TextView answer = (TextView) getView(R.id.answer);
        final TextView status = (TextView) getView(R.id.status);
        final TextView time = (TextView) getView(R.id.time);
        question.setText(model.getQuestion());
        answer.setText(model.getAnswer());
        status.setText(strArr[model.getState()]);
        time.setText(model.getTime());
    }
}
