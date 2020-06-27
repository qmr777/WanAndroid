package com.qmr.wanandroid.ui.home.adapter;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qmr.base.adapter.OnItemClickListener;
import com.qmr.base.image.ImageLoader;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.BannerBean;
import com.qmr.wanandroid.ui.common.WebViewActivity;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

public class MainBannerAdapter extends com.youth.banner.adapter.BannerAdapter<BannerBean, MainBannerAdapter.Holder> {

    public static final String TAG = "MainBannerAdapter";

    private OnItemClickListener<BannerBean> listener;

    public MainBannerAdapter(List<BannerBean> datas) {
        super(datas);
    }

    public MainBannerAdapter() {
        super(null);
    }

    @Override
    public Holder onCreateHolder(ViewGroup parent, int viewType) {
        //return new SearchResultBean(parent.);
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        setOnBannerListener(new OnBannerListener<BannerBean>() {
            @Override
            public void OnBannerClick(BannerBean data, int position) {
                WebViewActivity.linkStart(parent.getContext(), data.getTitle(), data.getUrl(), data.getId());
            }
        });
        return new Holder(item);
    }

    @Override
    public void onBindView(Holder holder, BannerBean data, final int position, int size) {
        Log.i(TAG, "onBindView: " + position);
        final BannerBean bean = getData(position);
        ImageLoader.loadImage(bean.getImagePath(), holder.imageView);
        holder.textView.setText(Html.fromHtml(bean.getTitle()));

        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(position, bean);
                }
            });
        }


    }

    public void setOnItemClickListener(OnItemClickListener<BannerBean> listener) {
        this.listener = listener;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_bg);
            textView = itemView.findViewById(R.id.tv_title);
        }
    }
}
