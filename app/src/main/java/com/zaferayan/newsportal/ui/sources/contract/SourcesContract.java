package com.zaferayan.newsportal.ui.sources.contract;

import com.zaferayan.newsportal.ui.base.BasePresenter;
import com.zaferayan.newsportal.ui.base.BaseView;
import com.zaferayan.newsportal.ui.sources.model.Source;

import java.util.List;

public interface SourcesContract {
    interface View extends BaseView<Presenter> {

        void loadList(List<Source> sources);

        void loadEmptyList();

        void clearList();

        void showListLoading();

        void hideListLoading();

        void showListError(Exception e);

    }

    abstract class Presenter implements BasePresenter {
        abstract protected void loadList();

        abstract public void loadListWithProgressDialog();

        abstract public void loadListFromSwipeRefresh();
    }
}
