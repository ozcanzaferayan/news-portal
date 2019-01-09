package com.zaferayan.newsportal.ui.topHeadlines.listener;

import android.content.Intent;
import android.view.View;
import com.zaferayan.newsportal.ui.topHeadlines.view.TopHeadlinesActivity;

public class ArticleClickListener implements View.OnClickListener {
    private final String sourceId;

    public ArticleClickListener(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), TopHeadlinesActivity.class);
        intent.putExtra(TopHeadlinesActivity.EXTRA_SOURCE_ID, sourceId);
        view.getContext().startActivity(intent);
    }
}
