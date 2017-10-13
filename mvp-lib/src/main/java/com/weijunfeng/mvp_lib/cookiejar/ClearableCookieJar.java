package com.weijunfeng.mvp_lib.cookiejar;

import okhttp3.CookieJar;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public interface ClearableCookieJar extends CookieJar{
    void clear();
}
