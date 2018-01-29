package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rain.zhihui_community.R;

/**
 * Created by Rain on 2017/10/31.
 */

public class CertificateAdapter extends BaseAdapter {
    public Context mContext;
    public int type;

    public CertificateAdapter(Context mContext, int type) {
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_certificate_win, null);
            viewHolder.tv_title_name = convertView.findViewById(R.id.tv_title_name);
            viewHolder.tv_phone = convertView.findViewById(R.id.tv_phone);
            viewHolder.tv_address = convertView.findViewById(R.id.tv_address);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (type == 1) {
            viewHolder.tv_title_name.setText("未央大队永久快处快赔中心");
            viewHolder.tv_phone.setText("62595299");
            viewHolder.tv_address.setText("永久汽车服务有限公司枣园");
        } else {
            viewHolder.tv_title_name.setText("西安市公安局出入境办证大厅");
            viewHolder.tv_phone.setText("029-87654321");
            viewHolder.tv_address.setText("陕西省西安市火炬路");
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_title_name, tv_phone, tv_address;
        ImageView iv_phone, iv_address;
    }
}
