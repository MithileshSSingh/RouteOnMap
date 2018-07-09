package com.example.mithilesh.routeonmap.data;


import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;

import java.util.ArrayList;

public interface DataSource {

    void getAllLocation(GetAllLocationCallback callback);

    void removeLocation(String id, EditLocationCallback callback);

    void addLocation(BeanLocation location, EditLocationCallback callback);

    void getNearestLocations(GetAllLocationCallback callback);

    interface GetAllLocationCallback {
        void success(ArrayList<BeanLocation> dataList);

        void failed(int errorCode, String errorMessage);
    }

    interface EditLocationCallback {
        void success(ArrayList<BeanLocation> dataList);

        void failed(int errorCode, String errorMessage);
    }
}
