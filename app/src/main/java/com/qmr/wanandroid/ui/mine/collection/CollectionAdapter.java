package com.qmr.wanandroid.ui.mine.collection;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qmr.base.adapter.BaseRecyclerViewAdapter;
import com.qmr.base.adapter.OnItemClickListener;
import com.qmr.base.image.ImageLoader;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.CollectionItemBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionAdapter extends BaseRecyclerViewAdapter<CollectionAdapter.Holder, CollectionItemBean> {

    private static final String TAG = "CollectionAdapter";

    public void setOnItemClickListener(OnItemClickListener<CollectionItemBean> l) {
        listener = l;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.Holder holder, int position) {
        final CollectionItemBean data = datalist.get(position);
        if (TextUtils.isEmpty(data.getEnvelopePic()))
            holder.ivImg.setVisibility(View.GONE);
        else {
            holder.ivImg.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(data.getEnvelopePic(), holder.ivImg);
        }

        holder.tvAuthor.setText(data.getAuthor());
        holder.tvChapterName.setText(data.getChapterName());
        holder.tvTitle.setText(Html.fromHtml(data.getTitle()));
        holder.tvTime.setText(data.getNiceDate());
        if (!TextUtils.isEmpty(data.getDesc())) {
            holder.tvDesc.setVisibility(View.VISIBLE);
            holder.tvDesc.setText(Html.fromHtml(data.getDesc()));
        } else
            holder.tvDesc.setVisibility(View.GONE);

        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: " + data.getTitle());
                    listener.OnItemClick(position, data);
                }
            });
        }
    }

    protected static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_new)
        TextView tvNew;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.ll_middle)
        LinearLayout llMiddle;
        @BindView(R.id.tv_top)
        TextView tvTop;
        @BindView(R.id.tv_chapter_name)
        TextView tvChapterName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
