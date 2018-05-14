package com.bby.youlianwallet.model;

import com.bby.youlianwallet.type.TypeFactory;

/**
 * Created by fanyy on 2018/4/2.
 */

public class Youlian implements Visitable{
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
