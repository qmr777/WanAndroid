package com.qmr.wanandroid.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class LoadErrorDialog extends DialogFragment {

    public static LoadErrorDialog getInstance() {
        return new LoadErrorDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Oops")
                .setMessage("加载失败了，看看网络连接")
                .setPositiveButton("ojbl", null);
        return builder.create();
    }

}
