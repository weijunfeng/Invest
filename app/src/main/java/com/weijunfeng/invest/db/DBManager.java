package com.weijunfeng.invest.db;

import android.content.ContentValues;
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

    public boolean insertInvestType(CharSequence iden, CharSequence name) {
        ContentValues values = new ContentValues(2);
        values.put(DBHelper.TypeColumn.IDENTIFIER, String.valueOf(iden));
        values.put(DBHelper.TypeColumn.NAME, String.valueOf(name));
        return mDatabase.insert(DBHelper.TABLE_TYPE, null, values) != -1;
    }


    private void closeDb() {
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }
    }
}
