package com.bby.youlianwallet.model;

import com.bby.youlianwallet.type.TypeFactory;

public class Yeji implements Visitable{
    private String phone;
    private String nickname;
    private Long num;
    private Double ticheng;
    private String time;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Double getTicheng() {
        return ticheng;
    }

    public void setTicheng(Double ticheng) {
        this.ticheng = ticheng;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
