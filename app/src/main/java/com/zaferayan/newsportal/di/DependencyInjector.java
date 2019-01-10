package com.zaferayan.newsportal.di;

import com.zaferayan.newsportal.data.repository.ArticleRepository;
import com.zaferayan.newsportal.network.NewsService;

public interface DependencyInjector {
    NewsService getNewsService();

    ArticleRepository getArticleRepository();
}
