package com.example.mithilesh.routeonmap.mvp.screen_map;

import com.example.mithilesh.routeonmap.mvp.BasePresenter;
import com.example.mithilesh.routeonmap.mvp.BaseView;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;
import com.example.mithilesh.routeonmap.mvp.screen_main.MainContract;

import java.util.ArrayList;


public class RouteMapContract {

    interface View extends BaseView<Presenter> {
        interface GetAllLocationCallback{
            void success(ArrayList<BeanLocation> dataList);
            void failed(int errorCode,String errorMessage);
        }
    }

    interface Presenter extends BasePresenter {
        void getAllLocation(View.GetAllLocationCallback callback);
    }
}
