package com.bby.youlianwallet.model;


import com.bby.youlianwallet.type.RecycleTypeFactory;

/**
 * Created by yq05481 on 2016/12/30.
 */

public interface RecycleVisitable {
    int type(RecycleTypeFactory typeFactory);
}
