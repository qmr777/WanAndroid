package com.qmr.wanandroid.ui.tixi.navi;

import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.qmr.base.adapter.OnItemClickListener;
import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.ui.common.WebViewActivity;
import com.qmr.wanandroid.ui.tixi.FlexboxAdapter;

public class NaviFlexboxAdaper extends FlexboxAdapter<ArticleBean> {

    public static final String TAG = "NaviFlexboxAdaper";

    public NaviFlexboxAdaper(FlexboxLayout parent) {
        super(parent);
        setOnItemClickListener(new NaviFlexboxAdaper.Listener());
    }

    @Override
    public int getLayoutID() {
        return 0;
    }

    @Override
    protected void onBindView(ArticleBean bean, TextView chip, int position) {
        chip.setText(bean.getTitle());
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnItemClickListener() != null)
                    getOnItemClickListener().OnItemClick(position, bean);
            }
        });
        //chip.requestLayout();
        if (chip.getParent() == null)
            getParent().addView(chip);
    }

    public class Listener implements OnItemClickListener<ArticleBean> {

        @Override
        public void OnItemClick(int position, ArticleBean data) {
            WebViewActivity.linkStart(getParent().getContext(), data.getTitle(), data.getLink(), data.getId());
        }

    }
}
