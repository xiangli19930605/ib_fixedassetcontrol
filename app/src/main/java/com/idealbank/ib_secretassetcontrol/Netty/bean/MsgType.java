package com.idealbank.ib_secretassetcontrol.Netty.bean;

/**
 * Created by zw on 2018/9/11.
 */
public enum MsgType {
    /**
     * 心跳信息
     */
    PING,
    /**
     * RFID
     */
    RFID,

    DOWNLOAD,//下载盘点任务

    DOWNLOAD_SUCCESS,//下载盘点任务
    DOWNLOAD_FAILURE,//下载盘点任务

    UPLOADDATA,//上传盘点任务
    /** v
     * PIC
     */
    PIC,
    /**
     * FORM
     */
    FORM,

    EXCL,


//搜索RFID
    INFO_SEARCH_RFID,
    INFO_SEARCH_RFID2,
    INFO_SEARCH_RFID_LIST



}
