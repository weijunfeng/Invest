package com.weijunfeng.mvp_lib.rx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class RxBus {

    private final Subject<Object, Object> mBus;
    private final Map<Class, Object> mStickyEventMap;

    public RxBus() {
        mBus = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus getDefault() {
        return DefaultRxBusInstanceHolder.mDefaultInstance;
    }

    public void post(Object event) {
        if (hasObservers()) {
            mBus.onNext(event);
        }
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.asObservable().ofType(eventType).onBackpressureBuffer();
    }

    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }

    public <T> Observable<T> toObservableSticky(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = toObservable(eventType);
            Object event = mStickyEventMap.get(eventType);
            if (event != null) {
                return observable.mergeWith(Observable.just(eventType.cast(event)));
            } else {
                return observable;
            }

        }
    }

    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    public void removeAllStickyEvent() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }

    private boolean hasObservers() {
        return mBus.hasObservers();
    }

    public static class DefaultRxBusInstanceHolder {
        private static RxBus mDefaultInstance = new RxBus();
    }
}
