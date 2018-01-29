package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.entity.ImageIssueData;
import com.rain.zhihui_community.entity.MyHouse;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.ui.activity.ImagePagerActivity;
import com.rain.zhihui_community.ui.view.MyGridView;
import com.rain.zhihui_community.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : Rain
 * time : 2017/10/20 0020
 * explain :
 */

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.MyViewHolder> {
    private Context mContext;
    private ImageAdapter imageAdapter;
    private List<MyHouse> list;
    private MyDeleteClickListener mListener;
    private boolean isDelete = true;

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public void setList(List<MyHouse> list) {
        this.list = list;
    }

    public HouseAdapter(Context mContext, List<MyHouse> list) {
        this.mContext = mContext;
        this.list = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(View.inflate(mContext, R.layout.adapter_house, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final MyHouse myHouse = list.get(position);
        holder.mDetails.setText(myHouse.getDetails());
        if (isDelete) {
            holder.mDelete.setVisibility(View.VISIBLE);
            holder.tv_housing_name.setText(myHouse.getCommname());
        } else {
            holder.mDelete.setVisibility(View.GONE);
            ImageUtils.imageCircle(mContext, BaseApiService.BASE_URL + myHouse.getHeadimg(), holder.iv_photo);
            holder.tv_housing_name.setText(myHouse.getUsername());
        }

        final String[] images = myHouse.getImgs().split(",");
        imageAdapter = new ImageAdapter(mContext, images);
        holder.gv_data.setAdapter(imageAdapter);
        holder.gv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                List<ImageIssueData> photoUrls = new ArrayList<ImageIssueData>();
                for (String path :
                        images) {
                    ImageIssueData issueData = new ImageIssueData(path, false);
                    photoUrls.add(issueData);
                }
                ImagePagerActivity.startImagePagerActivity(mContext, photoUrls, i, imageSize);
            }
        });
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, myHouse, position);
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

        @BindView(R.id.iv_image)
        ImageView iv_photo;
        @BindView(R.id.tv_housing_name)
        TextView tv_housing_name;
        @BindView(R.id.iv_delete)
        ImageView mDelete;
        @BindView(R.id.tv_details)
        TextView mDetails;
        @BindView(R.id.gv_data)
        MyGridView gv_data;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setmListener(MyDeleteClickListener mListener) {
        this.mListener = mListener;
    }

    public interface MyDeleteClickListener {
        void onItemClick(View view, MyHouse myHouse, int position);
    }
}
