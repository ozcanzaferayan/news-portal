package com.zaferayan.newsportal.ui.topHeadlines.listener;

import android.content.Intent;
import android.view.View;
import com.zaferayan.newsportal.ui.topHeadlines.view.TopHeadlinesActivity;

import static com.zaferayan.newsportal.config.Constants.EXTRA_SOURCE_ID;

public class ArticleClickListener implements View.OnClickListener {
    private final String sourceId;

    public ArticleClickListener(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), TopHeadlinesActivity.class);
        intent.putExtra(EXTRA_SOURCE_ID, sourceId);
        view.getContext().startActivity(intent);
    }
}
