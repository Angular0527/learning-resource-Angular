package com.retrofitdemo.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public final class DataBinder {
 
    private DataBinder() {
        //NO-OP
    }
 
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Picasso.with(context).load(url).into(imageView);
    }
}