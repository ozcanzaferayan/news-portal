package com.zaferayan.newsportal.ui.topHeadlines.contract;

import com.zaferayan.newsportal.ui.base.BasePresenter;
import com.zaferayan.newsportal.ui.base.BaseView;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

import java.util.List;

public interface TopHeadlinesContract {
    interface View extends BaseView<TopHeadlinesContract.Presenter> {

        void loadList(List<Article> articles);

        void loadEmptyList();

        void showListLoading();

        void hideListLoading();

        void showListError(Exception e);

    }

    abstract class Presenter implements BasePresenter {
        abstract protected void loadList(String sourceId);

        abstract public void loadListWithProgressDialog(String sourceId);

        abstract public void loadListFromSwipeRefresh(String sourceId);

        public abstract void loadEmptyList();
    }
}
