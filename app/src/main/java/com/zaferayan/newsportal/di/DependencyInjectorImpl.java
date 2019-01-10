package com.zaferayan.newsportal.di;

import android.app.Application;
import com.zaferayan.newsportal.config.Constants;
import com.zaferayan.newsportal.data.repository.ArticleRepository;
import com.zaferayan.newsportal.network.NewsService;
import com.zaferayan.newsportal.network.interceptor.CacheInterceptorOffline;
import com.zaferayan.newsportal.network.interceptor.CacheInterceptorOnline;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;

public class DependencyInjectorImpl implements DependencyInjector {

    private final Application application;

    public DependencyInjectorImpl(Application application) {
        this.application = application;
    }

    @Override
    public NewsService getNewsService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(provideCache())
                .addInterceptor(new CacheInterceptorOnline(application))
                .addInterceptor(new CacheInterceptorOffline(application))
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new CacheInterceptorOnline(application))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(NewsService.class);
    }

    @Override
    public ArticleRepository getArticleRepository() {
        return new ArticleRepository(application);
    }

    private Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(application.getCacheDir(), "http-cache"),
                    Constants.NETWORK_CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }
}
