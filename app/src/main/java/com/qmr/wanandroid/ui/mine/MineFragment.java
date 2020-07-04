package com.qmr.wanandroid.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.qmr.base.BaseFragment;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.ui.mine.collection.CollectionActivity;
import com.qmr.wanandroid.ui.mine.history.HistoryActivity;
import com.qmr.wanandroid.ui.mine.history.ReadLaterActivity;
import com.qmr.wanandroid.ui.mine.login.LoginActivity;
import com.qmr.wanandroid.util.LoginUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.ll_read_record)
    LinearLayout llReadRecord;
    @BindView(R.id.ll_coin)
    LinearLayout llCoin;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.ll_read_later)
    LinearLayout llReadLater;
    @BindView(R.id.ll_about_me)
    LinearLayout llAboutMe;
    @BindView(R.id.pre_layout)
    RelativeLayout rl_pre;

    public MineFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_mine, container, false);
            ButterKnife.bind(this, contentView);
        }
        setUI();
        return contentView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        llReadRecord.setOnClickListener(this);
        llCollect.setOnClickListener(this);
        rl_pre.setOnClickListener(this);
        llAboutMe.setOnClickListener(this);
        llReadLater.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_read_record:
                startActivity(new Intent(getActivity(), HistoryActivity.class));
                break;
            case R.id.ll_collect:
                startActivity(new Intent(getActivity(), CollectionActivity.class));
                break;
            case R.id.pre_layout:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), 1);
                break;
            case R.id.ll_read_later:
                startActivity(new Intent(requireActivity(), ReadLaterActivity.class));
                break;
            case R.id.ll_about_me:
                aboutMe();
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            loginSuccess();
        }
    }

    private void setUI() {
        if (LoginUtil.getLoginStatue()) {
            rl_pre.setVisibility(View.GONE);
        }
    }

    private void loginSuccess() {
        setUI();
        //rl_pre.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutID() {
        return 0;
    }

    @Override
    public String getTitle() {
        return TextUtils.isEmpty(LoginUtil.username) ? "我的" : LoginUtil.username;
    }

    private void aboutMe() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle("关于作者")
                .setMessage(getString(R.string.about_author))
                .setPositiveButton("ok", null);

        builder.show();

    }
}