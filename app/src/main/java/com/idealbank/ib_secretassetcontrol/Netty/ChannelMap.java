package com.idealbank.ib_secretassetcontrol.Netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.netty.channel.Channel;

public class ChannelMap {
    private static ConcurrentMap map ;

    public static void addChannel(String name, Channel channel){
        if(map == null){
            map = new ConcurrentHashMap();
        }
        map.put(name,channel);
    }

    public static Channel getChannel(String name){
        if(map == null || map.isEmpty()){
            return null;
        }
        return (Channel) map.get(name);


    }
    public static Channel delChannel(String name){
        if(map == null || map.isEmpty()){
            return null;
        }
        return (Channel) map.remove(name);


    }
}
