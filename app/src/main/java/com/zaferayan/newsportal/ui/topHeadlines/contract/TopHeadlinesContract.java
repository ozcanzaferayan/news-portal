package com.zaferayan.newsportal.ui.topHeadlines.contract;

import com.zaferayan.newsportal.ui.base.BasePresenter;
import com.zaferayan.newsportal.ui.base.BaseView;
import com.zaferayan.newsportal.ui.topHeadlines.model.Article;

import java.util.List;

public interface TopHeadlinesContract {
    interface View extends BaseView<TopHeadlinesContract.Presenter> {

        void loadList(List<Article> articles, List<Article> storedArticles, TopHeadlinesContract.Presenter presenter);

        void loadEmptyList();

        void showListLoading();

        void hideListLoading();

        void showListError(Exception e);

        void navigateToWebView(Article article);
    }

    abstract class Presenter implements BasePresenter {
        abstract protected void loadList();

        abstract public void loadListWithProgressDialog();

        abstract public void loadListFromSwipeRefresh();

        public abstract void loadEmptyList();

        public abstract android.view.View.OnClickListener addOrDeleteArticle(Article article, boolean isStored);

        public abstract void navigateToWebView(Article article);
    }
}
