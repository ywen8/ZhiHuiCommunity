package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.utils.ImageUtils;


/**
 * author : Rain
 * time : 2017/10/20 0020
 * explain :
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private String[] images;


    public void setImages(String[] images) {
        this.images = images;
    }

    public ImageAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image_layout, null);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageUtils.image(context, BaseApiService.BASE_URL + images[position], viewHolder.iv_image);
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_image;
    }
}
