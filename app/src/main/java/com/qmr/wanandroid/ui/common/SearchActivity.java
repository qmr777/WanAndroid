package com.qmr.wanandroid.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.qmr.base.activity.BaseActivity;
import com.qmr.base.adapter.OnItemClickListener;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.model.entity.HotKeyBean;
import com.qmr.wanandroid.model.entity.SearchResultBean;
import com.qmr.wanandroid.network.base.CheckMapFunction;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.main.MainApi;
import com.qmr.wanandroid.ui.home.adapter.MainArticleAdapter;
import com.qmr.wanandroid.ui.tixi.FlexboxAdapter;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 搜索页面
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_QUERY = "query";

    @BindView(R.id.textView)
    EditText textView;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.fl_hotkey)
    FlexboxLayout flHotkey;
    @BindView(R.id.rv_result)
    RecyclerView rvResult;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    SearchView mSearchView;

    FlexboxAdapter<HotKeyBean> hotKeyBeanFlexboxAdapter;
    MainArticleAdapter articleAdapter = new MainArticleAdapter();
    private String prevKey = "";
    private int mPage = 0;
    private boolean flag_search = false;//是否在搜索页
    private boolean flag_can_load_more = false;//是否在搜索页

    public static void linkStart(Activity activity, String msg) {
        Intent i = new Intent(activity, SearchActivity.class);
        i.putExtra(KEY_QUERY, msg);
        activity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initToolbar(toolbar, null);
        initView();
        initHotkey();
        //needSearch();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            needSearch();
    }

    @Override
    protected void initView() {
        button.setOnClickListener(this);
        rvResult.setAdapter(articleAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvResult.setLayoutManager(llm);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addOnScrollListener(new RecyclerView.OnScrollListener() {

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

        rvResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@androidx.annotation.NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (getWindow().peekDecorView() != null) {
                        assert imm != null;
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    search(mPage, textView.getText().toString());
                }
                return true;
            }
        });
    }

    protected void initHotkey() {

        hotKeyBeanFlexboxAdapter = new HotkeyAdapter(flHotkey);
        hotKeyBeanFlexboxAdapter.setOnItemClickListener(new OnItemClickListener<HotKeyBean>() {
            @Override
            public void OnItemClick(int position, HotKeyBean data) {
                search(mPage, data.getName());
            }
        });

        RequestManager.getInstance().getService(MainApi.class).hotKey()
                .map(new CheckMapFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HotKeyBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<HotKeyBean> hotKeyBeans) {
                        Log.i(TAG, "onNext: " + hotKeyBeans.size());
                        hotKeyBeanFlexboxAdapter.setData(hotKeyBeans);
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

    private void initSearch(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(mPage, s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
    }

    protected void loadMore() {
        if (TextUtils.isEmpty(prevKey) && flag_can_load_more) {
            search(mPage, prevKey);
            Log.i(TAG, "loadMore: " + prevKey + "  " + flag_can_load_more);
        }
    }

    private void needSearch() {//一次
        String k = getIntent().getStringExtra(KEY_QUERY);
        if (TextUtils.isEmpty(k)) return;
        search(mPage, k);
        //toolbar.setTitle(k);
    }

    protected void search(int page, String key) {
        if (TextUtils.isEmpty(key))
            return;
        Objects.requireNonNull(getSupportActionBar()).setTitle("搜索：" + key);
        //mSearchView.clearFocus();
        mSearchView.onActionViewCollapsed();
        flag_search = true;
        flag_can_load_more = true;
        flHotkey.setVisibility(View.GONE);
        rvResult.setVisibility(View.VISIBLE);
        if (!key.equals(prevKey)) {
            prevKey = key;
            mPage = 0;
            articleAdapter.clearData();
        }

        RequestManager.getInstance().getService(MainApi.class).search(mPage, key)
                .map(new CheckMapFunction<>())
                .map(new Function<SearchResultBean, List<ArticleBean>>() {
                    @Override
                    public List<ArticleBean> apply(SearchResultBean searchResultBean) throws Throwable {
                        return searchResultBean.getDatas();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ArticleBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<ArticleBean> articleBeans) {
                        if (articleBeans.size() != 0) {
                            mPage++;
                            articleAdapter.addData(articleBeans);
                        } else
                            flag_can_load_more = false;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        initSearch(menu);
        return true;
    }

/*
    @Override
    public void finish() {
        if (flag_search) {
            flag_search = false;
            rvResult.setVisibility(View.GONE);
            flHotkey.setVisibility(View.VISIBLE);
        } else {
            super.finish();
        }
    }
*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                search(mPage, textView.getText().toString());
                hideKeyboard();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (flag_search) {
                flag_search = false;
                rvResult.setVisibility(View.GONE);
                flHotkey.setVisibility(View.VISIBLE);
                toolbar.setTitle("搜索...");
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}