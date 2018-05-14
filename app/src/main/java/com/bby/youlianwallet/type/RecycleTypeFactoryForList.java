package com.bby.youlianwallet.type;

import android.view.View;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.holder.BaseRecyleViewHolder;
import com.bby.youlianwallet.holder.RecyclePersonViewHolder;
import com.bby.youlianwallet.model.Person;


/**
 * Created by yq05481 on 2016/12/30.
 */

public class RecycleTypeFactoryForList implements RecycleTypeFactory {
    private final int TYPE_RESOURCE_PERSON = R.layout.item_recylerview;

    @Override
    public int type(Person person) {
        return TYPE_RESOURCE_PERSON;
    }


    @Override
    public BaseRecyleViewHolder createViewHolder(int type, View itemView) {

        if(TYPE_RESOURCE_PERSON == type){
            return new RecyclePersonViewHolder(itemView);
        }

        return null;
    }
}
