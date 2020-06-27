package com.qmr.wanandroid.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.MergeAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qmr.base.BaseFragment;
import com.qmr.base.adapter.OnItemClickListener;
import com.qmr.base.adapter.OnItemLongClickListener;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.qmr.wanandroid.model.entity.TopArticleBean;
import com.qmr.wanandroid.ui.common.WebViewActivity;
import com.qmr.wanandroid.ui.dialog.ArticleDialog;
import com.qmr.wanandroid.ui.home.adapter.HeaderAdapter;
import com.qmr.wanandroid.ui.home.adapter.MainArticleAdapter;
import com.qmr.wanandroid.ui.home.adapter.TopArticleAdapter;
import com.qmr.wanandroid.ui.home.mvp.IHomePresenter;
import com.qmr.wanandroid.ui.home.mvp.IHomeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements IHomeView {

    IHomePresenter presenter;

    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    //    private MainBannerAdapter bannerAdapter;
    private HeaderAdapter headerAdapter;
    private TopArticleAdapter topArticleAdapter;
    private MainArticleAdapter articleListAdapter;

    private LinearLayoutManager llm;

    public static final String BANNER = "b";
    public static final String TOP = "t";
    public static final String CONTENT = "c";

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter();
        presenter.bindView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutID(), container, false);
            ButterKnife.bind(this, contentView);
            initView();
        }
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            headerAdapter.resetData(savedInstanceState.getParcelableArrayList(BANNER));
            topArticleAdapter.resetData(savedInstanceState.getParcelableArrayList(TOP));
            articleListAdapter.resetData(savedInstanceState.getParcelableArrayList(CONTENT));
            llm.scrollToPosition(savedInstanceState.getInt("position", 0));
        } else {
            initData();
        }
    }

    @Override
    public void initView() {

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.initData();
            }
        });

        llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMain.setLayoutManager(llm);
        rvMain.setItemAnimator(new DefaultItemAnimator());
        rvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int mLastChildPosition = llm.findLastVisibleItemPosition();
                int itemTotalCount = llm.getItemCount();
                View lastChildView = llm.getChildAt(llm.getChildCount() - 1);
                if (lastChildView == null) return;
                int lastChildBottom = lastChildView.getBottom();
                int recyclerBottom = recyclerView.getBottom();
                if (lastChildBottom == recyclerBottom) {
                    presenter.startLoading();
                }
            }
        });

        headerAdapter = new HeaderAdapter();
        topArticleAdapter = new TopArticleAdapter();
        articleListAdapter = new MainArticleAdapter();

        articleListAdapter.setOnItemClickListener(new OnItemClickListener<ArticleBean>() {
            @Override
            public void OnItemClick(int position, ArticleBean data) {
                WebViewActivity.linkStart(HomeFragment.this.getActivity(), data.getTitle(), data.getLink(), data.getId());
            }
        });
        articleListAdapter.setOnItemLongClickListener(new OnItemLongClickListener<ArticleBean>() {
            @Override
            public boolean OnItemLongClick(int position, ArticleBean data) {
                showArticleDialog(data, position);
                return true;
            }
        });

        topArticleAdapter.setOnItemClickListener(new OnItemClickListener<TopArticleBean>() {
            @Override
            public void OnItemClick(int position, TopArticleBean data) {
                WebViewActivity.linkStart(HomeFragment.this.getActivity(), data.getTitle(), data.getLink(), data.getId());
            }
        });
        topArticleAdapter.setOnItemLongClickListener(new OnItemLongClickListener<TopArticleBean>() {
            @Override
            public boolean OnItemLongClick(int position, TopArticleBean data) {
                showTopDialog(data, position);
                return true;
            }
        });

        MergeAdapter adapter = new MergeAdapter(headerAdapter, topArticleAdapter, articleListAdapter);
        rvMain.setAdapter(adapter);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void bindPresenter(IHomePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void unbindPresenter() {
        presenter.unBind();
        presenter = null;
    }

    @Override
    public void initData() {
        srl.setRefreshing(true);
        presenter.initData();
    }

    @Override
    public void initSuccess() {
        srl.setRefreshing(false);
    }

    @Override
    public void initError() {
        srl.setRefreshing(false);
        shortToast("网络错误");
    }

    @Override
    public void startLoading() {
        srl.setRefreshing(true);
        presenter.startLoading();
    }

    @Override
    public void loadSuccess() {
        srl.setRefreshing(false);
    }

    @Override
    public void loadError() {
        srl.setRefreshing(false);
        shortToast("网络错误");
    }

    @Override
    public void shortToast(CharSequence message) {
        super.shortToast(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindPresenter();
    }


    void showTopDialog(TopArticleBean bean, int position) {
        new ArticleDialog(bean).show(getChildFragmentManager(), null);
    }

    void showArticleDialog(ArticleBean bean, int position) {
        new ArticleDialog(bean).show(getChildFragmentManager(), null);
    }

    @Override
    public void setBannerData(List<BannerBean> bean) {
        headerAdapter.resetData(bean);
    }

    @Override
    public void setTopData(List<TopArticleBean> bean) {
        Log.i(TAG, "setTopData: " + bean.size());
        topArticleAdapter.resetData(bean);
    }

    @Override
    public void setContentBean(List<ArticleBean> bean) {
        articleListAdapter.resetData(bean);
    }

    @Override //加载更多
    public void addContent(List<ArticleBean> beans) {
        articleListAdapter.addData(beans);
    }

    @Override
    public void refresh() {
        topArticleAdapter.clearData();
        articleListAdapter.clearData();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", llm.findFirstCompletelyVisibleItemPosition());
        outState.putParcelableArrayList(TOP, topArticleAdapter.getData());
        outState.putParcelableArrayList(CONTENT, articleListAdapter.getData());
        outState.putParcelableArrayList(BANNER, headerAdapter.getData());
    }
}