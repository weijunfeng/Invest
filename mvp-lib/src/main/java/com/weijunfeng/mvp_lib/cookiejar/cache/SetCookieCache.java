package com.weijunfeng.mvp_lib.cookiejar.cache;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

import okhttp3.Cookie;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class SetCookieCache implements CookieCache {
    private Set<IdentifiableCookie> mCookies;

    public SetCookieCache() {
        mCookies = new HashSet<IdentifiableCookie>();
    }


    @NonNull
    @Override
    public Iterator<Cookie> iterator() {
        return new Iterator<Cookie>() {
            private Iterator<IdentifiableCookie> mIterator = mCookies.iterator();

            @Override
            public boolean hasNext() {
                return mIterator.hasNext();
            }

            @Override
            public Cookie next() {
                return mIterator.next().getCookie();
            }

            @Override
            public void remove() {
                mIterator.remove();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Cookie> action) {

    }

    @Override
    public void addAll(Collection<Cookie> cookies) {
        updateCookies(IdentifiableCookie.decorateAll(cookies));
    }

    private void updateCookies(Collection<IdentifiableCookie> cookies) {
        this.mCookies.removeAll(cookies);
        mCookies.addAll(cookies);
    }

    @Override
    public void clear() {
        mCookies.clear();
    }
}
