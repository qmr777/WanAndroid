package com.qmr.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.qmr.base.BuildConfig;
import com.qmr.base.R;
import com.qmr.base.image.ImageLoader;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
//import leakcanary.AppWatcher;

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;
    protected CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        disposable = new CompositeDisposable();
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);//夜间模式——跟随系统
        //debugToast(AppCompatDelegate.getDefaultNightMode() + "夜间模式");
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    protected void addDisposable(Disposable disposable) {
        this.disposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
//        AppWatcher.INSTANCE.getObjectWatcher().watch(this,"观察Aty是否正常销毁");
    }

    //要获取菜单重写这个
    protected int getMenuRes() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        if (getMenuRes() != 0) {
            getMenuInflater().inflate(getMenuRes(), menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void initView();

    protected void initToolbar(Toolbar toolbar, String title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_activity_start_in, R.anim.anim_activity_start_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_activity_exit_in, R.anim.anim_activity_exit_out);
    }

    protected void loadImage(String url, ImageView imageView) {
        //ImageLoader.loadImage(url,imageView);
        Glide.with(imageView).load(url)
                .placeholder(R.drawable.image_holder)
                .into(imageView);
    }

    protected void loadGif(String url, ImageView imageView) {
        ImageLoader.loadGif(url, imageView);
    }

    protected void shortToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void debugToast(CharSequence msg) {
        if (BuildConfig.DEBUG)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}