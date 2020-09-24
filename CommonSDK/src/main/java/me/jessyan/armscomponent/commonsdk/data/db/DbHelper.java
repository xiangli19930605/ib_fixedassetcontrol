package me.jessyan.armscomponent.commonsdk.data.db;

import java.util.List;

import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.HistoryData;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;

/**
 * @author quchao
 * @date 2017/11/27
 */

public interface DbHelper {

    // 增加盘查任务数据
    long insertTaskBean(TaskBean data);

    void clearTaskBean();

    //删除指定ID的任务
    void delTaskBeanWhereId(Long id);

    List<TaskBean> loadAllTaskBean();

    //更新任务状态
    void upDateTaskBeanWhereId(Long data, int state);

    //更新任务的已盘数量
    void upDateTaskBeanWhereId(Long data, String id, int checknum);

    //根据状态查询任务
    List<TaskBean> queryTaskBeanWhereState(int state);

    //根据numid和id查询任务
    TaskBean queryTaskBeanWhereUid(Long data);

    //根据  任务id返回唯一taskben
    TaskBean queryTaskBeanWhereID(String id);


    //增加单个资产
    long insertAssetsBean(AssetsBean data);

    //增加多个资产
    void insertInTxAssetsBean(List<AssetsBean> list);

    void clearAssetsBean();

    List<AssetsBean> loadAllAssetsBean();

    //根据任务编号CheckId查询
    List<AssetsBean> queryAssetsBeanWhereId(String data);

    //根据资产编号Assets  和任务checkId查询
    AssetsBean queryAssetsBeanWhereAssetsAndCheckId(String checkId, String Assets);

    //根据资产编号 Assets查询
    List<AssetsBean> queryAssetsBeanWhereAssets(String Assets);

    //更新资产盘点状态
    void upDateAssetsBeanStateWhereId(String data, int state);

    //更新资产盘点状态 通过id
    void upDateAssetsBeanStateWhereId(Long id, int state);

    //更新资产备注
    void upDateAssetsBeanRemarkWhereId(Long id, String remark);

    //根据状态查询资产
    List<AssetsBean> queryAssetsBeanWhereIdandState(String data, int state);

//    List<AssetsBean> queryAssetsBeanWhereTaskIdIdandState(Long data, int state);

    //模糊查询 根据rfid 查询是哪个资产
    AssetsBean queryAssetsBeanWhereRfid(String frid);

    //模糊查询 根据data 查询是哪个资产  多个字段
    List<AssetsBean> queryAssetsBeanWhereAll(String checkid, String data);


    //删除指定ID下的资产
    void delAssetsBeanWhereId(String taskid);


    // 增加搜索历史数据
    List<HistoryData> addHistoryData(String data);

    void clearHistoryData();

    List<HistoryData> loadAllHistoryData();


}
