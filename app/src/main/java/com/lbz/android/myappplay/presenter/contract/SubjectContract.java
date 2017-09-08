package com.lbz.android.myappplay.presenter.contract;

import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.bean.SubjectBean;
import com.lbz.android.myappplay.ui.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by elitemc on 2017/9/5.
 */

public interface SubjectContract {

    interface ISubjectModel {

        Observable<PageBean> getSubject();

    }

    interface SubjectView extends BaseView {

        void showData(List<SubjectBean> subjectList);

    }

}
