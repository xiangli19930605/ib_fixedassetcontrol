package com.idealbank.ib_secretassetcontrol.app;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.HistoryData;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
import me.jessyan.armscomponent.commonsdk.data.db.DbHelper;

public class DbManager implements DbHelper {

    public  long  insertTaskBean(TaskBean data) {
     return   MyApplication.mComponent
                .getAppDataManager(). insertTaskBean(data);
    }

    @Override
    public void clearTaskBean() {
        MyApplication.mComponent
                .getAppDataManager().clearTaskBean();
    }

    @Override
    public void delTaskBeanWhereId(Long id) {
        MyApplication.mComponent
                .getAppDataManager().delTaskBeanWhereId(id);
    }

    @Override
    public List<TaskBean> loadAllTaskBean() {
        return  MyApplication.mComponent
                .getAppDataManager().loadAllTaskBean();
    }

    @Override
    public void upDateTaskBeanWhereId(Long data, int state) {
        MyApplication.mComponent
                .getAppDataManager().upDateTaskBeanWhereId(data,state);
    }

    @Override
    public void upDateTaskBeanWhereId(Long data, String id, int checknum) {
        MyApplication.mComponent
                .getAppDataManager().upDateTaskBeanWhereId(data,id,checknum);
    }

    @Override
    public List<TaskBean> queryTaskBeanWhereState(int state) {
        return  MyApplication.mComponent
                .getAppDataManager().queryTaskBeanWhereState(state);
    }

    @Override
    public TaskBean queryTaskBeanWhereUid(Long data) {
        return  MyApplication.mComponent
                .getAppDataManager().queryTaskBeanWhereUid(data);
    }

    @Override
    public TaskBean queryTaskBeanWhereID(String id) {
        return MyApplication.mComponent
                .getAppDataManager().queryTaskBeanWhereID(id);
    }

    @Override
    public long insertAssetsBean(AssetsBean data) {
      return   MyApplication.mComponent
                .getAppDataManager().insertAssetsBean(data);
    }

    @Override
    public void insertInTxAssetsBean(List<AssetsBean> list) {
        MyApplication.mComponent
                .getAppDataManager().insertInTxAssetsBean(list);
    }

    @Override
    public void clearAssetsBean() {
        MyApplication.mComponent
                .getAppDataManager().clearAssetsBean();
    }

    @Override
    public List<AssetsBean> loadAllAssetsBean() {
        return  MyApplication.mComponent
                .getAppDataManager().loadAllAssetsBean();
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereId(String data) {
        return  MyApplication.mComponent
                .getAppDataManager().queryAssetsBeanWhereId(data);
    }

    @Override
    public AssetsBean queryAssetsBeanWhereAssetsAndCheckId(String checkId, String Assets) {
        return     MyApplication.mComponent
                .getAppDataManager().queryAssetsBeanWhereAssetsAndCheckId(checkId,Assets);
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereAssets(String data) {
        return  MyApplication.mComponent
                .getAppDataManager().queryAssetsBeanWhereAssets(data);
    }


    @Override
    public void upDateAssetsBeanRemarkWhereId(Long id, String remark) {
        MyApplication.mComponent
                .getAppDataManager().upDateAssetsBeanRemarkWhereId(id,remark);
    }

    @Override
    public void upDateAssetsBeanStateWhereId(String data, int state) {
          MyApplication.mComponent
                .getAppDataManager().upDateAssetsBeanStateWhereId(data,state);
    }

    @Override
    public void upDateAssetsBeanStateWhereId(Long id, int state) {
        MyApplication.mComponent
                .getAppDataManager().upDateAssetsBeanStateWhereId(id,state);
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereIdandState(String data, int state) {
        return  MyApplication.mComponent
                .getAppDataManager().queryAssetsBeanWhereIdandState(data,state);
    }

    @Override
    public AssetsBean queryAssetsBeanWhereRfid(String frid) {
        return  MyApplication.mComponent
                .getAppDataManager().queryAssetsBeanWhereRfid(frid);
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereAll(String checkid,String data) {
        return  MyApplication.mComponent
                .getAppDataManager().queryAssetsBeanWhereAll(checkid,data);
    }

    @Override
    public void delAssetsBeanWhereId(String taskid) {
        MyApplication.mComponent
                .getAppDataManager().delAssetsBeanWhereId(taskid);
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        return  MyApplication.mComponent
                .getAppDataManager().addHistoryData(data);
    }

    @Override
    public void clearHistoryData() {
        MyApplication.mComponent
                .getAppDataManager().clearHistoryData();
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return  MyApplication.mComponent
                .getAppDataManager().loadAllHistoryData();
    }


}
