package com.dgk.eventbusdemo.event;

/**
 * Created by Kevin on 2016/5/30.
 * EventBus的事件：信息
 */
public class MessageEventD {

    private String msg;

    public MessageEventD(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
