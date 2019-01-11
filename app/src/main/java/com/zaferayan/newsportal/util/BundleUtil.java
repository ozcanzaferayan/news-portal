package com.zaferayan.newsportal.util;

import android.os.Bundle;
import com.zaferayan.newsportal.ui.topHeadlines.model.Source;

import static com.zaferayan.newsportal.config.Constants.*;

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
}
