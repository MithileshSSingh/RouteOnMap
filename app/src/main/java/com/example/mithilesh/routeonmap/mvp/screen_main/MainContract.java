package com.example.mithilesh.routeonmap.mvp.screen_main;

import com.example.mithilesh.routeonmap.mvp.BasePresenter;
import com.example.mithilesh.routeonmap.mvp.BaseView;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;

import java.util.ArrayList;


public class MainContract {

    interface View extends BaseView<Presenter> {

        interface GetAllLocationCallback{
            void success(ArrayList<BeanLocation> dataList);
            void failed(int errorCode, String errorMessage);
        }

        interface EditLocationCallback {
            void success(ArrayList<BeanLocation> dataList);
            void failed(int errorCode, String errorMessage);
        }

    }

    interface Presenter extends BasePresenter {
        void getAllLocation(View.GetAllLocationCallback callback);
        void removeLocation(String id,View.EditLocationCallback callback);
        void addLocation(BeanLocation location, View.EditLocationCallback callback);
    }
}
