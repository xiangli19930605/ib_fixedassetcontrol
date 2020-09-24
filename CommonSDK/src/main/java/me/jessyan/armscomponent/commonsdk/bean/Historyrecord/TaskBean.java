package me.jessyan.armscomponent.commonsdk.bean.Historyrecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * @author quchao
 * @date 2018/3/5
 */
//@Entity(nameInDb = db, indexes = {
//        @Index(value = id DESC, unique = true)
//})
@Entity
public class TaskBean  extends  SelectIlem implements Serializable {
    private static final long serialVersionUID = 5298086812218887585L;
    //ID主键自增
    @Id(autoincrement = true)
    private Long numid;

    //cardId为Photo类中定义的一个属性
//    @ToMany(referencedJoinProperty = rfidId)
//    private List<AssetsBean> assetsList;

    private int  checkType ; //盘点类型0：按个人1：按部门
    private String   uploadBy ; //上传人id
    private String   updateTime;  //更新时间
    private String   downloadName; //下载人名称
    private String   checkName; //盘点任务名称
    private String    uploadName; //上传人名称
    private String    uploadTime;  //上传时间
    private String   updateName;  //更新时间
    private String    downloadTime;  //下载时间
    private int    gotNum; //正常资产数量
    private int    loss;  //丢失资产数量
    private String     createBy;  //创建人id
    private int    total;  //资产总数
    private String     belongDept; //盘点部门id
    private String     curUser; //盘点责任人
    private String      downloadBy;  //下载人id
    private String      createTime;   //创建时间
    private String     updateBy; //更新人id
    private String     belongDeptName; //盘点部门名称
    private String      id;  //盘点任务id
    private int     state; //状态 0：待盘点 1：待上传2：已上传3：已盘点(转历史记录)
    private String       per; //已盘资产/资产总数
    private String       createName; //创建人名称
    private int       checkNum; //已盘点数量
    @Generated(hash = 517731416)
    public TaskBean(Long numid, int checkType, String uploadBy, String updateTime,
            String downloadName, String checkName, String uploadName,
            String uploadTime, String updateName, String downloadTime, int gotNum,
            int loss, String createBy, int total, String belongDept, String curUser,
            String downloadBy, String createTime, String updateBy,
            String belongDeptName, String id, int state, String per,
            String createName, int checkNum) {
        this.numid = numid;
        this.checkType = checkType;
        this.uploadBy = uploadBy;
        this.updateTime = updateTime;
        this.downloadName = downloadName;
        this.checkName = checkName;
        this.uploadName = uploadName;
        this.uploadTime = uploadTime;
        this.updateName = updateName;
        this.downloadTime = downloadTime;
        this.gotNum = gotNum;
        this.loss = loss;
        this.createBy = createBy;
        this.total = total;
        this.belongDept = belongDept;
        this.curUser = curUser;
        this.downloadBy = downloadBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.belongDeptName = belongDeptName;
        this.id = id;
        this.state = state;
        this.per = per;
        this.createName = createName;
        this.checkNum = checkNum;
    }
    @Generated(hash = 1443476586)
    public TaskBean() {
    }
    public Long getNumid() {
        return this.numid;
    }
    public void setNumid(Long numid) {
        this.numid = numid;
    }
    public int getCheckType() {
        return this.checkType;
    }
    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }
    public String getUploadBy() {
        return this.uploadBy;
    }
    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }
    public String getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getDownloadName() {
        return this.downloadName;
    }
    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }
    public String getCheckName() {
        return this.checkName;
    }
    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }
    public String getUploadName() {
        return this.uploadName;
    }
    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }
    public String getUploadTime() {
        return this.uploadTime;
    }
    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
    public String getUpdateName() {
        return this.updateName;
    }
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
    public String getDownloadTime() {
        return this.downloadTime;
    }
    public void setDownloadTime(String downloadTime) {
        this.downloadTime = downloadTime;
    }
    public int getGotNum() {
        return this.gotNum;
    }
    public void setGotNum(int gotNum) {
        this.gotNum = gotNum;
    }
    public int getLoss() {
        return this.loss;
    }
    public void setLoss(int loss) {
        this.loss = loss;
    }
    public String getCreateBy() {
        return this.createBy;
    }
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public int getTotal() {
        return this.total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public String getBelongDept() {
        return this.belongDept;
    }
    public void setBelongDept(String belongDept) {
        this.belongDept = belongDept;
    }
    public String getCurUser() {
        return this.curUser;
    }
    public void setCurUser(String curUser) {
        this.curUser = curUser;
    }
    public String getDownloadBy() {
        return this.downloadBy;
    }
    public void setDownloadBy(String downloadBy) {
        this.downloadBy = downloadBy;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateBy() {
        return this.updateBy;
    }
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public String getBelongDeptName() {
        return this.belongDeptName;
    }
    public void setBelongDeptName(String belongDeptName) {
        this.belongDeptName = belongDeptName;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getPer() {
        return this.per;
    }
    public void setPer(String per) {
        this.per = per;
    }
    public String getCreateName() {
        return this.createName;
    }
    public void setCreateName(String createName) {
        this.createName = createName;
    }
    public int getCheckNum() {
        return this.checkNum;
    }
    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }
    




    
}
