package com.weijunfeng.mvp_lib.rxlifecycle;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public final class UntilEventObservaleTransformer<T, R extends LifecycleEvent> implements Observable.Transformer<T, T> {
    final Observable<R> lifecycle;
    final R event;

    public UntilEventObservaleTransformer(Observable<R> lifecycle, R event) {
        this.lifecycle = lifecycle;
        this.event = event;
    }

    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.takeUntil(lifecycle.takeFirst(new Func1<R, Boolean>() {
            @Override
            public Boolean call(R r) {
                return r.equals(event);
            }
        }));
    }
}
