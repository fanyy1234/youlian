package com.bby.youlianwallet.model;

import com.bby.youlianwallet.type.TypeFactory;

/**
 * Created by fanyy on 2018/4/2.
 */

public class AssetDetail implements Visitable{
    private int type;
    private String time;
    private Double betaNum;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getBetaNum() {
        return betaNum;
    }

    public void setBetaNum(Double betaNum) {
        this.betaNum = betaNum;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
