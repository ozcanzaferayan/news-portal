package com.zaferayan.newsportal.util;

import android.os.Bundle;
import com.zaferayan.newsportal.ui.topHeadlines.model.Source;
import com.zaferayan.newsportal.ui.webview.model.WebViewExtras;

import static com.zaferayan.newsportal.config.Constants.*;
import static com.zaferayan.newsportal.ui.webview.WebViewActivity.*;

public class BundleUtil {
    public static Source getSource(Bundle bundle) {
        String sourceId;
        String sourceName;
        if (bundle == null) {
            sourceId = EXTRA_SOURCE_ID_DEFAULT;
            sourceName = EXTRA_SOURCE_NAME_DEFAULT;
        } else {
            sourceId = bundle.getString(EXTRA_SOURCE_ID, EXTRA_SOURCE_ID_DEFAULT);
            sourceName = bundle.getString(EXTRA_SOURCE_NAME, EXTRA_SOURCE_NAME_DEFAULT);
        }
        return new Source(sourceId, sourceName);
    }

    public static WebViewExtras getWebViewExtras(Bundle bundle) {
        String url;
        String title;
        if (bundle == null) {
            url = EXTRA_URL_DEFAULT;
            title = EXTRA_TITLE_DEFAULT;
        } else {
            url = bundle.getString(EXTRA_URL, EXTRA_URL_DEFAULT);
            title = bundle.getString(EXTRA_TITLE, EXTRA_TITLE_DEFAULT);
        }
        return new WebViewExtras(url, title);
    }
}
