package com.zaferayan.newsportal.ui.topHeadlines.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.zaferayan.newsportal.R;
import com.zaferayan.newsportal.di.DependencyInjectorImpl;
import com.zaferayan.newsportal.ui.topHeadlines.adapter.TopHeadlinesAdapter;
import com.zaferayan.newsportal.ui.topHeadlines.contract.TopHeadlinesContract;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;
import com.zaferayan.newsportal.ui.topHeadlines.presenter.TopHeadlinesPresenter;

import java.util.List;

public class TopHeadlinesActivity extends AppCompatActivity implements TopHeadlinesContract.View {
    public final static String EXTRA_SOURCE_ID = "SOURCE_ID";
    public final static String EXTRA_SOURCE_ID_DEFAULT = "abc-news";
    public final static String EXTRA_SOURCE_NAME = "SOURCE_NAME";
    public final static String EXTRA_SOURCE_NAME_DEFAULT = "ABC News";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshSources;
    private TopHeadlinesContract.Presenter presenter;
    private TopHeadlinesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_headlines);
        final String sourceId = getIntent().getExtras().getString(EXTRA_SOURCE_ID, EXTRA_SOURCE_ID_DEFAULT);
        final String sourceName = getIntent().getExtras().getString(EXTRA_SOURCE_NAME, EXTRA_SOURCE_NAME_DEFAULT);
        getSupportActionBar().setTitle(sourceName);
        recyclerView = findViewById(R.id.listTopHeadlines);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshSources = findViewById(R.id.swipeRefreshTopHeadlines);
        setPresenter(new TopHeadlinesPresenter(this, new DependencyInjectorImpl(this)));
        presenter.loadEmptyList();
        presenter.loadListWithProgressDialog(sourceId);
        swipeRefreshSources.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadListFromSwipeRefresh(sourceId);
            }
        });
    }

    @Override
    public void loadList(List<Article> articles) {
        adapter = new TopHeadlinesAdapter(articles);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadEmptyList() {

    }

    @Override
    public void showListLoading() {

    }

    @Override
    public void hideListLoading() {

    }

    @Override
    public void showListError(Exception e) {

    }

    @Override
    public void setPresenter(TopHeadlinesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return null;
    }
}
