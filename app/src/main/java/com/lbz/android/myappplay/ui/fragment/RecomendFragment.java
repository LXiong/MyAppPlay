package com.lbz.android.myappplay.ui.fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.IndexBean;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerRecommendComponent;
import com.lbz.android.myappplay.di.module.RecommendModule;
import com.lbz.android.myappplay.presenter.RecomendPresenter;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;
import com.lbz.android.myappplay.ui.adapter.IndexMutilAdapter;


import butterknife.Bind;

/**
 * Created by elitemc on 2017/7/11.
 */
public class RecomendFragment extends ProgressFragment<RecomendPresenter> implements AppInfoContract.View {

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    IndexMutilAdapter mAdapter;

    @Override
    protected int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerRecommendComponent.builder()
                .appComponent(appComponent)
                .recommendModule(new RecommendModule(this))
                .build().inject(this);
    }

    @Override
    protected void init() {
        initRecyclerView();
        mPresenter.requestDatas();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }



    @Override
    public void onEmptyViewClick() {
        mPresenter.requestDatas();
    }

    @Override
    public void showData(IndexBean indexBean) {
        mAdapter = new IndexMutilAdapter(getActivity());

        mAdapter.setData(indexBean);

        mRecyclerView.setAdapter(mAdapter);
    }
}