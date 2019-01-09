package com.zaferayan.newsportal.ui.sources.presenter;

import android.support.annotation.NonNull;
import com.zaferayan.newsportal.di.DependencyInjector;
import com.zaferayan.newsportal.ui.sources.contract.SourcesContract;
import com.zaferayan.newsportal.ui.sources.model.Source;
import com.zaferayan.newsportal.ui.sources.model.SourcesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class SourcesPresenter extends SourcesContract.Presenter {

    private final DependencyInjector dependencyInjector;
    private SourcesContract.View view;

    public SourcesPresenter(SourcesContract.View view, DependencyInjector dependencyInjector) {
        this.view = view;
        this.dependencyInjector = dependencyInjector;
    }

    @Override
    protected void loadList() {
        final Call<SourcesResponse> sourcesResponseCall = dependencyInjector.getNewsRepository().getSources();
        sourcesResponseCall.enqueue(new Callback<SourcesResponse>() {
            @Override
            public void onResponse(@NonNull Call<SourcesResponse> call, @NonNull Response<SourcesResponse> response) {
                view.hideListLoading();
                if (response.code() != 200) {
                    String messageText = response.code() == 504
                            ? "Network not available"
                            : response.message();
                    view.showListError(new Exception(messageText));
                    return;
                }
                SourcesResponse sourcesResponse = response.body();
                if (sourcesResponse == null) return;
                List<Source> sources = sourcesResponse.getSources();
                view.loadList(sources);
            }

            @Override
            public void onFailure(@NonNull Call<SourcesResponse> call, @NonNull Throwable t) {
                view.hideListLoading();
                view.showListError(new Exception(t));
            }
        });
    }

    @Override
    public void loadListWithProgressDialog() {
        view.showListLoading();
        loadList();
    }

    @Override
    public void loadListFromSwipeRefresh() {
        loadList();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
