package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.entity.BuildData;
import com.rain.zhihui_community.entity.Community;

import java.util.List;


/**
 * Created by Administrator on 2017/5/3/003.
 */

public class BuildNumAdapter extends BaseAdapter {
    private Context context;
    private List<BuildData> list;

    public BuildNumAdapter(Context context, List<BuildData> list) {
        this.context = context;
        this.list = list;
    }

    public List<BuildData> getList() {
        return list;
    }

    public void setList(List<BuildData> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_certificate_know, null);
            viewHolder.tv_item = (TextView) convertView.findViewById(R.id.ct_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item.setText(list.get(position).getFloorname());
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_item;
    }
}
