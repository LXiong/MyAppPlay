package com.lbz.android.myappplay.di.module;


import com.lbz.android.myappplay.bean.SearchHistory.SearchHistoryDao;
import com.lbz.android.myappplay.data.SearchAppModel;
import com.lbz.android.myappplay.data.http.ApiService;
import com.lbz.android.myappplay.data.http.Repository;
import com.lbz.android.myappplay.presenter.contract.SearchAppContract;

import dagger.Module;
import dagger.Provides;


@Module
public class SearchAppModule {

    private SearchAppContract.SearchAppView mView;

    public SearchAppModule( SearchAppContract.SearchAppView view) {


        this.mView = view;
    }


    @Provides
    public  SearchAppContract.SearchAppView provideView() {

        return mView;
    }


    @Provides
    public  SearchAppContract.ISearchAppModel privodeModel(Repository repository, SearchHistoryDao searchHistoryDao) {

        return new SearchAppModel(repository,searchHistoryDao);
    }



}
