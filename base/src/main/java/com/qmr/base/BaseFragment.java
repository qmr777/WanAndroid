package com.qmr.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * onAttach
 * onCreate
 * onCreateView
 * onActivityCreate
 * <p>
 * onStart
 * onResume
 * onPause
 * onStop
 * <p>
 * onDestroy
 * onDestroyView
 * onDetach
 */

public abstract class BaseFragment extends Fragment {

    protected static String TAG;
    protected static String CACHE_TAG;

    protected View contentView;

    protected CompositeDisposable compositeDisposable;

    public BaseFragment() {
        Log.d(TAG, "BaseFragment: ");
        TAG = this.getClass().getSimpleName();
        CACHE_TAG = TAG.toLowerCase();
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        compositeDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        if (getLayoutID() != 0 && contentView == null) {
            contentView = inflater.inflate(getLayoutID(), container, false);
        }
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: ");
    }

    protected abstract int getLayoutID();

    protected void shortToast(CharSequence message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void debugToast(CharSequence message) {
        if (BuildConfig.DEBUG)
            shortToast(message);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.anim_activity_start_in, R.anim.anim_activity_start_out);

    }

    public void scrollToTop() {
    }

    protected String getName() {
        return TAG;
    }

    public String getTitle() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}