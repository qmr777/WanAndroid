package com.qmr.wanandroid.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.qmr.base.activity.BaseActivity;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.model.entity.ArticleListBean;
import com.qmr.wanandroid.model.entity.TixiArticleBean;
import com.qmr.wanandroid.network.base.CheckMapFunction;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.base.WanAndroidResponse;
import com.qmr.wanandroid.network.main.MainApi;
import com.qmr.wanandroid.network.tixi.Tixi;
import com.qmr.wanandroid.ui.tixi.tixi.TixiArticleListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 文章列表Activity
 */
public class ArticleListActivity extends BaseActivity {

    public static final int TYPE_AUTHOR = 1;
    public static final int TYPE_TIXI = 2;

    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private TixiArticleListAdapter articleListAdapter;
    private int cid;//体系id
    private String name;

    private int type;


    private int pages = 0;//页码
    private int maxPages = Integer.MAX_VALUE;//页码

    public static void linkStart(Context context, int cid, String name) {
        Intent i = new Intent(context, ArticleListActivity.class);
        i.putExtra("type", TYPE_TIXI);
        i.putExtra("cid", cid);
        i.putExtra("name", name);
        context.startActivity(i);
    }

    public static void linkStart(Context context, String author) {
        Intent i = new Intent(context, ArticleListActivity.class);
        i.putExtra("type", TYPE_AUTHOR);
        i.putExtra("name", author);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        articleListAdapter = new TixiArticleListAdapter();
        rvArticle.setAdapter(articleListAdapter);
        type = getIntent().getIntExtra("type", 0);
        name = getIntent().getStringExtra("name");
        cid = getIntent().getIntExtra("cid", 60);
        initToolbar(toolbar, name);
        switch (type) {
            case 0:
                break;
            case 1:
                searchAuthorArticle(0);
                break;
            case 2:
                searchKnowledgeArticle(0);
        }
    }

    protected void searchKnowledgeArticle(int page) {
        if (page >= maxPages)
            return;
        RequestManager.getInstance().getService(Tixi.class)
                .tixiArticle(page, cid)
                .map(new Function<WanAndroidResponse<TixiArticleBean>, List<ArticleBean>>() {
                    @Override
                    public List<ArticleBean> apply(WanAndroidResponse<TixiArticleBean> listWanAndroidResponse) throws Throwable {
                        //pages = listWanAndroidResponse.getData().getCurPage();
                        //maxPages = listWanAndroidResponse.getData().getPageCount()-1;
                        return listWanAndroidResponse.getData().getDatas();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ArticleBean>>() {
                    @Override
                    public void accept(List<ArticleBean> datasBeans) throws Throwable {
                        Log.i(TAG, "accept: datasBeansSize " + datasBeans.size());
                        if (datasBeans.size() != 0) {
                            articleListAdapter.addData(datasBeans);
                            pages = page + 1;
                            maxPages = 500;
                        } else {
                            maxPages = -1;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.e(TAG, "accept: " + throwable.getMessage());
                    }
                });
    }

    protected void searchAuthorArticle(int page) {
        if (page >= maxPages)
            return;

        RequestManager.getInstance().getService(MainApi.class)
                .searchAuthor(page, name)
                .map(new CheckMapFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleListBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull ArticleListBean articleListBean) {
                        Log.i(TAG, "onNext: articleListBean.getArticles().size()" + articleListBean.getArticles().size());
                        articleListAdapter.resetData(articleListBean.getArticles());
                        pages = page + 1;
                        maxPages = articleListBean.getPageCount();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        shortToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    protected void loadMore() {
        switch (type) {
            case TYPE_AUTHOR:
                searchAuthorArticle(pages);
                break;
            case TYPE_TIXI:
                searchKnowledgeArticle(pages);
                break;
            default:
                break;
        }
    }
}