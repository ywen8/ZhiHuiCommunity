package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.entity.TMessage;

import java.util.List;

/**
 * Created by Rain on 2017/10/27.
 */

public class TenementAdapter extends BaseAdapter {


    private Context context;
    private List<TMessage> list;

    public TenementAdapter(Context context, List<TMessage> tMessages) {
        this.context = context;
        this.list = tMessages;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_tenement_item, null);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            viewHolder.tv_data = convertView.findViewById(R.id.tv_data);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TMessage tMessage = list.get(position);
        viewHolder.tv_title.setText(tMessage.getTitle());
        viewHolder.tv_time.setText(tMessage.getCreatetime());
        viewHolder.tv_data.setText(tMessage.getDetails());
        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_data;
    }
}
