package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.entity.TMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Rain
 * time : 2017/10/25 0025
 * explain :
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private Context mContext;
    private List<TMessage> list;

    public MessageAdapter(Context mContext, List<TMessage> tMessages) {
        this.mContext = mContext;
        this.list = tMessages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_item, parent, false);//解决宽度
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TMessage tMessage = list.get(position);
        holder.tv_title_name.setText(tMessage.getTitle());
        holder.tv_title_time.setText(tMessage.getCreatetime());
        holder.tv_title_data.setText(tMessage.getDetails());
    }

    @Override
    public int getItemCount() {
        return list.size();
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
