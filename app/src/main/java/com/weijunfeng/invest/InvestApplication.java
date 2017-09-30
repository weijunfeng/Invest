package com.weijunfeng.invest;

import android.app.Application;

/**
 * Created by weijunfeng on 2017/9/30.
 * Email : weijunfeng@myhexin.com
 */

public class InvestApplication extends Application {
    public static volatile Application sApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
