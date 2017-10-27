package com.lbz.android.myappplay.presenter.contract;

import com.lbz.android.myappplay.bean.AppDetailBean;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.ui.BaseView;

import java.util.List;


/**
 * Created by lbz on 2017/7/12.
 */
public interface AppInfoContract {

    interface View extends BaseView {

        void showData(PageBean pageBean);

    }

    interface AppInfoView extends BaseView {

        void showData(PageBean pageBean);

        void onLoadMoreComplete();

    }

    interface AppDetailView extends BaseView {

        void showResult(AppDetailBean detailBean);

    }

}
