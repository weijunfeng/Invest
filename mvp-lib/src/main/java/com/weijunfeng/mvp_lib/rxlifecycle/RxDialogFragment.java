package com.weijunfeng.mvp_lib.rxlifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class RxDialogFragment extends DialogFragment {

    public final <T> UntilEventObservaleTransformer<T, FragmentEvent> bindUtilDestroy() {
        return LifecycleMgr.getDefault().bindUtilEvent(FragmentEvent.DESTROY);
    }

    private void publishEvent(FragmentEvent event) {
        LifecycleMgr.getDefault().publishEvent(event);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        publishEvent(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        publishEvent(FragmentEvent.CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        publishEvent(FragmentEvent.CREATE_VIEW);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        publishEvent(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        publishEvent(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        publishEvent(FragmentEvent.PAUSE);
    }

    @Override
    public void onStop() {
        publishEvent(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        publishEvent(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        publishEvent(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        publishEvent(FragmentEvent.DETACH);
        super.onDetach();
    }
}
