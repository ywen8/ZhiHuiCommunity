package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rain.zhihui_community.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Rain
 * time : 2017/11/15 0015
 * explain :
 */

public class ControlMessageAdapter extends RecyclerView.Adapter<ControlMessageAdapter.MyViewHolder> {
    private Context mContext;

    public ControlMessageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_item, parent, false);//解决宽度
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_title_name.setText("烟感报警器");
        holder.tv_title_time.setText("2017-10-22");
        holder.tv_title_data.setText("设备ID: d50f110003d3 \n类型：烟感 \n开始时间：2017-11-06 23:26:35 \n结束时间：2017-11-06 23:27:00 \n报警地址：陕西省西安市碑林区火炬路");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_name)
        TextView tv_title_name;
        @BindView(R.id.tv_title_time)
        TextView tv_title_time;
        @BindView(R.id.tv_title_data)
        TextView tv_title_data;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
