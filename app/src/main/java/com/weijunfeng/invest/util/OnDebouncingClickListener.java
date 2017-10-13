package com.weijunfeng.invest.util;

import android.view.View;

/**
 * Created by weijunfeng on 2017/10/12.
 * Email : weijunfeng@myhexin.com
 */

public abstract class OnDebouncingClickListener implements View.OnClickListener {
    private static boolean enabled = true;
    private static final Runnable ENABLE_AGAIN = new Runnable() {
        @Override
        public void run() {
            enabled = true;
        }
    };

    @Override
    public final void onClick(View v) {
        if (enabled) {
            enabled = false;
            v.postDelayed(ENABLE_AGAIN, 300);
            doClick(v);
        }
    }

    public abstract void doClick(View v);
}
