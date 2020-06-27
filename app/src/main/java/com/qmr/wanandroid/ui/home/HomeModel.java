package com.qmr.wanandroid.ui.home;

import android.util.Log;

import com.qmr.wanandroid.model.cache.CacheObserver;
import com.qmr.wanandroid.model.entity.ArticleListBean;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.qmr.wanandroid.model.entity.TopArticleBean;
import com.qmr.wanandroid.network.base.CheckAndSaveFlatMapFunc;
import com.qmr.wanandroid.network.base.CheckMapFunction;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.main.MainApi;
import com.qmr.wanandroid.ui.home.mvp.IHomeModel;
import com.qmr.wanandroid.ui.home.mvp.IHomePresenter;
import com.qmr.wanandroid.util.Const;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeModel implements IHomeModel {

    private static final String TAG = "HomeModel";

    public IHomePresenter presenter;
    CompositeDisposable cd = new CompositeDisposable();

    private int pages = 1;

    public HomeModel(IHomePresenter p) {
        this.presenter = p;
    }

    public void initBanner() {
        Observable<List<BannerBean>> local = CacheObserver.getBannerCache();
        Observable<List<BannerBean>> remote = RequestManager.getInstance().getService(MainApi.class)
                .banner()
                .flatMap(new CheckAndSaveFlatMapFunc<>(Const.KEY_CACHE_BANNER));

        Observable.concatDelayError(Arrays.asList(local, remote))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BannerBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<BannerBean> bannerBeans) {
                        Log.i(TAG, "onNext:BannerBean " + bannerBeans.size());
                        presenter.setBanner(bannerBeans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError:BannerBean " + e.getClass().getSimpleName());
                        presenter.loadError();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: banner");
                    }
                });
    }

    public void initTop() {
        Observable<List<TopArticleBean>> local = CacheObserver.getTopCache();
        Observable<List<TopArticleBean>> remote = RequestManager.getInstance().getService(MainApi.class)
                .topArticleList()
                .flatMap(new CheckAndSaveFlatMapFunc<>(Const.KEY_CACHE_TOP));

        Observable.concatDelayError(Arrays.asList(local, remote))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TopArticleBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: initTop");
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<TopArticleBean> bannerBeans) {
                        Log.i(TAG, "onNext: TopArticleBean " + bannerBeans.size());
                        presenter.setTop(bannerBeans);
                        presenter.endLoading();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: top " + e.getMessage());
                        presenter.loadError();
                    }

                    @Override
                    public void onComplete() {
                        presenter.endLoading();
                    }
                });

    }

    public void initArticle() {
        Observable<ArticleListBean> local = CacheObserver.getContentCache();
        Observable<ArticleListBean> remote = RequestManager.getInstance().getService(MainApi.class)
                .articleList(0)
                .flatMap(new CheckAndSaveFlatMapFunc<>(Const.KEY_CACHE_CONTENT));

        Observable.concatDelayError(Arrays.asList(local, remote))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleListBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: initArticle");
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArticleListBean articleListBean) {
                        Log.i(TAG, "onNext: ArticleList " + articleListBean.getArticles().size());
                        presenter.setContent(articleListBean.getArticles());
                        presenter.endLoading();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: Article " + e.getClass().getSimpleName() + e.getMessage());
                        presenter.loadError();
                    }

                    @Override
                    public void onComplete() {
                        presenter.endLoading();
                    }
                });
    }

    @Override
    public void loadMore() {
        RequestManager.getInstance().getService(MainApi.class)
                .articleList(pages)
                .map(new CheckMapFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleListBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArticleListBean articleListBean) {
                        presenter.loadMoreSuccess(articleListBean.getArticles());
                        pages++;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        presenter.loadError();
                        presenter.endLoading();
                    }

                    @Override
                    public void onComplete() {
                        presenter.endLoading();
                    }
                });
        //return null;
    }

    @Override
    public void bind() {

    }

    @Override
    public void unBind() {
        presenter.unBind();
        cd.dispose();
        presenter = null;
    }
}
