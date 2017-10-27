package com.lbz.android.myappplay.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppInfo;
import com.lbz.android.myappplay.bean.PageBean;
import com.lbz.android.myappplay.bean.event.AppDetailPageDownLoadBtnClickEvent;
import com.lbz.android.myappplay.commom.rx.RxBus;
import com.lbz.android.myappplay.presenter.AppInfoPresenter;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;
import com.lbz.android.myappplay.ui.activity.AppDetailActivity;
import com.lbz.android.myappplay.ui.adapter.AppInfoAdapter;
import com.lbz.android.myappplay.ui.widget.DividerItemDecoration;

import butterknife.Bind;
import io.reactivex.functions.Consumer;

/**
 * Created by lbz on 2017/9/4.
 */

public abstract class BaseAppInfoFragment extends ProgressFragment<AppInfoPresenter> implements AppInfoContract.AppInfoView, BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private AppInfoAdapter mAppInfoAdapter;

    int page = 0;

    @Override
    protected int setLayout() {
        return R.layout.template_recycler_view;
    }

    @Override
    protected void init() {
        initRecyclerView();
        mPresenter.requestData(type(), page,false);
    }

    protected void initRecyclerView() {
        mAppInfoAdapter = buildAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mAppInfoAdapter.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mAppInfoAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mMyApplication.setView(view);
                Intent intent = new Intent(getActivity(), AppDetailActivity.class);
                AppInfo appInfo = mAppInfoAdapter.getItem(position);
                intent.putExtra("appinfo", appInfo);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        RxBus.getDefault().toObservable(AppDetailPageDownLoadBtnClickEvent.class)
                .subscribe(new Consumer<AppDetailPageDownLoadBtnClickEvent>() {
                    @Override
                    public void accept(AppDetailPageDownLoadBtnClickEvent event) throws Exception {
                        mAppInfoAdapter.notifyItemChanged(event.getPosition());
                    }
                });
    }

    @Override
    public void showData(PageBean pageBean) {
        mAppInfoAdapter.addData(pageBean.getListApp());
        if (pageBean.isHasMore()) {
            page++;
        }
        mAppInfoAdapter.setEnableLoadMore(pageBean.isHasMore());
    }

    @Override
    public void onLoadMoreComplete() {
        mAppInfoAdapter.loadMoreComplete();

    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData(type(), page,false);
    }

    abstract int type();

    abstract AppInfoAdapter buildAdapter();


}
