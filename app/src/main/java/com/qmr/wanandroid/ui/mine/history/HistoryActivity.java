package com.qmr.wanandroid.ui.mine.history;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.qmr.base.activity.BaseActivity;
import com.qmr.base.adapter.OnItemClickListener;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.database.DatabaseManager;
import com.qmr.wanandroid.model.entity.HistoryBean;
import com.qmr.wanandroid.ui.common.WebViewActivity;

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
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 阅读历史页面
 */
public class HistoryActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(historyAdapter);
        rvMain.setItemAnimator(new DefaultItemAnimator());
        toolbar.setTitle("历史记录");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void clearHistory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认")
                .setMessage("确定要清除阅读记录吗？此操作不可撤销。")
                .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                                DatabaseManager.getInstance().getHistoryDao().deleteAll();
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Integer>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {
                                        disposable.add(d);
                                    }

                                    @Override
                                    public void onNext(@NonNull Integer integer) {
                                        shortToast("清除成功");
                                        historyAdapter.clearData();
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        debugToast(e.getMessage());
                                        shortToast("发生错误！");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }

                                });
                    }
                }).setNegativeButton("取消", null).create().show();
    }

    protected void initData() {
        historyAdapter = new HistoryAdapter();
        Observable.create(new ObservableOnSubscribe<List<HistoryBean>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<HistoryBean>> emitter) throws Throwable {
                //List<HistoryBean> historyBeans = DatabaseManager.getInstance().getHistoryDao2().getHistory();
                List<HistoryBean> historyBeans = DatabaseManager.getInstance().getHistoryDao().getHistory();
                Log.i(TAG, "subscribe: historyBeans.size() = " + historyBeans.size());
                emitter.onNext(historyBeans);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<HistoryBean>>() {
                    @Override
                    public void accept(List<HistoryBean> historyBeans) throws Throwable {
                        historyAdapter.resetData(historyBeans);
                        historyAdapter.setOnItemClickListener(new OnItemClickListener<HistoryBean>() {
                            @Override
                            public void OnItemClick(int position, HistoryBean data) {
                                WebViewActivity.linkStart(HistoryActivity.this, data.getTitle(), data.getLink(), data.get_id());
                            }
                        });
                    }
                });
    }

    @Override
    protected int getMenuRes() {
        return R.menu.toolbar_history;
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                clearHistory();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}