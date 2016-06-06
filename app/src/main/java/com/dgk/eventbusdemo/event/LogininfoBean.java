package com.dgk.eventbusdemo.event;

/**
 * Created by Kevin on 2016/6/6.
 */
public class LoginInfoBean {

    public String msg = "第几次发布的信息：";

    public LoginInfoBean(String msg) {
        this.msg = this.msg + msg;
    }
}
