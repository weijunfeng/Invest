package com.weijunfeng.mvp_lib.cookiejar.cache;

import java.util.Collection;

import okhttp3.Cookie;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public interface CookieCache extends Iterable<Cookie> {
    void addAll(Collection<Cookie> cookies);

    void clear();
}
