package com.maksg.mobilebutler.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

public class TabFragment extends Fragment {

    protected Context context;
    protected View view;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}