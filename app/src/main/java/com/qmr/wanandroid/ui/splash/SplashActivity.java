package com.qmr.wanandroid.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.qmr.wanandroid.BuildConfig;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.BingImageEntity;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.main.MainApi;
import com.qmr.wanandroid.ui.MainActivity;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    public static final int DELAY = 1000;
    public static final String TAG = "SplashActivity";

    //透明状态栏&底栏 全屏
    int flag = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;


    //真全屏 彻底隐藏状态栏底栏
    int flag2 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_wan)
    TextView tvWan;

    CompositeDisposable cd = new CompositeDisposable();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().getDecorView().setFitsSystemWindows(true);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        tvWan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });

        double rand = Math.random();
        if (rand < BuildConfig.van_probility) {
            tvWan.setText("Van♂Android");
            tvDesc.setText("Do you like ♂ VanAndroid?");
            int[] ids = {R.drawable.ddf1, R.drawable.ddf2, R.drawable.ddf3, R.drawable.ddf4};
            Random r = new Random();

            tvDesc.setVisibility(View.VISIBLE);
            Glide.with(this).load(ids[r.nextInt(4)]).into(iv);
            Disposable d = Observable.timer(5, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Throwable {
                            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    });
            cd.add(d);
        } else {

            //getWindow().getDecorView().setSystemUiVisibility(flag);

            RequestManager.getInstance().getService(MainApi.class)
                    .splashImage()
                    .map(new Function<List<BingImageEntity>, BingImageEntity>() {
                        @Override
                        public BingImageEntity apply(List<BingImageEntity> bingImageEntities) throws Throwable {
                            //int rand = (int) (Math.random() * 100 % bingImageEntities.size());
                            int rand = new Random().nextInt(bingImageEntities.size() - 1);
                            return bingImageEntities.get(rand);
                        }
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BingImageEntity>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            cd.add(d);
                        }

                        @Override
                        public void onNext(@NonNull BingImageEntity bingImageEntity) {
                            //Toast.makeText(SplashActivity.this,bingImageEntity.getCDNUrl(),Toast.LENGTH_SHORT).show();
                            Glide.with(SplashActivity.this)
                                    .load("https:" + bingImageEntity.getCDNUrl())
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .centerCrop()
                                    .into(iv);
                            tvDesc.setText(bingImageEntity.getCopyright());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Toast.makeText(SplashActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e(TAG, e.getMessage(), e);
                            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            Disposable disposable = Observable.interval(1, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Throwable {
                            if (aLong >= 3000) {
                                cd.dispose();
                                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            } else if (aLong >= 1000) {
                                tvDesc.setAlpha(getAlpha(aLong));
                            }
                        }
                    });
            cd.add(disposable);
        }

    }

    @Override
    public void finish() {
        super.finish();
        cd.dispose();
    }


    private float getAlpha(long ms) {
        if (ms < 1000) return 0;
        else if (ms < 2000) return (ms - 1000) / 1000f;
        else return 1f;
    }
}