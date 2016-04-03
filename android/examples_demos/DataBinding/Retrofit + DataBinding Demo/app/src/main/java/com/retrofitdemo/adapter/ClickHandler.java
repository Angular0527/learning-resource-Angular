package com.retrofitdemo.adapter;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.retrofitdemo.api.response.StackOverflowUser;

public class ClickHandler {
    private final StackOverflowUser.Item item;

    public ClickHandler(StackOverflowUser.Item item) {
        this.item = item;
    }

    public void onClick(View view) {
        Snackbar.make(view, item.getDisplayName() + " Selected", Snackbar.LENGTH_LONG).show();
    }
}