package com.qmr.wanandroid.ui.tixi;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.qmr.base.adapter.OnItemClickListener;
import com.qmr.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责回收利用view
 *
 * @param <T>
 */
public abstract class FlexboxAdapter<T> {

    List<T> datas = new ArrayList<>();
    List<TextView> chipList = new ArrayList<>(20);
    FlexboxLayout parent;
    private OnItemClickListener<T> l;

    public FlexboxAdapter(FlexboxLayout parent) {
        this.parent = parent;
    }

    public FlexboxLayout getParent() {
        return parent;
    }

    public void setData(List<T> datalist) {
        if (datalist == null)
            return;
        datas.clear();
        datas.addAll(datalist);
        notifyDateSetChange();
    }

    public void bindView(FlexboxLayout parent) {
        if (parent != null && !(this.parent == parent)) {
            this.parent = parent;
        }
        notifyDateSetChange();
    }

    protected OnItemClickListener<T> getOnItemClickListener() {
        return l;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        l = listener;
    }

    public T getData(int position) {
        return datas.get(position);
    }

    public void notifyDateSetChange() {
        for (TextView tv : chipList)
            tv.setVisibility(View.GONE);
        for (int i = 0; i < datas.size(); i++) {
            TextView c = getTextView(i);
            c.setVisibility(View.VISIBLE);
            onBindView(datas.get(i), c, i);
        }
        parent.requestLayout();
    }


    public abstract int getLayoutID();

    protected TextView onCreateView(FlexboxLayout parent) {
        TextView view = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chip, parent, false);
        return view;
    }

    protected TextView getTextView(int position) {
        TextView c;
        if (position < chipList.size()) {
            c = chipList.get(position);
            //parent.removeView(c);
        } else {
            c = onCreateView(parent);
            chipList.add(c);
        }
        return c;
    }

    protected abstract void onBindView(T t, TextView tv, int position);

}
