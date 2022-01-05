package com.gap.bis_inspection.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.gap.bis_inspection.db.objectmodel.Form;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FORM".
*/
public class FormDao extends AbstractDao<Form, Long> {

    public static final String TABLENAME = "FORM";

    /**
     * Properties of entity Form.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property MinScore = new Property(2, Integer.class, "minScore", false, "MIN_SCORE");
        public final static Property MaxScore = new Property(3, Integer.class, "maxScore", false, "MAX_SCORE");
        public final static Property StartDate = new Property(4, java.util.Date.class, "startDate", false, "START_DATE");
        public final static Property EndDate = new Property(5, java.util.Date.class, "endDate", false, "END_DATE");
        public final static Property StatusEn = new Property(6, Integer.class, "statusEn", false, "STATUS_EN");
        public final static Property FormStatus = new Property(7, Integer.class, "formStatus", false, "FORM_STATUS");
        public final static Property StatusDate = new Property(8, java.util.Date.class, "statusDate", false, "STATUS_DATE");
        public final static Property SendingStatusEn = new Property(9, Integer.class, "sendingStatusEn", false, "SENDING_STATUS_EN");
        public final static Property SendingStatusDate = new Property(10, java.util.Date.class, "sendingStatusDate", false, "SENDING_STATUS_DATE");
        public final static Property XLatitude = new Property(11, String.class, "xLatitude", false, "X_LATITUDE");
        public final static Property YLongitude = new Property(12, String.class, "yLongitude", false, "Y_LONGITUDE");
        public final static Property ServerAnswerInfoId = new Property(13, Long.class, "serverAnswerInfoId", false, "SERVER_ANSWER_INFO_ID");
        public final static Property InputValuesDefault = new Property(14, String.class, "inputValuesDefault", false, "INPUT_VALUES_DEFAULT");
    };

    private DaoSession daoSession;


    public FormDao(DaoConfig config) {
        super(config);
    }
    
    public FormDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FORM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"MIN_SCORE\" INTEGER," + // 2: minScore
                "\"MAX_SCORE\" INTEGER," + // 3: maxScore
                "\"START_DATE\" INTEGER," + // 4: startDate
                "\"END_DATE\" INTEGER," + // 5: endDate
                "\"STATUS_EN\" INTEGER," + // 6: statusEn
                "\"FORM_STATUS\" INTEGER," + // 7: formStatus
                "\"STATUS_DATE\" INTEGER," + // 8: statusDate
                "\"SENDING_STATUS_EN\" INTEGER," + // 9: sendingStatusEn
                "\"SENDING_STATUS_DATE\" INTEGER," + // 10: sendingStatusDate
                "\"X_LATITUDE\" TEXT," + // 11: xLatitude
                "\"Y_LONGITUDE\" TEXT," + // 12: yLongitude
                "\"SERVER_ANSWER_INFO_ID\" INTEGER," + // 13: serverAnswerInfoId
                "\"INPUT_VALUES_DEFAULT\" TEXT);"); // 14: inputValuesDefault
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FORM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Form entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        Integer minScore = entity.getMinScore();
        if (minScore != null) {
            stmt.bindLong(3, minScore);
        }
 
        Integer maxScore = entity.getMaxScore();
        if (maxScore != null) {
            stmt.bindLong(4, maxScore);
        }
 
        java.util.Date startDate = entity.getStartDate();
        if (startDate != null) {
            stmt.bindLong(5, startDate.getTime());
        }
 
        java.util.Date endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindLong(6, endDate.getTime());
        }
 
        Integer statusEn = entity.getStatusEn();
        if (statusEn != null) {
            stmt.bindLong(7, statusEn);
        }
 
        Integer formStatus = entity.getFormStatus();
        if (formStatus != null) {
            stmt.bindLong(8, formStatus);
        }
 
        java.util.Date statusDate = entity.getStatusDate();
        if (statusDate != null) {
            stmt.bindLong(9, statusDate.getTime());
        }
 
        Integer sendingStatusEn = entity.getSendingStatusEn();
        if (sendingStatusEn != null) {
            stmt.bindLong(10, sendingStatusEn);
        }
 
        java.util.Date sendingStatusDate = entity.getSendingStatusDate();
        if (sendingStatusDate != null) {
            stmt.bindLong(11, sendingStatusDate.getTime());
        }
 
        String xLatitude = entity.getXLatitude();
        if (xLatitude != null) {
            stmt.bindString(12, xLatitude);
        }
 
        String yLongitude = entity.getYLongitude();
        if (yLongitude != null) {
            stmt.bindString(13, yLongitude);
        }
 
        Long serverAnswerInfoId = entity.getServerAnswerInfoId();
        if (serverAnswerInfoId != null) {
            stmt.bindLong(14, serverAnswerInfoId);
        }
 
        String inputValuesDefault = entity.getInputValuesDefault();
        if (inputValuesDefault != null) {
            stmt.bindString(15, inputValuesDefault);
        }
    }

    @Override
    protected void attachEntity(Form entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Form readEntity(Cursor cursor, int offset) {
        Form entity = new Form( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // minScore
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // maxScore
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // startDate
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)), // endDate
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // statusEn
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // formStatus
            cursor.isNull(offset + 8) ? null : new java.util.Date(cursor.getLong(offset + 8)), // statusDate
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // sendingStatusEn
            cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)), // sendingStatusDate
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // xLatitude
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // yLongitude
            cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13), // serverAnswerInfoId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14) // inputValuesDefault
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Form entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMinScore(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setMaxScore(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setStartDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setEndDate(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
        entity.setStatusEn(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setFormStatus(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setStatusDate(cursor.isNull(offset + 8) ? null : new java.util.Date(cursor.getLong(offset + 8)));
        entity.setSendingStatusEn(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setSendingStatusDate(cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)));
        entity.setXLatitude(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setYLongitude(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setServerAnswerInfoId(cursor.isNull(offset + 13) ? null : cursor.getLong(offset + 13));
        entity.setInputValuesDefault(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Form entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Form entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
