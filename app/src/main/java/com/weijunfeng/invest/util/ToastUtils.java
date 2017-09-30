package com.weijunfeng.invest.util;

import android.widget.Toast;

import com.weijunfeng.invest.InvestApplication;

/**
 * Created by weijunfeng on 2017/9/30.
 * Email : weijunfeng@myhexin.com
 */
public class ToastUtils {
    private static Toast sToast = null;

    public static void toast(CharSequence charSequence) {
        if (sToast == null) {
            sToast = Toast.makeText(InvestApplication.sApplication, "", Toast.LENGTH_SHORT);
        }
        sToast.setText(charSequence);
        sToast.show();
    }
}
