package com.gap.chat_app.db.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.gap.chat_app.db.objectmodel.SurveyFormQuestion;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SURVEY_FORM_QUESTION".
*/
public class SurveyFormQuestionDao extends AbstractDao<SurveyFormQuestion, Long> {

    public static final String TABLENAME = "SURVEY_FORM_QUESTION";

    /**
     * Properties of entity SurveyFormQuestion.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Question = new Property(1, String.class, "question", false, "QUESTION");
        public final static Property AnswerTypeEn = new Property(2, Integer.class, "answerTypeEn", false, "ANSWER_TYPE_EN");
        public final static Property AnswerInt = new Property(3, Integer.class, "answerInt", false, "ANSWER_INT");
        public final static Property AnswerStr = new Property(4, String.class, "answerStr", false, "ANSWER_STR");
        public final static Property ServerAnswerId = new Property(5, Long.class, "serverAnswerId", false, "SERVER_ANSWER_ID");
        public final static Property FormQuestionGroupId = new Property(6, Long.class, "formQuestionGroupId", false, "FORM_QUESTION_GROUP_ID");
        public final static Property InputValuesDefault = new Property(7, String.class, "inputValuesDefault", false, "INPUT_VALUES_DEFAULT");
        public final static Property SurveyFormId = new Property(8, long.class, "surveyFormId", false, "SURVEY_FORM_ID");
    };

    private DaoSession daoSession;

    private Query<SurveyFormQuestion> surveyForm_SurveyFormQuestionListQuery;

    public SurveyFormQuestionDao(DaoConfig config) {
        super(config);
    }
    
    public SurveyFormQuestionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SURVEY_FORM_QUESTION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"QUESTION\" TEXT," + // 1: question
                "\"ANSWER_TYPE_EN\" INTEGER," + // 2: answerTypeEn
                "\"ANSWER_INT\" INTEGER," + // 3: answerInt
                "\"ANSWER_STR\" TEXT," + // 4: answerStr
                "\"SERVER_ANSWER_ID\" INTEGER," + // 5: serverAnswerId
                "\"FORM_QUESTION_GROUP_ID\" INTEGER," + // 6: formQuestionGroupId
                "\"INPUT_VALUES_DEFAULT\" TEXT," + // 7: inputValuesDefault
                "\"SURVEY_FORM_ID\" INTEGER NOT NULL );"); // 8: surveyFormId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SURVEY_FORM_QUESTION\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SurveyFormQuestion entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String question = entity.getQuestion();
        if (question != null) {
            stmt.bindString(2, question);
        }
 
        Integer answerTypeEn = entity.getAnswerTypeEn();
        if (answerTypeEn != null) {
            stmt.bindLong(3, answerTypeEn);
        }
 
        Integer answerInt = entity.getAnswerInt();
        if (answerInt != null) {
            stmt.bindLong(4, answerInt);
        }
 
        String answerStr = entity.getAnswerStr();
        if (answerStr != null) {
            stmt.bindString(5, answerStr);
        }
 
        Long serverAnswerId = entity.getServerAnswerId();
        if (serverAnswerId != null) {
            stmt.bindLong(6, serverAnswerId);
        }
 
        Long formQuestionGroupId = entity.getFormQuestionGroupId();
        if (formQuestionGroupId != null) {
            stmt.bindLong(7, formQuestionGroupId);
        }
 
        String inputValuesDefault = entity.getInputValuesDefault();
        if (inputValuesDefault != null) {
            stmt.bindString(8, inputValuesDefault);
        }
        stmt.bindLong(9, entity.getSurveyFormId());
    }

    @Override
    protected void attachEntity(SurveyFormQuestion entity) {
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
    public SurveyFormQuestion readEntity(Cursor cursor, int offset) {
        SurveyFormQuestion entity = new SurveyFormQuestion( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // question
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // answerTypeEn
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // answerInt
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // answerStr
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // serverAnswerId
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // formQuestionGroupId
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // inputValuesDefault
            cursor.getLong(offset + 8) // surveyFormId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SurveyFormQuestion entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setQuestion(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAnswerTypeEn(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setAnswerInt(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setAnswerStr(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setServerAnswerId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setFormQuestionGroupId(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setInputValuesDefault(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSurveyFormId(cursor.getLong(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(SurveyFormQuestion entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(SurveyFormQuestion entity) {
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
    
    /** Internal query to resolve the "surveyFormQuestionList" to-many relationship of SurveyForm. */
    public List<SurveyFormQuestion> _querySurveyForm_SurveyFormQuestionList(long surveyFormId) {
        synchronized (this) {
            if (surveyForm_SurveyFormQuestionListQuery == null) {
                QueryBuilder<SurveyFormQuestion> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.SurveyFormId.eq(null));
                surveyForm_SurveyFormQuestionListQuery = queryBuilder.build();
            }
        }
        Query<SurveyFormQuestion> query = surveyForm_SurveyFormQuestionListQuery.forCurrentThread();
        query.setParameter(0, surveyFormId);
        return query.list();
    }

}
