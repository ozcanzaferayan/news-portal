package com.zaferayan.newsportal.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Article article);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Article> article);

    @Query("UPDATE Article SET isSaved = :isSaved WHERE mUrl =:url")
    void update(String url, boolean isSaved);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM Article")
    LiveData<List<Article>> getAllArticlesAsync();

    @Query("SELECT * FROM Article WHERE mSource = :sourceId ORDER BY mPublishedAt DESC")
    LiveData<List<Article>> getAllArticlesBySource(String sourceId);


    @Query("SELECT * FROM Article WHERE mSource = :sourceId AND isSaved ORDER BY mPublishedAt DESC")
    LiveData<List<Article>> getSavedArticles(String sourceId);


    @Query("SELECT * FROM Article")
    List<Article> getAllArticles();


}