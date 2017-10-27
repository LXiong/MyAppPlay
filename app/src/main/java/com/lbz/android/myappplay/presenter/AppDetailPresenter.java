package com.lbz.android.myappplay.presenter;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.commom.rx.RxHttpResponseCompose;
import com.lbz.android.myappplay.commom.rx.subscriber.ProgressSubcriber;
import com.lbz.android.myappplay.data.AppInfoModel;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lbz on 2017/9/12.
 */

public class AppDetailPresenter extends BasePresenter<AppInfoModel, AppInfoContract.AppDetailView> {

    @Inject
    public AppDetailPresenter(AppInfoModel mModel, AppInfoContract.AppDetailView mView) {
        super(mModel, mView);
    }

    public void getAppDetail(int app_id,boolean update) {
        mModel.getAppDetailById(app_id,update)
                .compose(RxHttpResponseCompose.composeSchedulers())
                .subscribe(new ProgressSubcriber<PageBean>(mContext, mView) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(PageBean pageBean) {
                        mView.showResult(pageBean.getApp());
                    }
                });
    }
}
