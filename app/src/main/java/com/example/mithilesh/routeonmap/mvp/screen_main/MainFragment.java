package com.example.mithilesh.routeonmap.mvp.screen_main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mithilesh.routeonmap.R;
import com.example.mithilesh.routeonmap.di.RepositoryInjector;
import com.example.mithilesh.routeonmap.mvp.BaseFragment;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;
import com.example.mithilesh.routeonmap.mvp.listeners.OnItemClicked;
import com.example.mithilesh.routeonmap.mvp.screen_map.RouteMapActivity;
import com.example.mithilesh.routeonmap.mvp.screen_nearest_points.NearestPointsActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class MainFragment extends BaseFragment implements MainContract.View, OnItemClicked, View.OnClickListener {

    public static final String TAG = MainFragment.class.getSimpleName();
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private MainContract.Presenter mPresenter;
    private Button mBtnNearestLocation;
    private Button mBtnAnimateOnMap;
    private TextView mTvLandmark;
    private RecyclerView mRvLocations;
    private ArrayList<BeanLocation> mDataList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private AdapterLocations mAdapter;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(mActivity, data);

                BeanLocation beanLocation = new BeanLocation();
                beanLocation.setName(String.valueOf(place.getName()));

                LatLng latLng = place.getLatLng();

                beanLocation.setLatitude(latLng.latitude);
                beanLocation.setLongitude(latLng.longitude);
                beanLocation.setLocationId(place.getId());

                addLocationToDB(beanLocation);

                Log.i(TAG, "Place: " + place.getName());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

                Status status = PlaceAutocomplete.getStatus(mActivity, data);
                Log.i(TAG, status.getStatusMessage());

            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    protected void init() {
        initView();
        initMembers();
        initListeners();
        initData();
    }

    @Override
    protected void initView() {

        mBtnAnimateOnMap = mView.findViewById(R.id.btn_animate_on_map);
        mBtnNearestLocation = mView.findViewById(R.id.btn_nearest_points);

        mTvLandmark = mView.findViewById(R.id.tv_landmark);

        mRvLocations = mView.findViewById(R.id.rv_locations);
    }

    @Override
    protected void initMembers() {

        mPresenter = new MainPresenter(RepositoryInjector.provideRepository(getContext()), this);

        mRvLocations.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRvLocations.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterLocations(mDataList, this, mActivity);
        mRvLocations.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
        mBtnAnimateOnMap.setOnClickListener(this);
        mBtnNearestLocation.setOnClickListener(this);

        mTvLandmark.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter.getAllLocation(new GetAllLocationCallback() {
            @Override
            public void success(ArrayList<BeanLocation> dataList) {
                mDataList = dataList;
                mAdapter.setDataList(mDataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failed(int errorCode, String errorMessage) {
                Toast.makeText(getActivity(), errorMessage + " : " + errorCode, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showNearestPoints() {
        Intent intentNearestPointsActivity = new Intent(getActivity(), NearestPointsActivity.class);
        startActivity(intentNearestPointsActivity);
    }

    private void animateOnMap() {
        Intent intentMapActivity = new Intent(getActivity(), RouteMapActivity.class);
        startActivity(intentMapActivity);
    }

    private void removeLocation(BeanLocation beanLocation, final int position) {

        String id = beanLocation.getLocationId();

        mPresenter.removeLocation(id, new EditLocationCallback() {
            @Override
            public void success(ArrayList<BeanLocation> dataList) {
                mDataList = dataList;
                mAdapter.setDataList(mDataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failed(int errorCode, String errorMessage) {
                Toast.makeText(getActivity(), errorMessage + " : " + errorCode, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addLocationToDB(BeanLocation beanLocation) {
        mPresenter.addLocation(beanLocation, new EditLocationCallback() {
            @Override
            public void success(ArrayList<BeanLocation> dataList) {
                mDataList = dataList;
                mAdapter.setDataList(mDataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failed(int errorCode, String errorMessage) {
                Toast.makeText(getActivity(), errorMessage + " : " + errorCode, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showAutoFillPlaceActivity() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(mActivity);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } else {
                Toast.makeText(getActivity(), "Activity Not found", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClicked(View v, int position) {
        BeanLocation beanLocation = mDataList.get(position);
        removeLocation(beanLocation, position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_animate_on_map:
                if(mDataList!=null && mDataList.size()>=2) {
                    animateOnMap();
                }else{
                    Toast.makeText(mActivity,"Please enter at least 2 location",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_nearest_points:

                if(mDataList!=null && mDataList.size()>=2) {
                    showNearestPoints();
                }else{
                    Toast.makeText(mActivity,"Please enter at least 2 location",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.tv_landmark:
                showAutoFillPlaceActivity();
                break;
        }
    }

}
