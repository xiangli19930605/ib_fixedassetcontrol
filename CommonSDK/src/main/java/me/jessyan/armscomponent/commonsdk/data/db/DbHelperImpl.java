package me.jessyan.armscomponent.commonsdk.data.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.AssetsBean;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.HistoryData;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
import me.jessyan.armscomponent.commonsdk.greendao.AssetsBeanDao;
import me.jessyan.armscomponent.commonsdk.greendao.DaoMaster;
import me.jessyan.armscomponent.commonsdk.greendao.DaoSession;
import me.jessyan.armscomponent.commonsdk.greendao.HistoryDataDao;
import me.jessyan.armscomponent.commonsdk.greendao.TaskBeanDao;


/**
 * 对外隐藏操作数据库的实现细节
 *
 * @author quchao
 * @date 2017/11/27
 */
@Singleton
public class DbHelperImpl implements DbHelper {

    private static final int HISTORY_LIST_SIZE = 10;

    private DaoSession daoSession;
    private List<HistoryData> historyDataList;
    private String data;
    private HistoryData historyData;

    @Inject
    DbHelperImpl() {
        MyOpenHelper helper = new MyOpenHelper(MyApplication.getInstance(), "common_library.db", null);//建库
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    @Override
    public long insertTaskBean(TaskBean data) {
        return getTaskBeanDao().insert(data);
    }

    @Override
    public void clearTaskBean() {
        getTaskBeanDao().deleteAll();
    }

    @Override
    public void delTaskBeanWhereId(Long id) {
        TaskBean taskBean = getTaskBeanDao().queryBuilder().where(TaskBeanDao.Properties.Numid.eq(id)).unique();
        //删除操作
        getTaskBeanDao().delete(taskBean);
    }

    @Override
    public List<TaskBean> loadAllTaskBean() {
        return getTaskBeanDao().loadAll();
    }

    @Override
    public void upDateTaskBeanWhereId(Long data, int state) {
        TaskBean taskBean = getTaskBeanDao().queryBuilder().where(TaskBeanDao.Properties.Numid.eq(data)).unique();
        taskBean.setState(state);
        getTaskBeanDao().update(taskBean);
    }

    @Override
    public void upDateTaskBeanWhereId(Long data, String id, int checknum) {
        TaskBean taskBean = getTaskBeanDao().queryBuilder().where(TaskBeanDao.Properties.Numid.eq(data), TaskBeanDao.Properties.Id.eq(id)).unique();
        taskBean.setCheckNum(checknum);
        getTaskBeanDao().update(taskBean);
    }

    @Override
    public List<TaskBean> queryTaskBeanWhereState(int state) {
        return getTaskBeanDao().queryBuilder().where(TaskBeanDao.Properties.State.eq(state)).list();
    }

    @Override
    public TaskBean queryTaskBeanWhereUid(Long data) {
        return getTaskBeanDao().queryBuilder().where(TaskBeanDao.Properties.Numid.eq(data)).unique();
    }

    @Override
    public TaskBean queryTaskBeanWhereID(String id) {
        return getTaskBeanDao().queryBuilder().where(TaskBeanDao.Properties.Id.eq(id)).unique();
    }



    @Override
    public long insertAssetsBean(AssetsBean data) {
        return getAssetsBeanDao().insert(data);
    }

    @Override
    public void insertInTxAssetsBean(List<AssetsBean> list) {
        getAssetsBeanDao().insertInTx(list);
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereId(String data) {
        return getAssetsBeanDao().queryBuilder().where(AssetsBeanDao.Properties.CheckId.eq(data)).list();
    }

    @Override
    public AssetsBean queryAssetsBeanWhereAssetsAndCheckId(String checkId, String Assets) {
        return getAssetsBeanDao().queryBuilder().where(AssetsBeanDao.Properties.Assets.eq(Assets),AssetsBeanDao.Properties.CheckId.eq(checkId)).unique();
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereAssets(String Assets) {
        return getAssetsBeanDao().queryBuilder().where(AssetsBeanDao.Properties.Assets.eq(Assets)).list();
    }


    @Override
    public void upDateAssetsBeanRemarkWhereId(Long id, String remark) {
        AssetsBean assetsBean = getAssetsBeanDao().queryBuilder().where(AssetsBeanDao.Properties.Id.eq(id)).unique();
        //修改备注
        assetsBean.setRemark(remark);
        //修改
        getAssetsBeanDao().update(assetsBean);

    }

    @Override
    public void upDateAssetsBeanStateWhereId(String data, int state) {
        AssetsBean assetsBean = getAssetsBeanDao().queryBuilder().where(AssetsBeanDao.Properties.Assets.eq(data)).unique();
        //修改名字
        assetsBean.setCheckState(state);
        //修改
        getAssetsBeanDao().update(assetsBean);
    }

    @Override
    public void upDateAssetsBeanStateWhereId(Long id, int state) {
        AssetsBean assetsBean = getAssetsBeanDao().queryBuilder().where(AssetsBeanDao.Properties.Id.eq(id)).unique();
        //修改资产状态
        assetsBean.setCheckState(state);
        //修改
        getAssetsBeanDao().update(assetsBean);

    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereIdandState(String data, int state) {
        return getAssetsBeanDao().queryBuilder().where(AssetsBeanDao.Properties.CheckId.eq(data), AssetsBeanDao.Properties.CheckState.eq(state)).list();
    }

    @Override
    public AssetsBean queryAssetsBeanWhereRfid(String frid) {
        return getAssetsBeanDao().queryBuilder().where(AssetsBeanDao.Properties.RfidId.like(frid + "%")).unique();
    }

    @Override
    public List<AssetsBean> queryAssetsBeanWhereAll(String checkid,String data) {

        QueryBuilder qb = getAssetsBeanDao().queryBuilder();
//查询 资产为1230且  字符含有data的数据
        return qb.where(qb.and(AssetsBeanDao.Properties.CheckId.like(checkid),
                qb.or(
                        AssetsBeanDao.Properties.AssetName.like("%"+data + "%"),
                        AssetsBeanDao.Properties.TypeName.like("%" +data + "%"), AssetsBeanDao.Properties.Brand.like("%" +data + "%"),
                        AssetsBeanDao.Properties.Location.like("%" +data + "%"), AssetsBeanDao.Properties.BelongDept.like("%" +data + "%"),
                        AssetsBeanDao.Properties.CurName.like("%" +data + "%"), AssetsBeanDao.Properties.CheckState.like("%" +data + "%"),
                        AssetsBeanDao.Properties.Spec.like("%" +data + "%") , AssetsBeanDao.Properties.Assets.like("%" +data + "%")
                )
        )).list();

//        return getAssetsBeanDao().queryBuilder().where(AssetsBeanDao.Properties.CheckId.eq("123")).and().appendTo();


    }

    @Override
    public void delAssetsBeanWhereId(String taskid) {
        List<AssetsBean> list = queryAssetsBeanWhereId(taskid);
        getAssetsBeanDao().deleteInTx(list);
    }

    @Override
    public void clearAssetsBean() {
        daoSession.getAssetsBeanDao().deleteAll();
    }

    @Override
    public List<AssetsBean> loadAllAssetsBean() {
        return daoSession.getAssetsBeanDao().loadAll();
    }


    private AssetsBeanDao getAssetsBeanDao() {
        return daoSession.getAssetsBeanDao();
    }

    private TaskBeanDao getTaskBeanDao() {
        return daoSession.getTaskBeanDao();
    }


    @Override
    public List<HistoryData> addHistoryData(String data) {
        this.data = data;
        getHistoryDataList();
        createHistoryData();
        if (historyDataForward()) {
            return historyDataList;
        }

        if (historyDataList.size() < HISTORY_LIST_SIZE) {
            getHistoryDataDao().insert(historyData);
        } else {
            historyDataList.remove(0);
            historyDataList.add(historyData);
            getHistoryDataDao().deleteAll();
            getHistoryDataDao().insertInTx(historyDataList);
        }
        return historyDataList;
    }

    @Override
    public void clearHistoryData() {
        daoSession.getHistoryDataDao().deleteAll();
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return daoSession.getHistoryDataDao().loadAll();
    }

    /**
     * 历史数据前移
     *
     * @return 返回true表示查询的数据已存在，只需将其前移到第一项历史记录，否则需要增加新的历史记录
     */
    private boolean historyDataForward() {
        //重复搜索时进行历史记录前移
        Iterator<HistoryData> iterator = historyDataList.iterator();
        //不要在foreach循环中进行元素的remove、add操作，使用Iterator模式
        while (iterator.hasNext()) {
            HistoryData historyData1 = iterator.next();
            if (historyData1.getData().equals(data)) {
                historyDataList.remove(historyData1);
                historyDataList.add(historyData);
                getHistoryDataDao().deleteAll();
                getHistoryDataDao().insertInTx(historyDataList);
                return true;
            }
        }
        return false;
    }

    private void getHistoryDataList() {
        historyDataList = getHistoryDataDao().loadAll();
    }

    private void createHistoryData() {
        historyData = new HistoryData();
        historyData.setDate(System.currentTimeMillis());
        historyData.setData(data);
    }

    private HistoryDataDao getHistoryDataDao() {
        return daoSession.getHistoryDataDao();
    }


}
