package com.example.mithilesh.routeonmap.mvp.screen_map;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.example.mithilesh.routeonmap.R;
import com.example.mithilesh.routeonmap.di.RepositoryInjector;
import com.example.mithilesh.routeonmap.mvp.BaseFragment;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;


public class RouteMapFragment extends BaseFragment implements RouteMapContract.View, OnMapReadyCallback {

    public static final String TAG = RouteMapFragment.class.getSimpleName();

    private RouteMapContract.Presenter mPresenter;
    private ArrayList<BeanLocation> mDataList = new ArrayList<>();

    private SupportMapFragment mMapFragment;
    private GoogleMap mGoogleMap;

    public RouteMapFragment() {
    }

    public static RouteMapFragment newInstance() {
        return new RouteMapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_route_map, container, false);
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
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

    }

    @Override
    protected void initMembers() {
        mPresenter = new RouteMapPresenter(RepositoryInjector.provideRepository(getContext()), this);

        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void setPresenter(RouteMapContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mPresenter.getAllLocation(new GetAllLocationCallback() {
            @Override
            public void success(ArrayList<BeanLocation> dataList) {
                drawPath(dataList);
            }

            @Override
            public void failed(int errorCode, String errorMessage) {
                Toast.makeText(mActivity, errorMessage + " : " + errorCode, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void drawPath(ArrayList<BeanLocation> dataList) {

        List<LatLng> latLngs = new ArrayList<>();


        LatLng origin = null;
        LatLng destination = null;

        for (int i = 1; i < dataList.size(); i++) {
            origin = new LatLng(dataList.get(i - 1).getLatitude(), dataList.get(i - 1).getLongitude());
            destination = new LatLng(dataList.get(i).getLatitude(), dataList.get(i).getLongitude());
            DrawRouteMaps.getInstance(mActivity).draw(origin, destination, mGoogleMap);

            drawCircle(origin);
        }

        drawCircle(destination);

        LatLng firstPoint = new LatLng(dataList.get(0).getLatitude(), dataList.get(0).getLongitude());
        LatLng lastPoint = new LatLng(dataList.get(dataList.size() - 1).getLatitude(), dataList.get(dataList.size() - 1).getLongitude());

        LatLngBounds bounds = new LatLngBounds.Builder().include(firstPoint).include(lastPoint).build();

        Point displaySize = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(displaySize);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));

    }

    private void drawCircle(LatLng point){

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.strokeColor(Color.RED);
        circleOptions.radius(40);
        circleOptions.fillColor(0xffff0000);
        mGoogleMap.addCircle(circleOptions);

    }
}
