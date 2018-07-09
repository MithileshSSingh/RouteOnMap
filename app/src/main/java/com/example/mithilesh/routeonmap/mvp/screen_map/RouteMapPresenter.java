package com.example.mithilesh.routeonmap.mvp.screen_map;

import com.example.mithilesh.routeonmap.data.DataSource;
import com.example.mithilesh.routeonmap.data.Repository;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;

import java.util.ArrayList;


public class RouteMapPresenter implements RouteMapContract.Presenter {

    private Repository mRepository = null;
    private RouteMapContract.View mView = null;

    private RouteMapPresenter() {
    }

    public RouteMapPresenter(Repository repository, RouteMapContract.View view) {

        mRepository = repository;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void getAllLocation(final RouteMapContract.View.GetAllLocationCallback callback) {
        mRepository.getAllLocation(new DataSource.GetAllLocationCallback() {
            @Override
            public void success(ArrayList<BeanLocation> dataList) {
                callback.success(dataList);
            }

            @Override
            public void failed(int errorCode, String errorMessage) {
                callback.failed(errorCode,errorMessage);
            }
        });
    }
}
