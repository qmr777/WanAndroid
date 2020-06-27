package com.qmr.wanandroid.ui.tixi.tixi;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayout;
import com.qmr.base.adapter.BaseRecyclerViewAdapter;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.TixiBean;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TixiRecyclerViewAdapter extends
        BaseRecyclerViewAdapter<TixiRecyclerViewAdapter.Holder, TixiBean> {

    public static final String TAG = "TixiRecyclerViewAdapter";

    Map<View, TixiFlexboxAdapter> flexboxAdapterMap = new ArrayMap<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flow_layout, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        super.onBindViewHolder(holder, position);
        final TixiBean bean = datalist.get(position);
        holder.tv_title.setText(bean.getName());
        final TixiFlexboxAdapter flexboxAdapter = flexboxAdapterMap.get(holder.itemView);
        if (flexboxAdapter == null) {
            TixiFlexboxAdapter adapter = new TixiFlexboxAdapter(holder.fbl);
            flexboxAdapterMap.put(holder.itemView, adapter);
            adapter.setData(bean.getChildren());
            adapter.bindView(holder.fbl);
            adapter.notifyDateSetChange();
        } else {
            StringBuilder sb = new StringBuilder();
            for (TixiBean.ChildrenBean b : bean.getChildren())
                sb.append(b.getName()).append(" ");
            flexboxAdapter.setData(bean.getChildren());
            flexboxAdapter.bindView(holder.fbl);
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
