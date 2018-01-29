package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.entity.BannerItem;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.utils.ImageUtils;

import java.util.List;

/**
 * author : Rain
 * time : 2017/10/25 0025
 * explain :
 */

public class ControlServiceAdapter extends BaseAdapter {
    private Context context;
    private List<BannerItem> list;


    public ControlServiceAdapter(Context context) {
        this.context = context;
    }

    public List<BannerItem> getList() {
        return list;
    }

    public void setList(List<BannerItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_control_item, null);
            viewHolder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.iv_iamge = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BannerItem bannerItem = list.get(position);
        viewHolder.tv_data.setText(bannerItem.getTitle());
        viewHolder.tv_time.setText(bannerItem.getCreatetime());
        ImageUtils.image(context, BaseApiService.BASE_URL + bannerItem.getXfimg(), viewHolder.iv_iamge);
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_data, tv_time;
        private ImageView iv_iamge;
    }
}
