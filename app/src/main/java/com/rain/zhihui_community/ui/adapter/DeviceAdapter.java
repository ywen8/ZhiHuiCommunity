package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rain.zhihui_community.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Rain
 * time : 2017/11/15 0015
 * explain :
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> {


    private Context mContext;

    public DeviceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(View.inflate(mContext, R.layout.adapter_device, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_device_name)
        TextView tv_device_name;
        @BindView(R.id.tv_device_num)
        TextView tv_device_num;
        @BindView(R.id.tv_device_address)
        TextView tv_device_address;
        @BindView(R.id.iv_delete)
        ImageView mDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
