package com.zaferayan.newsportal.ui.topHeadlines.model;

import android.arch.persistence.room.TypeConverter;

public class SourceConverter {
    @TypeConverter
    public static Source fromString(String id) {
        return id == null ? null : new Source(id);
    }

    @TypeConverter
    public static String sourceToString(Source source) {
        return source == null ? null : source.getId();
    }
}
