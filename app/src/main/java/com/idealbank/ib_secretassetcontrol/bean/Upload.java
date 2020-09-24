package com.idealbank.ib_secretassetcontrol.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
@Getter
@Setter
public class Upload {


    /**
     * checkAssets : [{}]
     * check : {}
     */

    private TaskBean check;
    private List<AssetsBean> checkAssets;


}
