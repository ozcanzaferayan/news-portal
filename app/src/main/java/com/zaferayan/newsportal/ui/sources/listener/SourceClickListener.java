package com.zaferayan.newsportal.ui.sources.listener;

import android.content.Intent;
import android.view.View;
import com.zaferayan.newsportal.ui.topHeadlines.TopHeadlinesActivity;

public class SourceClickListener implements View.OnClickListener {
    private final String sourceId;

    public SourceClickListener(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), TopHeadlinesActivity.class);
        intent.putExtra("SOURCE_ID", sourceId);
        view.getContext().startActivity(intent);
    }
}
