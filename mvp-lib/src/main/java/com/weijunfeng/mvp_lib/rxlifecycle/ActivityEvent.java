package com.weijunfeng.mvp_lib.rxlifecycle;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public enum ActivityEvent implements LifecycleEvent {
    CREATE,
    START,
    RESUME,
    PAUSE,
    STOP,
    DESTROY;
}
