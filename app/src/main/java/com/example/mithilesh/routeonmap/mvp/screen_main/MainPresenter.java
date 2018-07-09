package com.example.mithilesh.routeonmap.mvp.screen_main;

import com.example.mithilesh.routeonmap.data.DataSource;
import com.example.mithilesh.routeonmap.data.Repository;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;

import java.util.ArrayList;

public class MainPresenter implements MainContract.Presenter {

    private Repository mRepository = null;
    private MainContract.View mView = null;

    private MainPresenter() {
    }

    public MainPresenter(Repository repository, MainContract.View view) {

        mRepository = repository;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void getAllLocation(final MainContract.View.GetAllLocationCallback callback) {
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

    @Override
    public void removeLocation(String id,final MainContract.View.EditLocationCallback callback) {
        mRepository.removeLocation(id,new DataSource.EditLocationCallback() {
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

    @Override
    public void addLocation(BeanLocation location, final MainContract.View.EditLocationCallback callback) {
        mRepository.addLocation(location, new DataSource.EditLocationCallback() {
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
