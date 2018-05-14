package com.bby.youlianwallet.model;

import com.bby.youlianwallet.type.TypeFactory;

/**
 * Created by fanyy on 2018/4/2.
 */

public class Notice implements Visitable{
    private String ct;
    private String desc;
    private Long id;
    private String image;
    private String title;

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
