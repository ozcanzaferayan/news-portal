package com.zaferayan.newsportal.ui.topHeadlines.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.zaferayan.newsportal.R;
import com.zaferayan.newsportal.di.DependencyInjectorImpl;
import com.zaferayan.newsportal.ui.savedArticles.SavedArticlesActivity;
import com.zaferayan.newsportal.ui.topHeadlines.adapter.TopHeadlinesAdapter;
import com.zaferayan.newsportal.ui.topHeadlines.contract.TopHeadlinesContract;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;
import com.zaferayan.newsportal.ui.topHeadlines.presenter.TopHeadlinesPresenter;
import com.zaferayan.newsportal.ui.webview.WebViewActivity;

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
    String sourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_headlines);
        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();
        final String sourceName;
        if (bundle == null) {
            sourceId = EXTRA_SOURCE_ID_DEFAULT;
            sourceName = EXTRA_SOURCE_NAME_DEFAULT;
        } else {
            sourceId = bundle.getString(EXTRA_SOURCE_ID, EXTRA_SOURCE_ID_DEFAULT);
            sourceName = bundle.getString(EXTRA_SOURCE_NAME, EXTRA_SOURCE_NAME_DEFAULT);
        }
        if (actionBar != null) {
            actionBar.setTitle(sourceName);
        }
        recyclerView = findViewById(R.id.listTopHeadlines);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshSources = findViewById(R.id.swipeRefreshTopHeadlines);
        setPresenter(new TopHeadlinesPresenter(this, new DependencyInjectorImpl(this.getApplication()), sourceId));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_headlines, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_saved_articles:
                Intent intent = new Intent(this, SavedArticlesActivity.class);
                intent.putExtra(TopHeadlinesActivity.EXTRA_SOURCE_ID, sourceId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadList(List<Article> articles, List<Article> storedArticles, TopHeadlinesContract.Presenter presenter) {
        adapter = new TopHeadlinesAdapter(articles, storedArticles, presenter);
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
    public void navigateToWebView(Article article) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_URL, article.getUrl());
        intent.putExtra(WebViewActivity.EXTRA_TITLE, article.getTitle());
        startActivity(intent);
    }

    @Override
    public void setPresenter(TopHeadlinesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
