package com.qmr.wanandroid.ui.common;

import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.qmr.wanandroid.model.entity.HotKeyBean;
import com.qmr.wanandroid.ui.tixi.FlexboxAdapter;

/**
 * 搜索页 热词 adapter
 */

public class HotkeyAdapter extends FlexboxAdapter<HotKeyBean> {


    public static final String TAG = "HotkeyAdapter";

    public HotkeyAdapter(FlexboxLayout parent) {
        super(parent);
    }

    @Override
    public int getLayoutID() {
        return 0;
    }

    @Override
    protected void onBindView(HotKeyBean hotKeyBean, TextView tv, int position) {
        tv.setText(hotKeyBean.getName());
        if (getOnItemClickListener() != null)
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnItemClickListener().OnItemClick(position, hotKeyBean);
                }
            });
        if (tv.getParent() == null)
            getParent().addView(tv);
    }


}
