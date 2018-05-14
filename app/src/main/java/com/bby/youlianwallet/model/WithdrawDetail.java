package com.bby.youlianwallet.model;

import com.bby.youlianwallet.type.TypeFactory;

/**
 * Created by fanyy on 2018/4/2.
 */

public class WithdrawDetail implements Visitable{
    private int type;
    private String time;
    private Long betaNum;
    private int orderStatus;
    private String payAddress;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getBetaNum() {
        return betaNum;
    }

    public void setBetaNum(Long betaNum) {
        this.betaNum = betaNum;
    }

    public String getPayAddress() {
        return payAddress;
    }

    public void setPayAddress(String payAddress) {
        this.payAddress = payAddress;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
