package com.bby.youlianwallet.holder;

import android.view.View;
import android.widget.TextView;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.adapter.CommonRecycleAdapter;
import com.bby.youlianwallet.model.Person;


/**
 * Created by yq05481 on 2017/1/3.
 */

public class RecyclePersonViewHolder extends BaseRecyleViewHolder<Person> {
    public RecyclePersonViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final Person model, int position, CommonRecycleAdapter adapter) {
        final TextView nameTv = (TextView) getView(R.id.recycler_view_test_item_person_name_tv);
        final TextView ageTv = (TextView) getView(R.id.recycler_view_test_item_person_age_tv);
        final View rootView = getView(R.id.root_view);
        nameTv.setText(model.getName());
        ageTv.setText(model.getAge());
    }
}
