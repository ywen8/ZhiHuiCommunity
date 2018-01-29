package com.rain.zhihui_community.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.entity.ImageIssueData;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.utils.ImageUtils;

import java.io.File;
import java.util.List;

import static android.R.id.list;


/**
 * author : Rain
 * time : 2017/10/20 0020
 * explain :
 */

public class IssueImageAdapter extends BaseAdapter {
    private Context context;
    private List<ImageIssueData> images;


    public void setImages(List<ImageIssueData> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public IssueImageAdapter(Context context, List<ImageIssueData> images) {
        this.context = context;
        this.images = images;
    }

    public List<ImageIssueData> getImages() {
        return images;
    }


    @Override
    public int getCount() {
        if (images != null)
            return images.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image_issue_layout, null);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageIssueData issueData = images.get(position);
        if (issueData.isFile())
            ImageUtils.image(context, images.get(position).getFile(), viewHolder.iv_image);
        else
            ImageUtils.image(context, images.get(position).getPath(), viewHolder.iv_image);
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_image;
    }
}
