package com.zaferayan.newsportal.ui.sources.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.zaferayan.newsportal.R;
import com.zaferayan.newsportal.di.DependencyInjectorImpl;
import com.zaferayan.newsportal.ui.sources.adapter.SourcesAdapter;
import com.zaferayan.newsportal.ui.sources.contract.SourcesContract;
import com.zaferayan.newsportal.ui.sources.model.Source;
import com.zaferayan.newsportal.ui.sources.presenter.SourcesPresenter;

import java.util.ArrayList;
import java.util.List;

public class SourcesActivity extends AppCompatActivity implements SourcesContract.View {
    private SourcesContract.Presenter presenter;
    private RecyclerView recyclerView;
    private ProgressDialog loading;
    private SourcesAdapter adapter;
    private SwipeRefreshLayout swipeRefreshSources;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);
        recyclerView = findViewById(R.id.listSources);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshSources = findViewById(R.id.swipeRefreshSources);
        setPresenter(new SourcesPresenter(this, new DependencyInjectorImpl(this)));
        presenter.addHomeScreenShortcut();
        presenter.loadEmptyList();
        presenter.loadListWithProgressDialog();
        swipeRefreshSources.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadListFromSwipeRefresh();
            }
        });
    }

    @Override
    public void loadList(List<Source> sources) {
        adapter = new SourcesAdapter(sources);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadEmptyList() {
        adapter = new SourcesAdapter(new ArrayList<Source>());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showListLoading() {
        loading = new ProgressDialog(this);
        loading.setMessage(getResources().getString(R.string.sources_loading));
        loading.show();
    }

    @Override
    public void hideListLoading() {
        loading.dismiss();
        swipeRefreshSources.setRefreshing(false);
    }

    @Override
    public void showListError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(SourcesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}