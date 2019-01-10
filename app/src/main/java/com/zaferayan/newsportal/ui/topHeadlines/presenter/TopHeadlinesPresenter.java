package com.zaferayan.newsportal.ui.topHeadlines.presenter;

import android.support.annotation.NonNull;
import com.zaferayan.newsportal.di.DependencyInjector;
import com.zaferayan.newsportal.ui.topHeadlines.contract.TopHeadlinesContract;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;
import com.zaferayan.newsportal.ui.topHeadlines.model.TopHeadlinesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class TopHeadlinesPresenter extends TopHeadlinesContract.Presenter {
    private final DependencyInjector dependencyInjector;
    private TopHeadlinesContract.View view;

    public TopHeadlinesPresenter(TopHeadlinesContract.View view, DependencyInjector dependencyInjector) {
        this.view = view;
        this.dependencyInjector = dependencyInjector;
    }

    @Override
    protected void loadList(String sourceId) {
        final Call<TopHeadlinesResponse> responseCall = dependencyInjector.getNewsService().getTopHeadlines(sourceId);
        responseCall.enqueue(new Callback<TopHeadlinesResponse>() {
            @Override
            public void onResponse(@NonNull Call<TopHeadlinesResponse> call, @NonNull Response<TopHeadlinesResponse> response) {
                view.hideListLoading();
                if (response.code() != 200) {
                    String messageText = response.code() == 504
                            ? "Network not available"
                            : response.message();
                    view.showListError(new Exception(messageText));
                    return;
                }
                TopHeadlinesResponse sourcesResponse = response.body();
                if (sourcesResponse == null) return;
                List<Article> sources = sourcesResponse.getArticles();
                view.loadList(sources);
            }

            @Override
            public void onFailure(@NonNull Call<TopHeadlinesResponse> call, @NonNull Throwable t) {
                view.hideListLoading();
                view.showListError(new Exception(t));
            }
        });
    }

    @Override
    public void loadListWithProgressDialog(String sourceId) {
        view.showListLoading();
        loadList(sourceId);
    }

    @Override
    public void loadListFromSwipeRefresh(String sourceId) {
        loadList(sourceId);
    }

    @Override
    public void loadEmptyList() {
        view.loadEmptyList();
    }


    @Override
    public void onDestroy() {
        this.view = null;
    }
}
