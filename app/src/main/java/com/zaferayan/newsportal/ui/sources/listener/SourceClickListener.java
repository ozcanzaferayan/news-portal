package com.zaferayan.newsportal.ui.sources.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.zaferayan.newsportal.config.Constants;
import com.zaferayan.newsportal.ui.sources.model.Source;
import com.zaferayan.newsportal.ui.topHeadlines2.TopHeadlinesActivity2;

import static android.content.Context.MODE_PRIVATE;
import static com.zaferayan.newsportal.config.Constants.EXTRA_SOURCE_ID;
import static com.zaferayan.newsportal.config.Constants.EXTRA_SOURCE_NAME;

public class SourceClickListener implements View.OnClickListener {
    private final Source source;

    public SourceClickListener(Source source) {
        this.source = source;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, TopHeadlinesActivity2.class);
        intent.putExtra(EXTRA_SOURCE_ID, source.getId());
        intent.putExtra(EXTRA_SOURCE_NAME, source.getName());
        context.getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)
                .edit()
                .putString(Constants.PREF_SOURCE_ID, source.getId())
                .apply();
        view.getContext().startActivity(intent);
    }
}
