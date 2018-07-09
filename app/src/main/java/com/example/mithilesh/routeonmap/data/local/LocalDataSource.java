package com.example.mithilesh.routeonmap.data.local;

import android.content.Context;

import com.example.mithilesh.routeonmap.data.DataSource;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;

import java.util.ArrayList;

public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE = null;

    private Context mContext;
    private ArrayList<BeanLocation> mDataList = new ArrayList<>();

    private LocalDataSource() {

    }

    private LocalDataSource(Context context) {
        mContext = context;
    }

    public static synchronized LocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }

        return INSTANCE;
    }

    @Override
    public void getAllLocation(GetAllLocationCallback callback) {
        callback.success(mDataList);
    }

    @Override
    public void removeLocation(String id, EditLocationCallback callback) {
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).getLocationId().equals(id)) {
                mDataList.remove(i);
                break;
            }
        }
        callback.success(mDataList);
    }

    @Override
    public void addLocation(BeanLocation location, EditLocationCallback callback) {
        mDataList.add(location);
        callback.success(mDataList);
    }

    @Override
    public void getNearestLocations(GetAllLocationCallback callback) {

    }
}
