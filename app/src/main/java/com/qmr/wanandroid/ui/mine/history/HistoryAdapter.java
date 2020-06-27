package com.qmr.wanandroid.ui.mine.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qmr.base.adapter.BaseRecyclerViewAdapter;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.HistoryBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends BaseRecyclerViewAdapter<HistoryAdapter.Holder, HistoryBean> {

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Holder h = new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        super.onBindViewHolder(holder, position);
        final HistoryBean data = getDataInPosition(position);
        holder.tvTime.setText(data.getTime());
        holder.tvTitle.setText(data.getTitle());
    }

    protected static class Holder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
