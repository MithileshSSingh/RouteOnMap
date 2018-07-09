package com.example.mithilesh.routeonmap.mvp.screen_nearest_points;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mithilesh.routeonmap.R;
import com.example.mithilesh.routeonmap.di.RepositoryInjector;
import com.example.mithilesh.routeonmap.mvp.BaseFragment;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;
import com.example.mithilesh.routeonmap.mvp.listeners.OnItemClicked;

import java.util.ArrayList;

public class NearestPointsFragment extends BaseFragment implements NearestPointsContract.View, OnItemClicked {

    public static final String TAG = NearestPointsFragment.class.getSimpleName();

    private NearestPointsContract.Presenter mPresenter;

    private RecyclerView mRvNearestLocations;

    private ArrayList<BeanLocation> mDataList = new ArrayList<>();

    private LinearLayoutManager mLayoutManager;
    private AdapterNearestLocations mAdapter;

    public NearestPointsFragment() {
    }

    public static NearestPointsFragment newInstance() {
        return new NearestPointsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_nearest_points, container, false);
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
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
        mRvNearestLocations = mView.findViewById(R.id.rv_locations);
    }

    @Override
    protected void initMembers() {

        mPresenter = new NearestPointsPresenter(RepositoryInjector.provideRepository(getContext()), this);

        mRvNearestLocations.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRvNearestLocations.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterNearestLocations(mDataList, this, getActivity());
        mRvNearestLocations.setAdapter(mAdapter);
    }

    @Override
    protected void initListeners() {
    }

    @Override
    protected void initData() {
        mPresenter.getNearestLocations(new NearestPointCallback() {
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
    public void setPresenter(NearestPointsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onItemClicked(View v, int position) {

    }
}
