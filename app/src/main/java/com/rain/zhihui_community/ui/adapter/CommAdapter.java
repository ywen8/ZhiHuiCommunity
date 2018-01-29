package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.entity.Community;

import java.util.List;


/**
 * Created by Administrator on 2017/5/3/003.
 */

public class CommAdapter extends BaseAdapter {
    private Context context;
    private List<Community> list;

    public CommAdapter(Context context, List<Community> list) {
        this.context = context;
        this.list = list;
    }

    public List<Community> getList() {
        return list;
    }

    public void setList(List<Community> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_community, null);
            viewHolder.tv_item = (TextView) convertView.findViewById(R.id.ct_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item.setText(list.get(position).getCommname());
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_item;
    }
}
