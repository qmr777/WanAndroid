package com.qmr.wanandroid.ui.wenda;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qmr.base.adapter.BaseRecyclerViewAdapter;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.WendaBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WendaAdapter extends BaseRecyclerViewAdapter<WendaAdapter.Holder, WendaBean.DatasBean> {

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WendaAdapter.Holder holder, int position) {
        super.onBindViewHolder(holder, position);
        final WendaBean.DatasBean bean = getDataInPosition(position);
        holder.tvTop.setVisibility(View.GONE);
        holder.ivImg.setVisibility(View.GONE);
        holder.tvAuthor.setText(bean.getAuthor());
        holder.tvNew.setVisibility(bean.isFresh() ? View.VISIBLE : View.GONE);
        holder.tvTitle.setText(Html.fromHtml(bean.getTitle()));
        holder.tvDesc.setText(Html.fromHtml(bean.getDesc()));
        holder.tvChapterName.setText(bean.getChapterName());
        holder.tvTime.setText(bean.getNiceDate());
        holder.tvTag.setText("问答");

    }

    protected static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_top)
        TextView tvTop;
        @BindView(R.id.tv_new)
        TextView tvNew;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rl_top)
        RelativeLayout rlTop;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_chapter_name)
        TextView tvChapterName;
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.rl_article)
        RelativeLayout rlArticle;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
