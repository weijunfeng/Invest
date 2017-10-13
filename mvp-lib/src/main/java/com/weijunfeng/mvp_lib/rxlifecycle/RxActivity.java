package com.weijunfeng.mvp_lib.rxlifecycle;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class RxActivity extends FragmentActivity {

    public final <T> UntilEventObservaleTransformer<T, ActivityEvent> bindUtilDestroy() {
        return LifecycleMgr.getDefault().bindUtilEvent(ActivityEvent.DESTROY);
    }

    private void publishEvent(ActivityEvent event) {
        LifecycleMgr.getDefault().publishEvent(event);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        publishEvent(ActivityEvent.CREATE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        publishEvent(ActivityEvent.CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        publishEvent(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        publishEvent(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        publishEvent(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        publishEvent(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        publishEvent(ActivityEvent.DESTROY);
        super.onDestroy();
    }
}
