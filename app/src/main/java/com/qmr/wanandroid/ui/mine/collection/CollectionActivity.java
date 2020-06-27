package com.qmr.wanandroid.ui.mine.collection;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.qmr.base.activity.BaseActivity;
import com.qmr.base.adapter.OnItemClickListener;
import com.qmr.base.adapter.OnItemLongClickListener;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.CollectionBean;
import com.qmr.wanandroid.model.entity.CollectionItemBean;
import com.qmr.wanandroid.network.base.CheckMapFunction;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.mine.MineApi;
import com.qmr.wanandroid.ui.common.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 我收藏的文章 页面
 */
public class CollectionActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    private CollectionAdapter adapter;
    private int page = 0;
    private boolean canLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        loadCollections2();
        initView();
    }

    @Override
    protected void initView() {
        initToolbar(toolbar, "我的收藏");
        adapter = new CollectionAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener<CollectionItemBean>() {
            @Override
            public void OnItemClick(int position, CollectionItemBean data) {
                WebViewActivity.linkStart(CollectionActivity.this, data.getTitle(), data.getLink(), data.getId());
            }
        });

        adapter.setOnItemLongClickListener(new OnItemLongClickListener<CollectionItemBean>() {
            @Override
            public boolean OnItemLongClick(int position, CollectionItemBean data) {
                Log.i(TAG, "OnItemLongClick: ");
                removeCollection(data);
                return true;
            }
        });
        rvMain.setAdapter(adapter);
    }


    protected void loadCollections2() {
        if (!canLoadMore)
            return;

        RequestManager.getInstance().getService(MineApi.class).getCollections(page)
                .map(new CheckMapFunction<>())
                .map(new Function<CollectionBean, List<CollectionItemBean>>() {
                    @Override
                    public List<CollectionItemBean> apply(CollectionBean collectionBean) throws Throwable {
                        return collectionBean.getDatas();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<CollectionItemBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<CollectionItemBean> collectionItemBeans) {
                        Log.i(TAG, "onNext: " + collectionItemBeans.size());
                        if (collectionItemBeans.size() == 0) {
                            canLoadMore = false;
                        } else {
                            adapter.addData(collectionItemBeans);
                            page++;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        shortToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        debugToast("collections onComplete");
                    }
                });
    }

    protected void removeCollection(CollectionItemBean data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("取消收藏")
                .setMessage("确定要取消收藏文章 " + data.getTitle() + " 吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeArticle(data);
                    }
                }).setNegativeButton("取消", null)
                .create().show();
    }


    protected void removeArticle(CollectionItemBean data) {
        RequestManager.getInstance().getService(MineApi.class)
                .removeCollections(data.getId(), data.getOriginId())
                .map(new CheckMapFunction())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        shortToast("取消收藏");
                        adapter.removeItem(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        shortToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}