package com.idealbank.ib_secretassetcontrol.Netty.bean;

/**
 * Created by zw on 2018/9/11.
 */


import java.io.Serializable;

public class Message<T> implements Serializable {

    private static final long serialVersionUID = -5756901646411393269L;
    //用戶id
    int id;

    //信息類型
    MsgType type;

    //返回结果
    int resultcode;
    //msg
    String msg;

    T responseMessage;//信息對象

    byte[] attachment;//文件

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public T getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(T responseMessage) {
        this.responseMessage = responseMessage;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public Message() {

    }

    public Message(MsgType type) {
        this.type = type;
    }
}