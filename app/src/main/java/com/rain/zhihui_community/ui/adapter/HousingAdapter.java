package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.entity.MyCommunity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class HousingAdapter extends RecyclerView.Adapter<HousingAdapter.MyViewHolder> {

    private Context mContext;
    private List<MyCommunity> list;
    private MyDeleteClickListener mListener;
    private OnItemViewListener onItemViewListener;
    private boolean isShow = false;

    public void setOnItemViewListener(OnItemViewListener onItemViewListener) {
        this.onItemViewListener = onItemViewListener;
    }

    public HousingAdapter(Context mContext, List<MyCommunity> list, boolean isShow) {
        this.mContext = mContext;
        this.list = list;
        this.isShow = isShow;
    }

    public void setList(List<MyCommunity> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(View.inflate(mContext, R.layout.item_housing_layout, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final MyCommunity myCommunity = list.get(position);
        holder.mHousingName.setText(myCommunity.getCommname());
        switch (myCommunity.getState()) {
            case 0:
                holder.tv_housing_state.setText("待审核");
                break;
            case 1:
                holder.tv_housing_state.setText("审核通过");
                break;
            case 2:
                holder.tv_housing_state.setText("审核失败");
                break;
            case 3:
                holder.tv_housing_state.setText("失效小区");
                break;
        }
        holder.mDetails.setText(myCommunity.getUnitnumber() + "  " + myCommunity.getRoomnumber());
        if (isShow) holder.mDelete.setVisibility(View.GONE);
        else
            holder.mDelete.setVisibility(View.VISIBLE);
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, myCommunity, position);
            }
        });
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemViewListener.OnItemListener(view, myCommunity);
            }
        });
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_item)
        RelativeLayout rl_item;
        @BindView(R.id.tv_housing_name)
        TextView mHousingName;
        @BindView(R.id.tv_details)
        TextView mDetails;
        @BindView(R.id.iv_delete)
        ImageView mDelete;
        @BindView(R.id.tv_housing_state)
        TextView tv_housing_state;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setmListener(MyDeleteClickListener mListener) {
        this.mListener = mListener;
    }

    public interface MyDeleteClickListener {
        void onItemClick(View view, MyCommunity community, int position);
    }

    public interface OnItemViewListener {
        void OnItemListener(View view, MyCommunity myCommunity);
    }
}
