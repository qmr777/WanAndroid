package com.qmr.wanandroid.ui.mine.collection;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class RemoveCollectionsDialog extends DialogFragment {

    String title;
    int id;

    public RemoveCollectionsDialog(String title, int id) {
        this.title = title;
        this.id = id;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("取消收藏")
                .setMessage("确定要取消收藏文章 " + title + " 吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("取消", null);
        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }

}
