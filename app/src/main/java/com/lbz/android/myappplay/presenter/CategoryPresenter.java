package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.PageMiBean;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.presenter.contract.CategoryContract;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by elitemc on 2017/9/5.
 */

public class CategoryPresenter extends BasePresenter<CategoryContract.ICategoryModel, CategoryContract.CategoryView> {

    @Inject
    public CategoryPresenter(CategoryContract.ICategoryModel mModel, CategoryContract.CategoryView mView) {
        super(mModel, mView);
    }

    public void requestDatas() {
        mModel.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubcriber<PageMiBean>(mContext,mView) {
                    @Override
                    public void onNext(PageMiBean categoryBean) {
                        mView.showData(categoryBean.getCategories());
                    }
                });
    }

}
