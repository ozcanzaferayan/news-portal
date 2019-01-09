package com.zaferayan.newsportal.network;

import com.zaferayan.newsportal.ui.sources.model.SourcesResponse;
import com.zaferayan.newsportal.ui.topHeadlines.model.TopHeadlinesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    String URL = "https://newsapi.org/";

    @GET("v2/sources?apiKey=55d51f93924344e083d2f96a773ff0d1")
    Call<SourcesResponse> getSources();

    @GET("v2/top-headlines?apiKey=55d51f93924344e083d2f96a773ff0d1")
    Call<TopHeadlinesResponse> getTopHeadlines(@Query("sources") String... sources);
}

