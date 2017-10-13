package com.weijunfeng.mvp_lib.fetcher;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public interface ServiceFetcher<T> {
    T getService();
    Class<T> getServiceClass();
}
