package me.jessyan.armscomponent.commonsdk.data;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.HistoryData;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
import me.jessyan.armscomponent.commonsdk.data.db.DataManager;
import me.jessyan.armscomponent.commonsdk.data.db.DbHelper;
import me.jessyan.armscomponent.commonsdk.data.http.HttpHelper;
import me.jessyan.armscomponent.commonsdk.data.prefs.PreferenceHelper;

/**
 * @author flymegoc
 * @date 2017/11/22
 * @describe
 */

@Singleton
public class AppDataManager implements DataManager {

    private DbHelper mDbHelper;
    private HttpHelper mHttpHelper;
    private PreferenceHelper mPreferencesHelper;

    @Inject
    public AppDataManager(DbHelper mDbHelper, PreferenceHelper mPreferencesHelper, HttpHelper mHttpHelper) {
        this.mDbHelper = mDbHelper;
        this.mHttpHelper = mHttpHelper;
        this.mPreferencesHelper = mPreferencesHelper;
    }

    public PreferenceHelper getSharedPreferences(){
        return mPreferencesHelper;
    }


    @Override
    public boolean getNightModeState() {
        return  mPreferencesHelper.getNightModeState();
    }

    @Override
    public void putBoolean(String key, boolean defValue) {
        mPreferencesHelper.putBoolean(key,defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return   mPreferencesHelper.getBoolean(key,defValue);
    }

    @Override
    public void putString(String key, String defValue) {
        mPreferencesHelper.putString(key,defValue);
    }

    @Override
    public String getString(String key) {
        return mPreferencesHelper.getString(key);
    }

    @Override
    public void setNightModeState(boolean b) {
        mPreferencesHelper .setNightModeState(b);
    }

    @Override
    public Observable<String> testPigAvAddress(String url) {
        return null;
    }

    @Override
    public long  insertTaskBean(TaskBean data) {
        return mDbHelper. insertTaskBean(data);
    }

    @Override
    public void clearTaskBean() {
        mDbHelper.clearTaskBean();
    }

    @Override
    public void delTaskBeanWhereId(Long id) {
        mDbHelper.delTaskBeanWhereId(id);
    }

    @Override
    public List<TaskBean> loadAllTaskBean() {
        return mDbHelper.loadAllTaskBean();
    }

    @Override
    public void upDateTaskBeanWhereId(Long data, int state) {
         mDbHelper.upDateTaskBeanWhereId(data,state);
    }

    @Override
    public void upDateTaskBeanWhereId(Long data, String id, int checknum) {

        mDbHelper.upDateTaskBeanWhereId(data,id,checknum);
    }

    @Override
    public List<TaskBean> queryTaskBeanWhereState(int state) {
        return mDbHelper.queryTaskBeanWhereState(state);
    }

    @Override
    public TaskBean queryTaskBeanWhereUid(Long data) {
        return mDbHelper.queryTaskBeanWhereUid(data);
    }

    @Override
    public TaskBean queryTaskBeanWhereID(String id) {
        return mDbHelper.queryTaskBeanWhereID(id);
    }



    @Override
    public long insertAssetsBean(AssetsBean data) {
       return   mDbHelper.insertAssetsBean(data);
    }

    @Override
    public void insertInTxAssetsBean(List<AssetsBean> list) {
          mDbHelper.insertInTxAssetsBean(list);
    }

    @Override
    public void clearAssetsBean() {
        mDbHelper.clearAssetsBean();
    }

    @Override
    public List<AssetsBean> loadAllAssetsBean() {
        return mDbHelper.loadAllAssetsBean();
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereId(String data) {
        return mDbHelper.queryAssetsBeanWhereId(data);
    }

    @Override
    public AssetsBean queryAssetsBeanWhereAssetsAndCheckId(String checkId, String Assets) {
        return mDbHelper.queryAssetsBeanWhereAssetsAndCheckId(checkId,Assets);
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereAssets(String data) {
        return mDbHelper.queryAssetsBeanWhereAssets(data);
    }


    @Override
    public void upDateAssetsBeanRemarkWhereId(Long id, String remark) {
        mDbHelper.upDateAssetsBeanRemarkWhereId(id,remark);
    }

    @Override
    public void upDateAssetsBeanStateWhereId(String data, int state) {
        mDbHelper.upDateAssetsBeanStateWhereId(data,state);
    }

    @Override
    public void upDateAssetsBeanStateWhereId(Long id, int state) {
        mDbHelper.upDateAssetsBeanStateWhereId(id,state);
    }


    @Override
    public List<AssetsBean> queryAssetsBeanWhereIdandState(String data, int state) {
        return mDbHelper.queryAssetsBeanWhereIdandState(data,state);
    }

    @Override
    public AssetsBean queryAssetsBeanWhereRfid(String frid) {
        return mDbHelper.queryAssetsBeanWhereRfid(frid);
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereAll(String checkid,String data) {
        return mDbHelper.queryAssetsBeanWhereAll(checkid,data);
    }

    @Override
    public void delAssetsBeanWhereId(String taskid) {
        mDbHelper.delAssetsBeanWhereId(taskid);
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        return mDbHelper.addHistoryData(data);
    }

    @Override
    public void clearHistoryData() {
        mDbHelper.clearHistoryData();
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return mDbHelper.loadAllHistoryData();
    }
}
