package com.zaferayan.newsportal.di;

import com.zaferayan.newsportal.network.NewsService;

public interface DependencyInjector {
    NewsService getNewsRepository();
}
