package com.zaferayan.newsportal.ui.savedArticles;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.zaferayan.newsportal.R;
import com.zaferayan.newsportal.ui.savedArticles.viewModel.ArticleViewModel;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;
import com.zaferayan.newsportal.ui.topHeadlines.model.Source;
import com.zaferayan.newsportal.ui.topHeadlines2.adapter.ArticleRecyclerViewAdapter;
import com.zaferayan.newsportal.util.ActionBarUtil;
import com.zaferayan.newsportal.util.BundleUtil;

import java.util.List;

public class SavedArticlesActivity extends AppCompatActivity {

    private ArticleViewModel articleViewModel;

    private ArticleRecyclerViewAdapter mWordRecyclerViewAdapter;
    private Source source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_headlines);
        RecyclerView rv = findViewById(R.id.listTopHeadlines);
        source = BundleUtil.getSource(getIntent().getExtras());
        ActionBarUtil.setTitle(getSupportActionBar(), getResources().getString(R.string.appbar_saved_articles) + ": " + source.getName());
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        articleViewModel.getSavedArticles(source.getId()).observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                mWordRecyclerViewAdapter.setArticles(articles);
            }
        });
        mWordRecyclerViewAdapter = new ArticleRecyclerViewAdapter(this, articleViewModel);
        rv.setAdapter(mWordRecyclerViewAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
