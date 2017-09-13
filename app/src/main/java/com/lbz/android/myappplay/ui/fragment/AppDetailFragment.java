package com.lbz.android.myappplay.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lbz.android.myappplay.R;
import com.lbz.android.myappplay.bean.AppDetailBean;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.imageloader.ImageLoader;
import com.lbz.android.myappplay.commom.util.DateUtils;
import com.lbz.android.myappplay.di.component.AppComponent;
import com.lbz.android.myappplay.di.component.DaggerAppDetailComponent;
import com.lbz.android.myappplay.di.module.AppDetailModule;
import com.lbz.android.myappplay.presenter.AppDetailPresenter;
import com.lbz.android.myappplay.presenter.contract.AppInfoContract;
import com.lbz.android.myappplay.ui.adapter.AppInfoAdapter;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by elitemc on 2017/9/12.
 */

public class AppDetailFragment extends ProgressFragment<AppDetailPresenter> implements AppInfoContract.AppDetailView {


    @Bind(R.id.view_gallery)
    LinearLayout mViewGallery;

    @Bind(R.id.view_introduction)
    ExpandableTextView mViewIntroduction;
    @Bind(R.id.txt_update_time)
    TextView mTxtUpdateTime;
    @Bind(R.id.txt_version)
    TextView mTxtVersion;
    @Bind(R.id.txt_apk_size)
    TextView mTxtApkSize;
    @Bind(R.id.txt_publisher)
    TextView mTxtPublisher;
    @Bind(R.id.txt_publisher2)
    TextView mTxtPublisher2;
    @Bind(R.id.recycler_view_same_dev)
    RecyclerView mRecyclerViewSameDev;
    @Bind(R.id.recycler_view_relate)
    RecyclerView mRecyclerViewRelate;
    @Bind(R.id.sameDevLayout)
    LinearLayout mSameDevLayout;

    private LayoutInflater mInflater;
    private int mAppId;
    private AppInfoAdapter mAdapter;


    public AppDetailFragment(int id) {

        this.mAppId = id;

    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_app_detail;
    }

    @Override
    protected void setFragmentComponent(AppComponent appComponent) {
        DaggerAppDetailComponent.builder().appComponent(appComponent)
                .appDetailModule(new AppDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        mInflater = LayoutInflater.from(getActivity());
        mPresenter.getAppDetail(mAppId);
    }

    @Override
    public void showResult(AppDetailBean detailBean) {

        showScreenshot(detailBean.getScreenshot());

        mViewIntroduction.setText(detailBean.getIntroduction());

        mTxtUpdateTime.setText(DateUtils.formatDate(detailBean.getUpdateTime()));
        mTxtApkSize.setText((detailBean.getApkSize() / 1014 / 1024) + " Mb");
        mTxtVersion.setText(detailBean.getVersionName());
        mTxtPublisher.setText(detailBean.getPublisherName());
        mTxtPublisher2.setText(detailBean.getPublisherName());

        mSameDevLayout.setVisibility(detailBean.getSameDevAppInfoList().size()==0?View.GONE:View.VISIBLE);

        mAdapter = AppInfoAdapter.builder().layout(R.layout.template_appinfo2)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewSameDev.setLayoutManager(layoutManager);


        mAdapter.addData(detailBean.getSameDevAppInfoList());
        mRecyclerViewSameDev.setAdapter(mAdapter);

        /////////////////////////////////////////////

        mAdapter = AppInfoAdapter.builder().layout(R.layout.template_appinfo2)
                .build();

        mRecyclerViewRelate.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter.addData(detailBean.getRelateAppInfoList());
        mRecyclerViewRelate.setAdapter(mAdapter);
    }

    private void showScreenshot(String screentShot) {

        List<String> urls = Arrays.asList(screentShot.split(","));

        for (String url : urls) {

            ImageView imageView = (ImageView) mInflater.inflate(R.layout.template_imageview, mViewGallery, false);

            ImageLoader.load(Constant.BASE_IMG_URL + url, imageView);

            mViewGallery.addView(imageView);

        }

    }

}
