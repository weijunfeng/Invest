package com.weijunfeng.mvp_lib.cookiejar;

import com.weijunfeng.mvp_lib.Singleton;
import com.weijunfeng.mvp_lib.cookiejar.cache.CookieCache;
import com.weijunfeng.mvp_lib.cookiejar.cache.SetCookieCache;
import com.weijunfeng.mvp_lib.cookiejar.persistence.CookiePersistor;
import com.weijunfeng.mvp_lib.cookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class PersistentCookieJar implements ClearableCookieJar {
    private static Singleton<PersistentCookieJar> sSingleton = new Singleton<PersistentCookieJar>() {
        @Override
        protected PersistentCookieJar create() {
            return new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(null));
        }
    };
    private CookieCache mCache;
    private CookiePersistor mPersistor;

    public PersistentCookieJar(CookieCache cache, CookiePersistor persistor) {
        mCache = cache;
        mPersistor = persistor;
        mCache.addAll(persistor.loadAll());
    }

    public static PersistentCookieJar getSingleton() {
        return sSingleton.get();
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        mCache.addAll(cookies);
        mPersistor.saveAll(cookies);
    }

    @Override
    synchronized public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookiesToRemove = new ArrayList<>();
        List<Cookie> validCookies = new ArrayList<>();

        for (Iterator<Cookie> it = mCache.iterator(); it.hasNext(); ) {
            Cookie currentCookie = it.next();

            if (isCookieExpired(currentCookie)) {
                cookiesToRemove.add(currentCookie);
                it.remove();

            } else if (currentCookie.matches(url)) {
                validCookies.add(currentCookie);
            }
        }

        mPersistor.removeAll(cookiesToRemove);

        return validCookies;
    }

    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    public synchronized void clearSession() {
        mCache.clear();
        mCache.addAll(mPersistor.loadAll());
    }

    @Override
    public synchronized void clear() {
        mCache.clear();
        mPersistor.clear();
    }
}
