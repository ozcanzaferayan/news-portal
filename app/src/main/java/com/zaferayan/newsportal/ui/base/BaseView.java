package com.zaferayan.newsportal.ui.base;

import android.content.Context;

public interface BaseView<T> {
    void setPresenter(T presenter);

    Context getContext();
}
