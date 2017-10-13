package com.weijunfeng.mvp_lib.fetcher;

import java.lang.ref.SoftReference;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public abstract class CachedServiceFetcher<T> implements ServiceFetcher<T> {
    private volatile SoftReference<T> cached;

    @Override
    public final T getService() {
        if (cached == null || cached.get() == null) {
            synchronized (this) {
                if (cached == null || cached.get() == null) {
                    cached = new SoftReference<T>(createService());
                }
            }
        }
        return cached.get();
    }

    protected abstract T createService();
}
