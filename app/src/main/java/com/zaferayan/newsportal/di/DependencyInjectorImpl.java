package com.zaferayan.newsportal.di;

import android.content.Context;
import com.zaferayan.newsportal.config.Constants;
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

    private final Context context;

    public DependencyInjectorImpl(Context context) {
        this.context = context;
    }

    @Override
    public NewsService getNewsRepository() {
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(provideCache())
                .addInterceptor(new CacheInterceptorOnline(context))
                .addInterceptor(new CacheInterceptorOffline(context))
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new CacheInterceptorOnline(context))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(NewsService.class);
    }

    private Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), "http-cache"),
                    Constants.NETWORK_CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }
}
