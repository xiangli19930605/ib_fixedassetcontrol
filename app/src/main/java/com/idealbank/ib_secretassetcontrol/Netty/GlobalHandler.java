package com.idealbank.ib_secretassetcontrol.Netty;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GlobalHandler extends Handler {

    private HandleMsgListener listener;
    private String Tag = GlobalHandler.class.getSimpleName();

    //使用单例模式创建GlobalHandler
    private GlobalHandler() {
        Log.e(Tag, "GlobalHandler创建");
    }

    private static class Holder {
        private static final GlobalHandler HANDLER = new GlobalHandler();
    }

    public static GlobalHandler getInstance() {
        return Holder.HANDLER;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (getHandleMsgListener() !=null) {
            getHandleMsgListener().handleMsg(msg);
        } else {
            Log.e(Tag, "请传入HandleMsgListener对象");
        }
    }

    public interface HandleMsgListener {
        void handleMsg(Message msg);
    }

    public void setHandleMsgListener(HandleMsgListener listener) {
        this.listener = listener;
    }

    public HandleMsgListener getHandleMsgListener() {
        return listener;
    }

}