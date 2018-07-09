package com.example.mithilesh.routeonmap.mvp.screen_nearest_points;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mithilesh.routeonmap.R;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;
import com.example.mithilesh.routeonmap.mvp.listeners.OnItemClicked;

import java.util.ArrayList;

public class AdapterNearestLocations extends RecyclerView.Adapter<ViewHolderNearestLocations> {

    private Context mContext;
    private OnItemClicked mListener;
    private ArrayList<BeanLocation> mDataList;

    AdapterNearestLocations(ArrayList<BeanLocation> dataList, OnItemClicked listener, Context context) {
        mContext = context;
        mListener = listener;
        mDataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolderNearestLocations onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location_nearest, null);
        ViewHolderNearestLocations viewHolderNearestLocations = new ViewHolderNearestLocations(view);
        return viewHolderNearestLocations;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNearestLocations holder, int position) {
        BeanLocation data = mDataList.get(position);
        holder.setData(data, position, mListener);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setDataList(ArrayList<BeanLocation> dataList) {
        this.mDataList = dataList;
    }
}

class ViewHolderNearestLocations extends RecyclerView.ViewHolder {
    private BeanLocation mData;
    private int mPosition;
    private OnItemClicked mListener;

    private TextView tvLatLng;
    private TextView tvLocationName;

    public ViewHolderNearestLocations(View itemView) {
        super(itemView);

        initView();
        initListener();

    }

    private void initView() {

        tvLatLng = itemView.findViewById(R.id.tv_lat_lng);
        tvLocationName = itemView.findViewById(R.id.tv_location_name);

    }

    private void initListener() {

    }

    public void setData(BeanLocation data, int position, OnItemClicked listener) {
        this.mData = data;
        this.mPosition = position;
        this.mListener = mListener;

        tvLatLng.setText(String.valueOf("Latitude : " + String.valueOf(mData.getLatitude()) + "\n" + "Longitude : " + String.valueOf(mData.getLongitude())));
        tvLocationName.setText(String.valueOf(mData.getName()));

    }

}
