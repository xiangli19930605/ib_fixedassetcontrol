package me.jessyan.armscomponent.commonsdk.utils;

import org.simple.eventbus.EventBus;

import me.jessyan.armscomponent.commonsdk.bean.Event;

/**
 * Describe：EventBusUtils
 * Created by 吴天强 on 2018/10/22.
 */

public class EventBusUtils {

    /**
     * 注册事件
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 解除事件
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送普通事件
     */
    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }
    /**
     * 发送指定my_tag事件
     */
    public static void sendEvent(Event event,String my_tag) {
        EventBus.getDefault().post(event,my_tag);
    }
    /**
     * 发送Sticky事件
     */
    public static void postSticky(Event event) {
        EventBus.getDefault().postSticky(event);
    }
}
