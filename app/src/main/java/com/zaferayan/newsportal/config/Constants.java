package com.zaferayan.newsportal.config;

public class Constants {
    public static final long NETWORK_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    public static final int NETWORK_MAX_AGE_MINUTES = 5;
    public static final int NETWORK_MAX_STALE_DAYS = 7;
    public static final long INTERVAL_SERVICE = 1000 * 60; // 1 Min
    public static final String PREF_SOURCE_ID = "PREF_SOURCE_ID";
    public static final String PREF_NAME = "PREF_NAME";
    public final static String EXTRA_SOURCE_ID = "SOURCE_ID";
    public final static String EXTRA_SOURCE_ID_DEFAULT = "abc-news";
    public final static String EXTRA_SOURCE_NAME = "SOURCE_NAME";
    public final static String EXTRA_SOURCE_NAME_DEFAULT = "ABC News";

    public static final long INTERVAL_SERVICE_DELAY = 0;
}
