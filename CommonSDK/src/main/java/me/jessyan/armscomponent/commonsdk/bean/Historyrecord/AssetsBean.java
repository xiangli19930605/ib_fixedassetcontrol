package me.jessyan.armscomponent.commonsdk.bean.Historyrecord;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
//@Entity(nameInDb = db, indexes = {
//        @Index(value = 属性名 DESC,  属性名 DESC, unique = true)
//})
@Entity
public class AssetsBean {

    //ID主键自增
    @Id(autoincrement = true)
    private Long id;

//    @NotNull
    //设计此字段，然后在Card中引用它，以便于Card来识别它
//    private Long  rfid;

    private Long  taskbeanid;//任务ID  关联任务表的numid

    private String typeName;  //资产类型名称
    private int     assetUserState;  //资产使用状态   0在库1在用2毁坏不能用3其他
    private String  spec;  //资产型号
    private String  typeCode; //资产类型code
    private String   curName;  //责任人名称
    private String    assets;  //资产编号
    private String  belongDept; //所属部门id
    private int    checkState;  //状态 0初始状态1已盘点（正常）2丢失3其他
    private String   assetName;  //资产名称
    private String    cueUser;  //责任人id
    private String    location;  //资产位置
    private String     checkId;  //盘点任务id
    private String     rfidId;  //资产rfid编号
    private String      brand; //品牌
    private String      belongName; //所属部门名称
    private String      remark; //所属部门名称
    private Boolean      isExand=false; //是否展开

    @Generated(hash = 1218324724)
    public AssetsBean(Long id, Long taskbeanid, String typeName, int assetUserState,
            String spec, String typeCode, String curName, String assets,
            String belongDept, int checkState, String assetName, String cueUser,
            String location, String checkId, String rfidId, String brand,
            String belongName, String remark, Boolean isExand) {
        this.id = id;
        this.taskbeanid = taskbeanid;
        this.typeName = typeName;
        this.assetUserState = assetUserState;
        this.spec = spec;
        this.typeCode = typeCode;
        this.curName = curName;
        this.assets = assets;
        this.belongDept = belongDept;
        this.checkState = checkState;
        this.assetName = assetName;
        this.cueUser = cueUser;
        this.location = location;
        this.checkId = checkId;
        this.rfidId = rfidId;
        this.brand = brand;
        this.belongName = belongName;
        this.remark = remark;
        this.isExand = isExand;
    }
    @Generated(hash = 901117572)
    public AssetsBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTypeName() {
        return this.typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public int getAssetUserState() {
        return this.assetUserState;
    }
    public void setAssetUserState(int assetUserState) {
        this.assetUserState = assetUserState;
    }
    public String getSpec() {
        return this.spec;
    }
    public void setSpec(String spec) {
        this.spec = spec;
    }
    public String getTypeCode() {
        return this.typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    public String getCurName() {
        return this.curName;
    }
    public void setCurName(String curName) {
        this.curName = curName;
    }
    public String getAssets() {
        return this.assets;
    }
    public void setAssets(String assets) {
        this.assets = assets;
    }
    public String getBelongDept() {
        return this.belongDept;
    }
    public void setBelongDept(String belongDept) {
        this.belongDept = belongDept;
    }
    public int getCheckState() {
        return this.checkState;
    }
    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }
    public String getAssetName() {
        return this.assetName;
    }
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
    public String getCueUser() {
        return this.cueUser;
    }
    public void setCueUser(String cueUser) {
        this.cueUser = cueUser;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCheckId() {
        return this.checkId;
    }
    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }
    public String getRfidId() {
        return this.rfidId;
    }
    public void setRfidId(String rfidId) {
        this.rfidId = rfidId;
    }
    public String getBrand() {
        return this.brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getBelongName() {
        return this.belongName;
    }
    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Boolean getIsExand() {
        return this.isExand;
    }
    public void setIsExand(Boolean isExand) {
        this.isExand = isExand;
    }
    public Long getTaskbeanid() {
        return this.taskbeanid;
    }
    public void setTaskbeanid(Long taskbeanid) {
        this.taskbeanid = taskbeanid;
    }



  


}
