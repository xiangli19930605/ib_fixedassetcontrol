package com.idealbank.ib_secretassetcontrol.Netty.bean;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> implements Serializable {

    /**
     * data : {"id":"1141308413323509760"}
     * type : UPLOADDATA
     */
    T data;//信息對象
    //信息類型
    MsgType type;

}

