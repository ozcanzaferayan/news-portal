package com.zaferayan.newsportal.util;

import android.content.Intent;
import com.zaferayan.newsportal.ui.topHeadlines.model.Source;

import static com.zaferayan.newsportal.config.Constants.EXTRA_SOURCE_ID;
import static com.zaferayan.newsportal.config.Constants.EXTRA_SOURCE_NAME;

public class IntentUtil {
    public static Intent putExtras(Intent intent, Source source) {
        intent.putExtra(EXTRA_SOURCE_ID, source.getId());
        intent.putExtra(EXTRA_SOURCE_NAME, source.getName());
        return intent;
    }
}
