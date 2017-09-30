package com.weijunfeng.invest.util;

import com.weijunfeng.invest.db.DBManager;

/**
 * Created by weijunfeng on 2017/9/30.
 * Email : weijunfeng@myhexin.com
 */

public class UIUtils {
    public static void exitApp() {
        DBManager.destroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
