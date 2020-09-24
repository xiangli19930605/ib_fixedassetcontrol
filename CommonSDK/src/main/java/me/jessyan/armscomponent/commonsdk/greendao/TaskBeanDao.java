package me.jessyan.armscomponent.commonsdk.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TASK_BEAN".
*/
public class TaskBeanDao extends AbstractDao<TaskBean, Long> {

    public static final String TABLENAME = "TASK_BEAN";

    /**
     * Properties of entity TaskBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Numid = new Property(0, Long.class, "numid", true, "_id");
        public final static Property CheckType = new Property(1, int.class, "checkType", false, "CHECK_TYPE");
        public final static Property UploadBy = new Property(2, String.class, "uploadBy", false, "UPLOAD_BY");
        public final static Property UpdateTime = new Property(3, String.class, "updateTime", false, "UPDATE_TIME");
        public final static Property DownloadName = new Property(4, String.class, "downloadName", false, "DOWNLOAD_NAME");
        public final static Property CheckName = new Property(5, String.class, "checkName", false, "CHECK_NAME");
        public final static Property UploadName = new Property(6, String.class, "uploadName", false, "UPLOAD_NAME");
        public final static Property UploadTime = new Property(7, String.class, "uploadTime", false, "UPLOAD_TIME");
        public final static Property UpdateName = new Property(8, String.class, "updateName", false, "UPDATE_NAME");
        public final static Property DownloadTime = new Property(9, String.class, "downloadTime", false, "DOWNLOAD_TIME");
        public final static Property GotNum = new Property(10, int.class, "gotNum", false, "GOT_NUM");
        public final static Property Loss = new Property(11, int.class, "loss", false, "LOSS");
        public final static Property CreateBy = new Property(12, String.class, "createBy", false, "CREATE_BY");
        public final static Property Total = new Property(13, int.class, "total", false, "TOTAL");
        public final static Property BelongDept = new Property(14, String.class, "belongDept", false, "BELONG_DEPT");
        public final static Property CurUser = new Property(15, String.class, "curUser", false, "CUR_USER");
        public final static Property DownloadBy = new Property(16, String.class, "downloadBy", false, "DOWNLOAD_BY");
        public final static Property CreateTime = new Property(17, String.class, "createTime", false, "CREATE_TIME");
        public final static Property UpdateBy = new Property(18, String.class, "updateBy", false, "UPDATE_BY");
        public final static Property BelongDeptName = new Property(19, String.class, "belongDeptName", false, "BELONG_DEPT_NAME");
        public final static Property Id = new Property(20, String.class, "id", false, "ID");
        public final static Property State = new Property(21, int.class, "state", false, "STATE");
        public final static Property Per = new Property(22, String.class, "per", false, "PER");
        public final static Property CreateName = new Property(23, String.class, "createName", false, "CREATE_NAME");
        public final static Property CheckNum = new Property(24, int.class, "checkNum", false, "CHECK_NUM");
    }


    public TaskBeanDao(DaoConfig config) {
        super(config);
    }
    
    public TaskBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TASK_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: numid
                "\"CHECK_TYPE\" INTEGER NOT NULL ," + // 1: checkType
                "\"UPLOAD_BY\" TEXT," + // 2: uploadBy
                "\"UPDATE_TIME\" TEXT," + // 3: updateTime
                "\"DOWNLOAD_NAME\" TEXT," + // 4: downloadName
                "\"CHECK_NAME\" TEXT," + // 5: checkName
                "\"UPLOAD_NAME\" TEXT," + // 6: uploadName
                "\"UPLOAD_TIME\" TEXT," + // 7: uploadTime
                "\"UPDATE_NAME\" TEXT," + // 8: updateName
                "\"DOWNLOAD_TIME\" TEXT," + // 9: downloadTime
                "\"GOT_NUM\" INTEGER NOT NULL ," + // 10: gotNum
                "\"LOSS\" INTEGER NOT NULL ," + // 11: loss
                "\"CREATE_BY\" TEXT," + // 12: createBy
                "\"TOTAL\" INTEGER NOT NULL ," + // 13: total
                "\"BELONG_DEPT\" TEXT," + // 14: belongDept
                "\"CUR_USER\" TEXT," + // 15: curUser
                "\"DOWNLOAD_BY\" TEXT," + // 16: downloadBy
                "\"CREATE_TIME\" TEXT," + // 17: createTime
                "\"UPDATE_BY\" TEXT," + // 18: updateBy
                "\"BELONG_DEPT_NAME\" TEXT," + // 19: belongDeptName
                "\"ID\" TEXT," + // 20: id
                "\"STATE\" INTEGER NOT NULL ," + // 21: state
                "\"PER\" TEXT," + // 22: per
                "\"CREATE_NAME\" TEXT," + // 23: createName
                "\"CHECK_NUM\" INTEGER NOT NULL );"); // 24: checkNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TASK_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TaskBean entity) {
        stmt.clearBindings();
 
        Long numid = entity.getNumid();
        if (numid != null) {
            stmt.bindLong(1, numid);
        }
        stmt.bindLong(2, entity.getCheckType());
 
        String uploadBy = entity.getUploadBy();
        if (uploadBy != null) {
            stmt.bindString(3, uploadBy);
        }
 
        String updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindString(4, updateTime);
        }
 
        String downloadName = entity.getDownloadName();
        if (downloadName != null) {
            stmt.bindString(5, downloadName);
        }
 
        String checkName = entity.getCheckName();
        if (checkName != null) {
            stmt.bindString(6, checkName);
        }
 
        String uploadName = entity.getUploadName();
        if (uploadName != null) {
            stmt.bindString(7, uploadName);
        }
 
        String uploadTime = entity.getUploadTime();
        if (uploadTime != null) {
            stmt.bindString(8, uploadTime);
        }
 
        String updateName = entity.getUpdateName();
        if (updateName != null) {
            stmt.bindString(9, updateName);
        }
 
        String downloadTime = entity.getDownloadTime();
        if (downloadTime != null) {
            stmt.bindString(10, downloadTime);
        }
        stmt.bindLong(11, entity.getGotNum());
        stmt.bindLong(12, entity.getLoss());
 
        String createBy = entity.getCreateBy();
        if (createBy != null) {
            stmt.bindString(13, createBy);
        }
        stmt.bindLong(14, entity.getTotal());
 
        String belongDept = entity.getBelongDept();
        if (belongDept != null) {
            stmt.bindString(15, belongDept);
        }
 
        String curUser = entity.getCurUser();
        if (curUser != null) {
            stmt.bindString(16, curUser);
        }
 
        String downloadBy = entity.getDownloadBy();
        if (downloadBy != null) {
            stmt.bindString(17, downloadBy);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(18, createTime);
        }
 
        String updateBy = entity.getUpdateBy();
        if (updateBy != null) {
            stmt.bindString(19, updateBy);
        }
 
        String belongDeptName = entity.getBelongDeptName();
        if (belongDeptName != null) {
            stmt.bindString(20, belongDeptName);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(21, id);
        }
        stmt.bindLong(22, entity.getState());
 
        String per = entity.getPer();
        if (per != null) {
            stmt.bindString(23, per);
        }
 
        String createName = entity.getCreateName();
        if (createName != null) {
            stmt.bindString(24, createName);
        }
        stmt.bindLong(25, entity.getCheckNum());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TaskBean entity) {
        stmt.clearBindings();
 
        Long numid = entity.getNumid();
        if (numid != null) {
            stmt.bindLong(1, numid);
        }
        stmt.bindLong(2, entity.getCheckType());
 
        String uploadBy = entity.getUploadBy();
        if (uploadBy != null) {
            stmt.bindString(3, uploadBy);
        }
 
        String updateTime = entity.getUpdateTime();
        if (updateTime != null) {
            stmt.bindString(4, updateTime);
        }
 
        String downloadName = entity.getDownloadName();
        if (downloadName != null) {
            stmt.bindString(5, downloadName);
        }
 
        String checkName = entity.getCheckName();
        if (checkName != null) {
            stmt.bindString(6, checkName);
        }
 
        String uploadName = entity.getUploadName();
        if (uploadName != null) {
            stmt.bindString(7, uploadName);
        }
 
        String uploadTime = entity.getUploadTime();
        if (uploadTime != null) {
            stmt.bindString(8, uploadTime);
        }
 
        String updateName = entity.getUpdateName();
        if (updateName != null) {
            stmt.bindString(9, updateName);
        }
 
        String downloadTime = entity.getDownloadTime();
        if (downloadTime != null) {
            stmt.bindString(10, downloadTime);
        }
        stmt.bindLong(11, entity.getGotNum());
        stmt.bindLong(12, entity.getLoss());
 
        String createBy = entity.getCreateBy();
        if (createBy != null) {
            stmt.bindString(13, createBy);
        }
        stmt.bindLong(14, entity.getTotal());
 
        String belongDept = entity.getBelongDept();
        if (belongDept != null) {
            stmt.bindString(15, belongDept);
        }
 
        String curUser = entity.getCurUser();
        if (curUser != null) {
            stmt.bindString(16, curUser);
        }
 
        String downloadBy = entity.getDownloadBy();
        if (downloadBy != null) {
            stmt.bindString(17, downloadBy);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(18, createTime);
        }
 
        String updateBy = entity.getUpdateBy();
        if (updateBy != null) {
            stmt.bindString(19, updateBy);
        }
 
        String belongDeptName = entity.getBelongDeptName();
        if (belongDeptName != null) {
            stmt.bindString(20, belongDeptName);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(21, id);
        }
        stmt.bindLong(22, entity.getState());
 
        String per = entity.getPer();
        if (per != null) {
            stmt.bindString(23, per);
        }
 
        String createName = entity.getCreateName();
        if (createName != null) {
            stmt.bindString(24, createName);
        }
        stmt.bindLong(25, entity.getCheckNum());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TaskBean readEntity(Cursor cursor, int offset) {
        TaskBean entity = new TaskBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // numid
            cursor.getInt(offset + 1), // checkType
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // uploadBy
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // updateTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // downloadName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // checkName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // uploadName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // uploadTime
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // updateName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // downloadTime
            cursor.getInt(offset + 10), // gotNum
            cursor.getInt(offset + 11), // loss
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // createBy
            cursor.getInt(offset + 13), // total
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // belongDept
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // curUser
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // downloadBy
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // createTime
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // updateBy
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // belongDeptName
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // id
            cursor.getInt(offset + 21), // state
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // per
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // createName
            cursor.getInt(offset + 24) // checkNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TaskBean entity, int offset) {
        entity.setNumid(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCheckType(cursor.getInt(offset + 1));
        entity.setUploadBy(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUpdateTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDownloadName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCheckName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUploadName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUploadTime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUpdateName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDownloadTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setGotNum(cursor.getInt(offset + 10));
        entity.setLoss(cursor.getInt(offset + 11));
        entity.setCreateBy(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setTotal(cursor.getInt(offset + 13));
        entity.setBelongDept(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCurUser(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setDownloadBy(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setCreateTime(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setUpdateBy(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setBelongDeptName(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setId(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setState(cursor.getInt(offset + 21));
        entity.setPer(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setCreateName(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setCheckNum(cursor.getInt(offset + 24));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TaskBean entity, long rowId) {
        entity.setNumid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TaskBean entity) {
        if(entity != null) {
            return entity.getNumid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TaskBean entity) {
        return entity.getNumid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}