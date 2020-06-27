package com.qmr.wanandroid.ui.home.adapter;

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
import com.qmr.base.image.ImageLoader;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.model.entity.TagsBean;
import com.qmr.wanandroid.ui.common.ArticleListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainArticleAdapter extends BaseRecyclerViewAdapter<MainArticleAdapter.Holder, ArticleBean> {

    private static final String TAG = "CollectionAdapter";

/*    public void setOnItemClickListener(OnItemClickListener<ArticleBean> l){
        listener = l;
    }*/

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.v(TAG, "onCreateViewHolder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainArticleAdapter.Holder holder, int position) {
        super.onBindViewHolder(holder, position);
        Log.v(TAG, "onBindViewHolder");

        final ArticleBean data = datalist.get(position);
        if (TextUtils.isEmpty(data.getEnvelopePic()))
            holder.ivImg.setVisibility(View.GONE);
        else {
            holder.ivImg.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(data.getEnvelopePic(), holder.ivImg);
        }

        holder.tvNew.setVisibility(data.isFresh() ? View.VISIBLE : View.GONE);
        //holder.tvTop.setVisibility(data.isFresh()?View.VISIBLE:View.GONE);
        holder.tvAuthor.setText(TextUtils.isEmpty(data.getAuthor()) ?
                holder.tvAuthor.getResources().getString(R.string.get_reprint, data.getShareUser())
                : holder.tvAuthor.getResources().getString(R.string.get_author, data.getAuthor()));
        holder.tvAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleListActivity.linkStart(v.getContext(), TextUtils.isEmpty(data.getAuthor()) ? data.getShareUser() : data.getAuthor());
            }
        });

        holder.tvChapterName.setText(data.getChapterName());
        holder.tvTitle.setText(Html.fromHtml(data.getTitle()));
        holder.tvTime.setText(data.getNiceShareDate());
        if (!TextUtils.isEmpty(data.getDesc())) {
            holder.tvDesc.setVisibility(View.VISIBLE);
            holder.tvDesc.setText(Html.fromHtml(data.getDesc()));
        } else
            holder.tvDesc.setVisibility(View.GONE);
        if (data.getTags() != null && data.getTags().size() != 0) {
            //holder.tvTag.setText(data.getTags().get(0).getName());
            StringBuilder sb = new StringBuilder();
            for (TagsBean tagsBean : data.getTags())
                sb.append(tagsBean.getName()).append("|");
            sb.deleteCharAt(sb.length() - 1);
            holder.tvTag.setText(sb);
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
