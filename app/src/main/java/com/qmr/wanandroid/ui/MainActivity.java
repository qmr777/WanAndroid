package com.qmr.wanandroid.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.qmr.base.BaseFragment;
import com.qmr.base.activity.BaseActivity;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.ui.common.SearchActivity;
import com.qmr.wanandroid.ui.home.HomeFragment;
import com.qmr.wanandroid.ui.mine.MineFragment;
import com.qmr.wanandroid.ui.tixi.navi.NaviFragment;
import com.qmr.wanandroid.ui.tixi.tixi.TixiFragment;
import com.qmr.wanandroid.ui.wenda.WendaFragment;
import com.qmr.wanandroid.util.LoginUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_main)
    ViewPager2 vp_main;
    @BindView(R.id.bnv)
    BottomNavigationView bnv;

    SearchView mSearchView;

    List<BaseFragment> fragmentList = new ArrayList<>();
    String[] titles = {"WanAndroid", "体系", "导航", "问答", "我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //if (hasFocus)
    }

    @Override
    protected void initView() {
        toolbar.setTitle("WanAndroid");
        toolbar.inflateMenu(R.menu.toolbar_search);
        initSearch(toolbar.getMenu());
/*
        toolbar.inflateMenu(R.menu.toolbar_main_frag);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_search:
                        startActivity(new Intent(MainActivity.this, SearchActivity.class));
                        break;
                }
                return true;
            }
        });*/

        vp_main.setAdapter(new ViewPagerAdapter(this));
        vp_main.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                toolbar.setTitle(titles[position]);
                if (position == 4)
                    toolbar.setTitle(TextUtils.isEmpty(LoginUtil.username) && LoginUtil.getLoginStatue() ?
                            titles[position] : LoginUtil.username);
                bnv.getMenu().getItem(position).setChecked(true);
//                toolbar.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        fragmentList.get(position).scrollToTop();
//                    }
//                });
            }
        });

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_navi_main:
                        vp_main.setCurrentItem(0);
                        break;
                    case R.id.id_tixi:
                        vp_main.setCurrentItem(1);
                        break;
                    case R.id.id_navigation:
                        vp_main.setCurrentItem(2);
                        break;
                    case R.id.id_wenda:
                        vp_main.setCurrentItem(3);
                        break;
                    case R.id.id_mine:
                        vp_main.setCurrentItem(4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

    private void changePage(int position) {
        vp_main.setCurrentItem(position);
    }

    private void initSearch(Menu menu) {
        //getMenuInflater().inflate(R.menu.toolbar_search,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchView.setQuery(null, false);
                mSearchView.clearFocus();
                mSearchView.onActionViewCollapsed();
                SearchActivity.linkStart(MainActivity.this, s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
/*            fragmentList.add(new MainFragment());
            fragmentList.add(new TixiFragment());
            fragmentList.add(new NaviFragment());
            fragmentList.add(new WendaFragment());
            fragmentList.add(new MineFragment());*/
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            BaseFragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new TixiFragment();
                    break;
                case 2:
                    fragment = new NaviFragment();
                    break;
                case 3:
                    fragment = new WendaFragment();
                    break;
                case 4:
                    fragment = new MineFragment();
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return 5;
        }

    }

}