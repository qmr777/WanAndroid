package com.qmr.wanandroid.ui.tixi.tixi;

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
import com.qmr.base.util.ThreadTransfer;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.cache.CacheObserver;
import com.qmr.wanandroid.model.entity.TixiBean;
import com.qmr.wanandroid.network.base.CheckAndSaveFlatMapFunc;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.tixi.Tixi;
import com.qmr.wanandroid.util.Const;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 体系fragment
 */
public class TixiFragment extends BaseFragment {

    @BindView(R.id.rv_tixi)
    RecyclerView rvTixi;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    TixiRecyclerViewAdapter adapter;

    private boolean flag_need_refresh = true;

    public TixiFragment() {
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
            rvTixi.setLayoutManager(new LinearLayoutManager(requireActivity()));
            initView();
        }
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new TixiRecyclerViewAdapter();
        rvTixi.setAdapter(adapter);
        initData();
    }

    private void initView() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    @Override
    public String getTitle() {
        return "体系";
    }

    @Override
    public void scrollToTop() {
        rvTixi.smoothScrollToPosition(0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "onHiddenChanged: ");
        if (!hidden && flag_need_refresh)
            initData();
    }

    private void initData() {

        Observer<List<TixiBean>> ob = new Observer<List<TixiBean>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                compositeDisposable.add(d);
                Log.i(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<TixiBean> tixiBeans) {
                Log.i(TAG, "onNext: tixi" + tixiBeans.size());
                if (tixiBeans.size() > 0)
                    flag_need_refresh = false;
                adapter.resetData(tixiBeans);
                srl.setRefreshing(false);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                shortToast(e.getMessage());
                Log.e(TAG, "onError: " + e.getClass().getSimpleName());
                srl.setRefreshing(false);
            }

            @Override
            public void onComplete() {
                srl.setRefreshing(false);
                Log.i(TAG, "onComplete: ");
            }
        };

        Observable<List<TixiBean>> local = CacheObserver.getTixiCache();
        Observable<List<TixiBean>> network = RequestManager.getInstance().getService(Tixi.class)
                .tixi().flatMap(new CheckAndSaveFlatMapFunc<>(Const.KEY_CACHE_TIXI)).compose(new ThreadTransfer<>());

        Observable.concatEagerDelayError(Arrays.asList(local, network))
                .subscribe(ob);

    }
}