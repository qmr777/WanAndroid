package com.qmr.wanandroid.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qmr.base.adapter.BaseRecyclerViewAdapter;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.youth.banner.Banner;

import java.util.List;

public class HeaderAdapter extends BaseRecyclerViewAdapter<HeaderAdapter.Holder, BannerBean> {

    private MainBannerAdapter bannerAdapter;

    public HeaderAdapter() {
        bannerAdapter = new MainBannerAdapter();
    }

    public HeaderAdapter(List<BannerBean> bannerBeanList) {
        super();
    }

    @Override
    public void resetData(List<BannerBean> bannerBeans) {
        bannerAdapter.setDatas(bannerBeans);
        bannerAdapter.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_banner, parent, false);
        Holder holder = new Holder(v);
        if (bannerAdapter != null)
            holder.banner.setAdapter(bannerAdapter);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        //doNothing
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    protected static class Holder extends RecyclerView.ViewHolder {

        Banner<BannerBean, MainBannerAdapter> banner;

        public Holder(@NonNull View itemView) {
            super(itemView);
            banner = (Banner<BannerBean, MainBannerAdapter>) itemView;
        }
    }
}
