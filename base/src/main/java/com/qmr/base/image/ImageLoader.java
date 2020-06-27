package com.qmr.base.image;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageLoader {

    private static Context context;

    public static void init(Context c) {
        context = c;
    }

    public static void loadImage(String url, ImageView imageView) {

        if (context != null) {
            Glide.with(context)
                    .load(url)
//                .placeholder()
//                .error()
//                .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView);
        } /*else {
            Glide.with(imageView.getContext())
                    .load(url)
//                .placeholder()
//                .error()
//                .skipMemoryCache(false)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }*/
    }

    public static void loadGif(String url, ImageView imageView) {

        if (imageView.getContext() instanceof Activity &&
                ((Activity) imageView.getContext()).isFinishing()) return;

        Glide.with(imageView)
                .asGif()
                .load(url)
                .into(imageView);
    }


}
