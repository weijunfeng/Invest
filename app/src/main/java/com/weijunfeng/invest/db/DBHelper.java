package com.weijunfeng.invest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by weijunfeng on 2017/9/30.
 * Email : weijunfeng@myhexin.com
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "invest.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_ENTRY = "invest_entry";
    public static final String TABLE_DETAIL = "invest_detail";
    public static final String TABLE_TYPE = "invest_type";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDetailTable(db);
        createEntryTable(db);
        createTypeTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createEntryTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + TABLE_ENTRY + " (" +
                EntryColumn.CODE + " INTEGER PRIMARY KEY," +
                EntryColumn.NAME + " TEXT," +
                EntryColumn.TYPE + " TEXT)";
        db.execSQL(sql);
    }

    private void createTypeTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + TABLE_TYPE + " (" +
                TypeColumn.IDENTIFIER + " INTEGER PRIMARY KEY," +
                TypeColumn.NAME + " TEXT)";
        db.execSQL(sql);
    }

    private void createDetailTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "
                + TABLE_DETAIL + " (" +
                DetailColumn.AUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DetailColumn.INVEST_CODE + " TEXT," +
                DetailColumn.INVEST_TIME + " TEXT," +
                DetailColumn.INVEST_NUM + " TEXT)";
        String indexSql = "CREATE UNIQUE INDEX investTime ON " +
                TABLE_DETAIL + "(" +
                DetailColumn.INVEST_TIME + ")";
        db.execSQL(sql);
        db.execSQL(indexSql);
    }

    public static class EntryColumn {
        public static final String NAME = "invest_name";
        public static final String TYPE = "type_identifier";
        public static final String CODE = "invest_code";
    }

    public static class TypeColumn {
        public static final String NAME = "type_name";
        public static final String IDENTIFIER = "identifier";
    }

    public static class DetailColumn {
        public static final String AUTO_ID = "_id";
        public static final String INVEST_CODE = "invest_code";
        public static final String INVEST_TIME = "invest_time";
        public static final String INVEST_NUM = "invest_num";
    }
}
