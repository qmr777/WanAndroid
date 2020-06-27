package com.qmr.wanandroid.ui.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import com.qmr.base.activity.BaseActivity;
import com.qmr.base.util.DateUtil;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.database.DatabaseManager;
import com.qmr.wanandroid.model.entity.HistoryBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wv_content)
    WebView wv;
    @BindView(R.id.pb_wv)
    ProgressBar progressBar;

    private WebChromeClient mClient;

    public static void linkStart(Context activity, String title, String url, int id) {

        Observable.just(1).map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) throws Throwable {
                Log.i("WebViewActivity", "apply: " + id + "   " + title);
                HistoryBean historyBean = new HistoryBean();
                historyBean.setLink(url);
                historyBean.setTitle(title);
                historyBean.set_id(id);
                historyBean.setTime(DateUtil.YYYYMMDD(System.currentTimeMillis()));
                DatabaseManager.getInstance().getHistoryDao().insert(historyBean);
                return 1;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Throwable {
                        Intent i = new Intent(activity, WebViewActivity.class);
                        i.putExtra("title", title);
                        i.putExtra("url", url);
                        activity.startActivity(i);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wv.destroy();
    }

    @SuppressLint("SetJavaScriptEnabled")
    void initData() {
        wv.setWebViewClient(new WebViewClient());
        mClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100)
                    progressBar.setVisibility(View.GONE);
                else
                    progressBar.setProgress(newProgress);
            }
        };
        wv.setWebChromeClient(mClient);
        wv.getSettings().setJavaScriptEnabled(true);
        final String url = getIntent().getStringExtra("url");
        wv.loadUrl(url);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        final String title = getIntent().getStringExtra("title");
        initToolbar(toolbar, title);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.scrollTo(0, 0);
            }
        });
    }
}