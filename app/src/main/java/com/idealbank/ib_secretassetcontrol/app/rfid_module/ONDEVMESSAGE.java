package com.idealbank.ib_secretassetcontrol.app.rfid_module;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.bean.Event;
import me.jessyan.armscomponent.commonsdk.core.EventBusTags;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;
import me.jessyan.autosize.utils.LogUtils;

public class ONDEVMESSAGE implements rfid_interface.OnReadMessage {

    public  List<String> list = new ArrayList<String>();
    private JSONObject json_data;
    public String Read_data;

    @Override
    public void OnReadMessage(String szCode) {
        System.out.println(szCode + "size:" + list.size());
        try {
            json_data = new JSONObject(szCode);
            String action = json_data.getString("action");
            if (action.equals("inventory")) {
                String originTagid = json_data.getString("tagid");//初始的ID
                String tagid;
                String  type;//类型 fe:院内资产 ff:车辆标签 fc:院外资产
                int   tagType  ;//类型  0123
                int str;//实际数据长度
                try {
                    type=originTagid.substring(0, 2);
                    //过滤掉其他标签
                    if(type.equals("FE")){
                        tagType=0;
                    }else if(type.equals("FC")){
                        tagType=2;
                    }else{
                        return;
                    }
                    str = Integer.valueOf(originTagid.substring(2, 4));//截取前两个字符
                }catch (NumberFormatException e){
                    LogUtils.e(""+e);
                    return;
                }
                try {
                    tagid = originTagid.substring(4,  4+str);
                }catch (StringIndexOutOfBoundsException e){
                    LogUtils.e(""+e);
                    return;
                }

                if (list.size() == 0) {
                    list.add(tagid);
                    EventBusUtils.sendEvent(new Event(new RfidData(tagid,tagType) ), EventBusTags.SCANRFID);
                } else {
                    /**
                     * 判断是否存在list中是否已存在该tagid 存在：退出循环 不存在：添加tagid
                     */
                    if (list.contains(tagid)) {
                        LogUtils.e("相同" + tagid);
                    } else {
                        list.add(tagid);
                        EventBusUtils.sendEvent(new Event(new RfidData(tagid,tagType) ), EventBusTags.SCANRFID);
                    }
                }

            }
//            if (action.equals("inventory")) {
//                String originTagid = json_data.getString("tagid");//初始的ID
//                String tagid;
//                int str;
//                try {
//                    str = Integer.valueOf(originTagid.substring(0, 2));//截取前两个字符
//                }catch (NumberFormatException e){
//                    LogUtils.e(""+e);
//                    return;
//                }
//                try {
//                    tagid = originTagid.substring(2,  str+2);
//                }catch (StringIndexOutOfBoundsException e){
//                    LogUtils.e(""+e);
//                    return;
//                }
//                if (list.size() == 0) {
//                    list.add(tagid);
//                    EventBusUtils.sendEvent(new Event("", tagid), EventBusTags.SCANRFID);
//                } else {
//                    /**
//                     * 判断是否存在list中是否已存在该tagid 存在：退出循环 不存在：添加tagid
//                     */
//                    /**
//                     * 判断是否存在list中是否已存在该tagid 存在：退出循环 不存在：添加tagid
//                     */
//                    if (list.contains(tagid)) {
//                        LogUtils.e("相同" + tagid);
//                    } else {
//                        list.add(tagid);
//                        EventBusUtils.sendEvent(new Event("", tagid), EventBusTags.SCANRFID);
//                    }
//                }
//
//            }
            // 读取标签
            if (action.equals("readtag")) {
                // {"action":"readtag", "error":"0", "tagid":"A09000000000C509", "data":"0000000000000000"}
                //System.out.println(szCode);
                Read_data = json_data.getString("data");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
