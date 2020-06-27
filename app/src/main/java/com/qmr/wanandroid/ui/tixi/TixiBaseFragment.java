package com.qmr.wanandroid.ui.tixi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.qmr.base.BaseFragment;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.ui.tixi.navi.NaviFragment;
import com.qmr.wanandroid.ui.tixi.tixi.TixiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 知识体系fragment
 * 最外层的
 */
public class TixiBaseFragment extends BaseFragment {

    @BindView(R.id.tl)
    TabLayout tl;
    @BindView(R.id.vp)
    ViewPager2 vp;

    private TixiFragment tixiFragment;
    private NaviFragment naviFragment;

    public TixiBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_tixi_root;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_tixi_root, container, false);
            ButterKnife.bind(this, contentView);
            initView();
        }
        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        tixiFragment = new TixiFragment();
        naviFragment = new NaviFragment();
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(tixiFragment);
        fragmentList.add(naviFragment);
        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return fragmentList.size();
            }
        };
        vp.setAdapter(adapter);
        new TabLayoutMediator(tl, vp, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(fragmentList.get(position).getTitle());
            }
        }).attach();
    }

    private void initData() {

    }
}