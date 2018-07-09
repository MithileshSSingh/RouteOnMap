package com.example.mithilesh.routeonmap.mvp.screen_nearest_points;

import com.example.mithilesh.routeonmap.mvp.BasePresenter;
import com.example.mithilesh.routeonmap.mvp.BaseView;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;

import java.util.ArrayList;


public class NearestPointsContract {

    interface View extends BaseView<Presenter> {
        interface NearestPointCallback{
            void success(ArrayList<BeanLocation> dataList);
            void failed(int errorCode,String errorMessage);
        }
    }

    interface Presenter extends BasePresenter {
        void getNearestLocations(View.NearestPointCallback callback);
    }
}
