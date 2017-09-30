package com.weijunfeng.invest.db;

import android.database.sqlite.SQLiteDatabase;

import com.weijunfeng.invest.InvestApplication;

/**
 * Created by weijunfeng on 2017/9/30.
 * Email : weijunfeng@myhexin.com
 */
public class DBManager {
    private static volatile DBManager ourInstance = null;
    private SQLiteDatabase mDatabase;

    private DBManager() {
        mDatabase = new DBHelper(InvestApplication.sApplication).getWritableDatabase();
    }

    public static DBManager getInstance() {
        if (ourInstance == null) {
            synchronized (DBManager.class) {
                if (ourInstance == null) {
                    ourInstance = new DBManager();
                }
            }
        }
        return ourInstance;
    }

    public static void destroy() {
        if (ourInstance != null) {
            ourInstance.closeDb();
            ourInstance = null;
        }
    }

    private void closeDb() {
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }
    }
}
