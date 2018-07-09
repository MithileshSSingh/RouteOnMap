package com.example.mithilesh.routeonmap.mvp.screen_main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mithilesh.routeonmap.R;
import com.example.mithilesh.routeonmap.mvp.bean.BeanLocation;
import com.example.mithilesh.routeonmap.mvp.listeners.OnItemClicked;

import java.util.ArrayList;

public class AdapterLocations extends RecyclerView.Adapter<ViewHolderLocations> {

    private Context mContext;
    private OnItemClicked mListener;
    private ArrayList<BeanLocation> mDataList;

    AdapterLocations(ArrayList<BeanLocation> dataList, OnItemClicked listener, Context context) {
        mContext = context;
        mListener = listener;
        mDataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolderLocations onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location_main_layout, null);
        ViewHolderLocations viewHolderLocations = new ViewHolderLocations(view);
        return viewHolderLocations;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLocations holder, int position) {
        BeanLocation data = mDataList.get(position);
        holder.setData(data, position, mListener);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setDataList(ArrayList<BeanLocation> dataList){
        mDataList = dataList;
    }
}

class ViewHolderLocations extends RecyclerView.ViewHolder implements View.OnClickListener {
    private BeanLocation mData;
    private int mPosition;
    private OnItemClicked mListener;

    private ImageView ivDelete;

    private TextView tvLatLng;
    private TextView tvLocationName;

    public ViewHolderLocations(View itemView) {
        super(itemView);

        initView();
        initListener();

    }

    private void initView() {

        ivDelete = itemView.findViewById(R.id.btn_delete);

        tvLatLng = itemView.findViewById(R.id.tv_lat_lng);
        tvLocationName = itemView.findViewById(R.id.tv_location_name);

    }

    private void initListener() {

        ivDelete.setOnClickListener(this);

    }

    public void setData(BeanLocation data, int position, OnItemClicked listener) {
        this.mData = data;
        this.mPosition = position;
        this.mListener = listener;

        tvLatLng.setText(String.valueOf("Latitude\t: " + String.valueOf(mData.getLatitude()) + "\n" + "Longitude\t: " + String.valueOf(mData.getLongitude())));
        tvLocationName.setText(String.valueOf(mData.getName()));
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                mListener.onItemClicked(null, mPosition);
                break;
        }
    }
}
