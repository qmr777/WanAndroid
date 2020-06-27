package com.qmr.wanandroid.ui.wenda;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qmr.base.BaseFragment;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.cache.CacheObserver;
import com.qmr.wanandroid.model.entity.WendaBean;
import com.qmr.wanandroid.network.base.CheckAndSaveFlatMapFunc;
import com.qmr.wanandroid.network.base.CheckAndSaveMapFunc;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.base.WanAndroidResponse;
import com.qmr.wanandroid.network.main.MainApi;
import com.qmr.wanandroid.ui.common.WebViewActivity;
import com.qmr.wanandroid.util.Const;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WendaFragment extends BaseFragment {

    public static final String TAG = "wendaFrag";


    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private WendaAdapter adapter;
    private int pages = 0;
    private boolean canLoadMore = true;

    public WendaFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_wenda, container, false);
            ButterKnife.bind(this, contentView);
        }
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    void initView() {
        LinearLayoutManager llm = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new WendaAdapter();
        adapter.setOnItemClickListener((position, data) -> WebViewActivity.linkStart(requireActivity(), data.getTitle(), data.getLink(), data.getId()));

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {
                int mLastChildPosition = llm.findLastVisibleItemPosition();
                int itemTotalCount = llm.getItemCount();
                View lastChildView = llm.getChildAt(llm.getChildCount() - 1);
                if (lastChildView == null) return;
                int lastChildBottom = lastChildView.getBottom();
                int recyclerBottom = recyclerView.getBottom();
                if (mLastChildPosition == itemTotalCount - 1 && lastChildBottom == recyclerBottom)
                    loadMore();
            }
        });

    }

    void initData() {
        Observable<WendaBean> local = CacheObserver.getWendaCache();
        Observable<WendaBean> network = RequestManager.getInstance().getService(MainApi.class)
                .wenda(0).flatMap(new CheckAndSaveFlatMapFunc<>(Const.KEY_WENDA));

        Observer<WendaBean> ob = new Observer<WendaBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull WendaBean wendaBean) {
                Log.i(TAG, "onNext: 问答" + wendaBean.getSize());
                canLoadMore = !wendaBean.isOver();
                pages = wendaBean.getCurPage() + 1;
                adapter.addData(wendaBean.getDatas());
                srl.setRefreshing(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                srl.setRefreshing(false);
                Log.e(TAG, "onError: " + e.getMessage(), e);
                shortToast(e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        };
        Observable.concatEagerDelayError(Arrays.asList(local, network)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ob);
        //load();
        //load();
    }

    private void load() {
        if (!canLoadMore) return;
        RequestManager.getInstance().getService(MainApi.class)
                .wenda(pages)
                .map(new CheckAndSaveMapFunc<>(Const.KEY_WENDA))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WendaBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull WendaBean wendaBean) {
                        Log.i(TAG, "onNext: 问答" + wendaBean.getSize());
                        canLoadMore = !wendaBean.isOver();
                        pages = wendaBean.getCurPage() + 1;
                        adapter.addData(wendaBean.getDatas());
                        srl.setRefreshing(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        srl.setRefreshing(false);
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                        shortToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    private void loadMore() {
        if (!canLoadMore) return;
        RequestManager.getInstance().getService(MainApi.class)
                .wenda(pages)
                .map(new Function<WanAndroidResponse<WendaBean>, WendaBean>() {
                    @Override
                    public WendaBean apply(WanAndroidResponse<WendaBean> wendaBeanWanAndroidResponse) throws Throwable {
                        return wendaBeanWanAndroidResponse.getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WendaBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull WendaBean wendaBean) {
                        Log.i(TAG, "onNext: 问答" + wendaBean.getSize());
                        canLoadMore = !wendaBean.isOver();
                        pages = wendaBean.getCurPage() + 1;
                        adapter.addData(wendaBean.getDatas());
                        srl.setRefreshing(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        srl.setRefreshing(false);
                        Log.e(TAG, "onError: " + e.getMessage(), e);
                        shortToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public String getTitle() {
        return "问答";
    }

    @Override
    protected int getLayoutID() {
        return 0;
    }
}