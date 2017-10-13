package com.weijunfeng.mvp_lib.cookiejar.persistence;

import java.util.Collection;
import java.util.List;

import okhttp3.Cookie;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public interface CookiePersistor {
    List<Cookie> loadAll();

    void saveAll(Collection<Cookie> cookies);

    void removeAll(Collection<Cookie> cookies);

    void clear();
}
