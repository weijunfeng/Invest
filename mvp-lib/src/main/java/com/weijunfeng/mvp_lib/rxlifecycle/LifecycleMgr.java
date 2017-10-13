package com.weijunfeng.mvp_lib.rxlifecycle;

import com.weijunfeng.mvp_lib.Singleton;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class LifecycleMgr {
    private static final Singleton<LifecycleMgr> DEFAULT = new Singleton<LifecycleMgr>() {
        @Override
        protected LifecycleMgr create() {
            return new LifecycleMgr();
        }
    };
    private final BehaviorSubject<LifecycleEvent> lifecycleSubject = BehaviorSubject.create();

    private final Observable<LifecycleEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    <T, R extends LifecycleEvent> UntilEventObservaleTransformer<T, R> bindUtilEvent(R event) {
        return (UntilEventObservaleTransformer<T, R>) new UntilEventObservaleTransformer<>(lifecycle(), event);
    }

    void publishEvent(LifecycleEvent event) {
        lifecycleSubject.onNext(event);
    }

    static LifecycleMgr getDefault() {
        return DEFAULT.get();
    }
}
