package com.qmr.wanandroid.ui.tixi.navi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qmr.base.BaseFragment;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.cache.CacheObserver;
import com.qmr.wanandroid.model.entity.NaviBean;
import com.qmr.wanandroid.network.base.CheckAndSaveFlatMapFunc;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.tixi.Tixi;
import com.qmr.wanandroid.util.Const;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NaviFragment extends BaseFragment {


    @BindView(R.id.rv_tixi)
    RecyclerView rvTixi;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    NaviRecyclerViewAdapter adapter;

    private boolean flag_need_refresh = true;


    public NaviFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_tixi;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_tixi, container, false);
            ButterKnife.bind(this, contentView);
            rvTixi.setLayoutManager(new LinearLayoutManager(getActivity()));
            initView();
        }
        return contentView;
    }

    private void initView() {
        adapter = new NaviRecyclerViewAdapter();
        rvTixi.setAdapter(adapter);
        //requireActivity().stopService()
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        Observer<List<NaviBean>> observer = new Observer<List<NaviBean>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                compositeDisposable.add(d);
                Log.i(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<NaviBean> naviBeans) {
                Log.w(TAG, "accept: tixiBeans.size() = " + naviBeans.size());
                flag_need_refresh = (naviBeans.size() == 0);
                adapter.resetData(naviBeans);
                srl.setRefreshing(false);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                shortToast(e.getMessage());
                srl.setRefreshing(false);
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
                srl.setRefreshing(false);
            }
        };

        Observable<List<NaviBean>> localOb = CacheObserver.getNavigationCache();
        Observable<List<NaviBean>> netOb = RequestManager.getInstance().getService(Tixi.class).navigation().flatMap(new CheckAndSaveFlatMapFunc<>(Const.KEY_CACHE_NAVIGATION));
        Observable.concatDelayError(Arrays.asList(localOb, netOb))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public String getTitle() {
        return "导航";
    }

    @Override
    public void scrollToTop() {
        rvTixi.smoothScrollToPosition(0);
        //rvTixi.smoothScrollBy(0,0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && flag_need_refresh)
            initData();
    }

}