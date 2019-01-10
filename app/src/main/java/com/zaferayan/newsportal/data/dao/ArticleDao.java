package com.zaferayan.newsportal.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM Article")
    LiveData<List<Article>> getAllArticles();
}