package com.example.mithilesh.routeonmap.data;


import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

public class Repository implements DataSource {


    private static Repository INSTANCE = null;

    private DataSource mLocalDataSource = null;
    private DataSource mRemoteDataSource = null;

    private Repository() {

    }

    private Repository(DataSource localDataSource, DataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static synchronized Repository getInstance(DataSource localDataSource, DataSource remoteDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new Repository(localDataSource, remoteDataSource);
        }

        return INSTANCE;
    }

    @Override
    public void getAllLocation(final GetAllLocationCallback callback) {
        mLocalDataSource.getAllLocation(new GetAllLocationCallback() {
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

    @Override
    public void removeLocation(String id, final EditLocationCallback callback) {
        mLocalDataSource.removeLocation(id, new EditLocationCallback() {
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

    @Override
    public void addLocation(BeanLocation location, final EditLocationCallback callback) {
        mLocalDataSource.addLocation(location, new EditLocationCallback() {
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

    @Override
    public void getNearestLocations(final GetAllLocationCallback callback) {
        mLocalDataSource.getAllLocation(new GetAllLocationCallback() {
            @Override
            public void success(ArrayList<BeanLocation> dataList) {
                ArrayList<BeanLocation> nearestPoints = calculateNearestLocations(dataList);
                callback.success(nearestPoints);
            }

            @Override
            public void failed(int errorCode, String errorMessage) {
                callback.failed(errorCode, errorMessage);
            }
        });
    }

    private ArrayList<BeanLocation> calculateNearestLocations(ArrayList<BeanLocation> dataList) {

        ArrayList<BeanLocation> ansList = new ArrayList<>();

        int noOfLocations = dataList.size();

        Double matrix[][] = new Double[noOfLocations][noOfLocations];
        Double minDistance = Double.MAX_VALUE;

        /**
         * Computing distance between each locations
         */

        for (int i = 0; i < noOfLocations; i++) {
            for (int j = 0; j < noOfLocations; j++) {
                if (i == j) {
                    matrix[i][j] = Double.MAX_VALUE;
                } else {
                    LatLng source = new LatLng(dataList.get(i).getLatitude(), dataList.get(i).getLongitude());
                    LatLng destination = new LatLng(dataList.get(j).getLatitude(), dataList.get(j).getLongitude());

                    Double distance = SphericalUtil.computeDistanceBetween(source, destination);

                    matrix[i][j] = distance;
                }
            }
        }


        /**
         * Computing minimum Distance Brute force way
         */
        int source = 0;
        int destination = 0;

        for (int i = 0; i < noOfLocations; ++i) {
            for (int j = i + 1; j < noOfLocations; ++j) {
                if (matrix[i][j].compareTo(minDistance) < 0) {
                    minDistance = matrix[i][j];
                    source = i;
                    destination = j;
                }
            }
        }

        ansList.add(dataList.get(source));
        ansList.add(dataList.get(destination));

        return ansList;
    }
}