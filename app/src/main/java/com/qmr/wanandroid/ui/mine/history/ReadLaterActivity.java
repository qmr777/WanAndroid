package com.qmr.wanandroid.ui.mine.history;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.qmr.base.activity.BaseActivity;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.database.DatabaseManager;
import com.qmr.wanandroid.model.entity.HistoryBean;
import com.qmr.wanandroid.model.entity.ReadLaterBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReadLaterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;

    HistoryAdapter adapter = new HistoryAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_later);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        initToolbar(toolbar, "稍后再看");
        rv.setAdapter(adapter);
    }

    private void initData() {
        Observable.create(new ObservableOnSubscribe<List<ReadLaterBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<ReadLaterBean>> emitter) throws Throwable {
                emitter.onNext(DatabaseManager.getInstance().getReadLaterDao().getHistory());
                emitter.onComplete();
            }
        }).map(new Function<List<ReadLaterBean>, List<HistoryBean>>() {
            @Override
            public List<HistoryBean> apply(List<ReadLaterBean> readLaterBeans) throws Throwable {
                List<HistoryBean> beans = new ArrayList<>(readLaterBeans.size());
                for (ReadLaterBean r : readLaterBeans) {
                    HistoryBean hb = new HistoryBean();
                    hb.setTime(r.getTime());
                    hb.setTitle(r.getTitle());
                    hb.setLink(r.getLink());
                    beans.add(hb);
                }
                return beans;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HistoryBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<HistoryBean> historyBeans) {
                        adapter.resetData(historyBeans);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}