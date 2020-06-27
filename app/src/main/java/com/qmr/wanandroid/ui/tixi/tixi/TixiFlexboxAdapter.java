package com.qmr.wanandroid.ui.tixi.tixi;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.qmr.base.adapter.OnItemClickListener;
import com.qmr.wanandroid.model.entity.TixiBean;
import com.qmr.wanandroid.ui.common.ArticleListActivity;
import com.qmr.wanandroid.ui.tixi.FlexboxAdapter;

public class TixiFlexboxAdapter extends FlexboxAdapter<TixiBean.ChildrenBean> {

    public static final String TAG = "TixiChipGroupAdapter";

    public TixiFlexboxAdapter(FlexboxLayout parent) {
        super(parent);
        setOnItemClickListener(new Listener());
    }

    @Override
    public int getLayoutID() {
        return 0;
    }

    @Override
    protected void onBindView(TixiBean.ChildrenBean bean, TextView chip, int position) {
        chip.setText(bean.getName());
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

    public class Listener implements OnItemClickListener<TixiBean.ChildrenBean> {

        @Override
        public void OnItemClick(int position, TixiBean.ChildrenBean data) {
            Log.i(TAG, "OnItemClick: " + data.toString() + "id:" + data.getId());
            ArticleListActivity.linkStart(getParent().getContext(), data.getId(), data.getName());
        }

    }
}
