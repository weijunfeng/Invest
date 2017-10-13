package com.weijunfeng.mvp_lib;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public abstract class Singleton<T> {
    private T mInsstance;

    protected abstract T create();

    public final T get() {
        if (null == mInsstance) {
            synchronized (this) {
                if (mInsstance == null) {
                    mInsstance = create();
                }
                return mInsstance;
            }
        }
        return mInsstance;
    }
}
