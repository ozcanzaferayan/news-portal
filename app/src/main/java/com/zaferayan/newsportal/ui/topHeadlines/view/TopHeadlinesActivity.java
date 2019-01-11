package com.zaferayan.newsportal.ui.topHeadlines.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.zaferayan.newsportal.R;
import com.zaferayan.newsportal.di.DependencyInjectorImpl;
import com.zaferayan.newsportal.service.UpdaterService;
import com.zaferayan.newsportal.ui.savedArticles.SavedArticlesActivity;
import com.zaferayan.newsportal.ui.topHeadlines.adapter.ArticleRecyclerViewAdapter;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;
import com.zaferayan.newsportal.ui.topHeadlines.model.Source;
import com.zaferayan.newsportal.ui.topHeadlines.viewModel.ArticleViewModel;
import com.zaferayan.newsportal.util.*;

import java.util.ArrayList;
import java.util.List;

public class TopHeadlinesActivity extends AppCompatActivity {

    private com.zaferayan.newsportal.ui.topHeadlines.viewModel.ArticleViewModel articleViewModel;
    private ArticleRecyclerViewAdapter mWordRecyclerViewAdapter;
    private RecyclerView rv;
    private ProgressDialog loading;
    private SwipeRefreshLayout swipeRefreshSources;
    private DependencyInjectorImpl injector;
    private List<Article> storedArticles = new ArrayList<>();
    private Source source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_headlines);
        source = BundleUtil.getSource(getIntent().getExtras());
        ActionBarUtil.setTitle(getSupportActionBar(), source.getName());
        injector = new DependencyInjectorImpl(getApplication());
        findViews();
        initLoadingIndicators();
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        mWordRecyclerViewAdapter = new ArticleRecyclerViewAdapter(this, articleViewModel);
        rv.setAdapter(mWordRecyclerViewAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ServiceUtil.startService(getApplication(), UpdaterService.class);
        articleViewModel.getArticlesBySource(source.getId()).observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (storedArticles.size() == 0 || storedArticles.size() != articles.size()) {
                    storedArticles = articles;
                    NetworkUtil.getArticles(injector, source.getId(), loading, swipeRefreshSources);
                }
                mWordRecyclerViewAdapter.setArticles(articles);
            }
        });

    }

    private void initLoadingIndicators() {
        loading = new ProgressDialog(this);
        loading.setMessage(getResources().getString(R.string.articles_loading));
        swipeRefreshSources.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NetworkUtil.getArticles(injector, source.getId(), loading, swipeRefreshSources);
            }
        });
    }

    private void findViews() {
        rv = findViewById(R.id.listTopHeadlines);
        swipeRefreshSources = findViewById(R.id.swipeRefreshTopHeadlines);
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
                IntentUtil.putExtras(intent, source);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
