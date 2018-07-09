package com.example.mithilesh.routeonmap.data.remote;

import android.content.Context;

import com.example.mithilesh.routeonmap.data.DataSource;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;

/**
 * Created by mithilesh on 8/19/16.
 */
public class RemoteDataSource implements DataSource {


    private static RemoteDataSource INSTANCE = null;
    private Context mContext;

    private RemoteDataSource() {

    }

    private RemoteDataSource(Context context) {
        mContext = context;
    }

    public static synchronized RemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(context);
        }

        return INSTANCE;
    }

    @Override
    public void getAllLocation(GetAllLocationCallback callback) {

    }

    @Override
    public void removeLocation(String id, EditLocationCallback callback) {

    }

    @Override
    public void addLocation(BeanLocation location, EditLocationCallback callback) {

    }

    @Override
    public void getNearestLocations(GetAllLocationCallback callback) {

    }

}
