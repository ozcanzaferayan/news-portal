package com.zaferayan.newsportal.util;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import com.zaferayan.newsportal.data.repository.ArticleRepository;
import com.zaferayan.newsportal.di.DependencyInjectorImpl;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;
import com.zaferayan.newsportal.ui.topHeadlines.model.TopHeadlinesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static com.zaferayan.newsportal.util.ToastHelper.showError;

public class NetworkUtil {
    public static void getArticles(final DependencyInjectorImpl injector, String sourceId, final ProgressDialog loading, final SwipeRefreshLayout swipeRefreshSources) {
        showLoading(loading);
        final Call<TopHeadlinesResponse> responseCall = injector.getNewsService().getTopHeadlines(sourceId);
        responseCall.enqueue(new Callback<TopHeadlinesResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopHeadlinesResponse> call, @NonNull Response<TopHeadlinesResponse> response) {
                hideLoading();
                if (response.code() != 200) {
                    String messageText = response.code() == 504
                            ? "Network not available"
                            : response.message();
                    showError(injector.application, messageText);
                    return;
                }
                TopHeadlinesResponse sourcesResponse = response.body();
                if (sourcesResponse == null) return;
                List<Article> articles = sourcesResponse.getArticles();

                ArticleRepository articleRepository = injector.getArticleRepository();
                articleRepository.insertAll(articles);
            }

            @Override
            public void onFailure(@NonNull Call<TopHeadlinesResponse> call, @NonNull Throwable t) {
                hideLoading();
                new Exception(t).printStackTrace();
                showError(injector.application, new Exception(t).getMessage());
            }

            private void hideLoading() {
                if (loading != null && swipeRefreshSources != null) {
                    loading.dismiss();
                    swipeRefreshSources.setRefreshing(false);
                }
            }
        });
    }

    private static void showLoading(ProgressDialog loading) {
        if (loading != null) loading.show();
    }
}
