package com.bby.youlianwallet.type;

import android.view.View;

import com.bby.youlianwallet.holder.BaseRecyleViewHolder;
import com.bby.youlianwallet.model.Person;


/**
 * Created by yq05481 on 2016/12/30.
 */

public interface RecycleTypeFactory {
    int type(Person person);


    BaseRecyleViewHolder createViewHolder(int type, View itemView);
}
