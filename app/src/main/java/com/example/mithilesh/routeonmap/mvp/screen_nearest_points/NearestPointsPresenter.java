package com.example.mithilesh.routeonmap.mvp.screen_nearest_points;


import com.example.mithilesh.routeonmap.data.DataSource;
import com.example.mithilesh.routeonmap.data.Repository;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;

import java.util.ArrayList;

public class NearestPointsPresenter implements NearestPointsContract.Presenter {

    private Repository mRepository = null;
    private NearestPointsContract.View mView = null;

    private NearestPointsPresenter() {
    }

    public NearestPointsPresenter(Repository repository, NearestPointsContract.View view) {

        mRepository = repository;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void getNearestLocations(final NearestPointsContract.View.NearestPointCallback callback) {
        mRepository.getNearestLocations(new DataSource.GetAllLocationCallback() {
            @Override
            public void success(ArrayList<BeanLocation> dataList) {
                callback.success(dataList);
            }

            @Override
            public void failed(int errorCode, String errorMessage) {
                callback.failed(errorCode, errorMessage);
            }
        });
    }
}
