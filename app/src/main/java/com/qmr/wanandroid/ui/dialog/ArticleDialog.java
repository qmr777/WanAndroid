package com.qmr.wanandroid.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.qmr.base.util.DateUtil;
import com.qmr.wanandroid.model.database.DatabaseManager;
import com.qmr.wanandroid.model.entity.ArticleBean;
import com.qmr.wanandroid.model.entity.ReadLaterBean;
import com.qmr.wanandroid.model.entity.TopArticleBean;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.mine.MineApi;
import com.qmr.wanandroid.ui.common.ArticleListActivity;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArticleDialog extends DialogFragment {

    public static final String TAG = "ArticleDialog";

    private TopArticleBean topArticleBean;
    private ArticleBean articleBean;
    private String[] strings = new String[3];
    private int flag = 0;//1是top 2是普通

    public ArticleDialog(TopArticleBean bean) {
        super();
        flag = 1;
        this.topArticleBean = bean;
        strings[0] = "收藏";
        strings[1] = "稍后在看";
        strings[2] = TextUtils.isEmpty(bean.getAuthor()) ? "查看转载者：" + bean.getShareUser() : "查看作者：" + bean.getAuthor();
    }

    public ArticleDialog(ArticleBean bean) {
        super();
        flag = 2;
        this.articleBean = bean;
        strings[0] = bean.isCollect() ? "取消收藏" : "收藏";
        strings[1] = "稍后在看";
        strings[2] = TextUtils.isEmpty(bean.getAuthor()) ? "查看转载者：" + bean.getShareUser() : "查看作者：" + bean.getAuthor();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        if (flag == 1) {
            builder.setTitle(topArticleBean.getTitle())
                    .setItems(strings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    collection(topArticleBean.getId(), topArticleBean.isCollect());
                                    topArticleBean.setCollect(!topArticleBean.isCollect());
                                    break;
                                case 1:
                                    readLater();
                                    break;
                                case 2:
                                    showAuthor();
                                    break;
                            }
                        }
                    });
        } else if (flag == 2) {
            builder.setTitle(articleBean.getTitle())
                    .setItems(strings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    collection(articleBean.getId(), articleBean.isCollect());
                                    articleBean.setCollect(!articleBean.isCollect());
                                    break;
                                case 1:
                                    readLater();
                                    break;
                                case 2:
                                    showAuthor();
                            }
                        }
                    });
        }

        return builder.create();
    }

    private void collection(int id, boolean isCollect) {

        MineApi api = RequestManager.getInstance().getService(MineApi.class);
        if (isCollect) {//已经收藏 要取消
            api.removeCollections(id).subscribeOn(Schedulers.io()).subscribe();
        } else {
            api.addCollection(id).subscribeOn(Schedulers.io()).subscribe();
        }

    }

    private void readLater() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                ReadLaterBean bean = new ReadLaterBean();
                bean.setTime(DateUtil.YYYYMMDD(System.currentTimeMillis()));
                if (flag == 1) {
                    bean.setTitle(topArticleBean.getTitle());
                    bean.setLink(topArticleBean.getLink());
                } else if (flag == 2) {
                    bean.setTitle(articleBean.getTitle());
                    bean.setLink(articleBean.getLink());
                }
                DatabaseManager.getInstance().getReadLaterDao().insert(bean);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).subscribe();

    }

    private void showAuthor() {
        if (flag == 1)
            ArticleListActivity.linkStart(requireActivity(),
                    TextUtils.isEmpty(topArticleBean.getAuthor()) ? topArticleBean.getShareUser() : topArticleBean.getAuthor());
        else if (flag == 2)
            ArticleListActivity.linkStart(requireActivity(),
                    TextUtils.isEmpty(articleBean.getAuthor()) ? articleBean.getShareUser() : articleBean.getAuthor());
    }


}
