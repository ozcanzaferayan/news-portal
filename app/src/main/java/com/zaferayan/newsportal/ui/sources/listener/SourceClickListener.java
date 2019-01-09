package com.zaferayan.newsportal.ui.sources.listener;

import android.content.Intent;
import android.view.View;
import com.zaferayan.newsportal.ui.sources.model.Source;
import com.zaferayan.newsportal.ui.topHeadlines.view.TopHeadlinesActivity;

public class SourceClickListener implements View.OnClickListener {
    private final Source source;

    public SourceClickListener(Source source) {
        this.source = source;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), TopHeadlinesActivity.class);
        intent.putExtra(TopHeadlinesActivity.EXTRA_SOURCE_ID, source.getId());
        intent.putExtra(TopHeadlinesActivity.EXTRA_SOURCE_NAME, source.getName());
        view.getContext().startActivity(intent);
    }
}
