package me.jessyan.armscomponent.commonsdk.bean;

import lombok.Getter;
import lombok.Setter;


/**
 * Describe：EventBus事件类
 * Created by 吴天强 on 2018/10/22.
 */

@Getter
@Setter
public class Event<T> {

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private T data;

//
    public Event(String action) {
        this.action = action;
    }

    public Event(String action, T data) {
        this.action = action;
        this.data = data;
    }
    public Event( T data) {
        this.data = data;
    }


}
