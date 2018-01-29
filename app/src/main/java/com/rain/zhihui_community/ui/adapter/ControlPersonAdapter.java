package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.entity.ControlPerson;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.utils.ImageUtils;

import java.util.List;

/**
 * Created by Rain on 2017/10/30.
 */

public class ControlPersonAdapter extends BaseAdapter {
    private Context context;
    List<ControlPerson> list;

    public ControlPersonAdapter(Context context) {
        this.context = context;
    }

    public List<ControlPerson> getList() {
        return list;
    }

    public void setList(List<ControlPerson> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null && list.size() != 0)
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout_control, null);
            viewHolder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_join = (TextView) convertView.findViewById(R.id.tv_join);
            viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ControlPerson controlPerson = list.get(position);
        ImageUtils.image(context, BaseApiService.BASE_URL+controlPerson.getHeadimg(), viewHolder.iv_photo);
        viewHolder.tv_name.setText(controlPerson.getName());
        switch (controlPerson.getType()) {
            case 0:
                viewHolder.tv_join.setText("消防安全负责人");
                break;
            case 1:
                viewHolder.tv_join.setText("消防安全管理人");
                break;
            case 2:
                viewHolder.tv_join.setText("消防队员");
                break;
            case 3:
                viewHolder.tv_join.setText("消防楼长");
                break;
        }
        viewHolder.tv_phone.setText(controlPerson.getPhone());
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_photo;
        public TextView tv_name, tv_join, tv_phone;
    }
}
