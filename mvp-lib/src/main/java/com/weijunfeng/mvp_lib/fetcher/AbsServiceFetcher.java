package com.weijunfeng.mvp_lib.fetcher;

import com.weijunfeng.mvp_lib.converter.FastJsonConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public abstract class AbsServiceFetcher<T> extends CachedServiceFetcher<T> {
    final Class<T> service;

    public AbsServiceFetcher(Class<T> service) {
        this.service = service;
    }

    @Override
    protected T createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
        return retrofit.create(service);
    }

    protected abstract OkHttpClient createOkHttpClient();

    protected abstract String baseUrl();
}
