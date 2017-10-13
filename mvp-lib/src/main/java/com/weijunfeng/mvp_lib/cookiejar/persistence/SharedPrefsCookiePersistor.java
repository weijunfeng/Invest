package com.weijunfeng.mvp_lib.cookiejar.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class SharedPrefsCookiePersistor implements CookiePersistor {
    private SharedPreferences mSharedPreferences;

    public SharedPrefsCookiePersistor(Context context) {
        mSharedPreferences = context.getSharedPreferences("cookie_per", Context.MODE_PRIVATE);
    }

    @Override
    public List<Cookie> loadAll() {
        List<Cookie> cookieList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : mSharedPreferences.getAll().entrySet()) {
            String serializedCookie = (String) entry.getValue();
            Cookie cookie = new SerializableCookie().decode(serializedCookie);
            if (cookie != null) {
                cookieList.add(cookie);
            }

        }
        return cookieList;
    }

    @Override
    public void saveAll(Collection<Cookie> cookies) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        for (Cookie cookie : cookies) {
            if (cookie.persistent()) {
                edit.putString(createCookieKey(cookie), new SerializableCookie().encode(cookie));
            }
        }
        edit.apply();
    }

    private String createCookieKey(Cookie cookie) {
        return (cookie.secure() ? "https" : "http") + "://" + cookie.domain() + cookie.path() + "|" + cookie.name();
    }

    @Override
    public void removeAll(Collection<Cookie> cookies) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        for (Cookie cookie : cookies) {
            edit.remove(createCookieKey(cookie));
        }
        edit.apply();
    }

    @Override
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
}
