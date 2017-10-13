package com.weijunfeng.mvp_lib.cookiejar.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Cookie;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class IdentifiableCookie {
    private Cookie mCookie;

    public IdentifiableCookie(Cookie cookie) {
        mCookie = cookie;
    }

    static List<IdentifiableCookie> decorateAll(Collection<Cookie> cookies) {
        List<IdentifiableCookie> identifiableCookies = new ArrayList<>(cookies.size());
        for (Cookie cookie : cookies) {
            identifiableCookies.add(new IdentifiableCookie(cookie));
        }
        return identifiableCookies;
    }

    public Cookie getCookie() {
        return mCookie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentifiableCookie)) return false;

        IdentifiableCookie that = (IdentifiableCookie) o;

        return getCookie().equals(that.getCookie());
    }

    @Override
    public int hashCode() {
        return getCookie().hashCode();
    }
}
