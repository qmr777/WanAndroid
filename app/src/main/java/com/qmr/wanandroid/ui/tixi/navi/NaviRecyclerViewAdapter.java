package com.qmr.wanandroid.ui.tixi.navi;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.qmr.base.adapter.BaseRecyclerViewAdapter;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.NaviBean;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NaviRecyclerViewAdapter extends BaseRecyclerViewAdapter<NaviRecyclerViewAdapter.Holder, NaviBean> {

    public static final String TAG = "TixiRecyclerViewAdapter";

    Map<View, NaviFlexboxAdaper> flexboxAdapterMap = new ArrayMap<>();


    @NonNull
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flow_layout, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        super.onBindViewHolder(holder, position);
        final NaviBean bean = datalist.get(position);
        Log.w(TAG, "onBindViewHolder: position " + bean.getName());
        holder.tv_title.setText(bean.getName());
        final NaviFlexboxAdaper flexboxAdapter = flexboxAdapterMap.get(holder.itemView);
        if (flexboxAdapter == null) {
            Log.w(TAG, "onBindViewHolder:createAdapter position" + position);
            NaviFlexboxAdaper adapter = new NaviFlexboxAdaper(holder.fbl);
            flexboxAdapterMap.put(holder.itemView, adapter);
            adapter.setData(bean.getArticles());
            adapter.bindView(holder.fbl);
            adapter.notifyDateSetChange();
        } else {
            StringBuilder sb = new StringBuilder();

            flexboxAdapter.setData(bean.getArticles());
            flexboxAdapter.bindView(holder.fbl);
            //Objects.requireNonNull(map.get(bean.getCourseId())).bindView(holder.fbl);
        }
    }

    protected static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.cg)
        FlexboxLayout fbl;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
