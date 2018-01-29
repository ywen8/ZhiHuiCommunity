package com.rain.zhihui_community.ui.activity.control;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.BuildData;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.adapter.BuildNumAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.ImageUtils;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControlBuildActivity extends BaseAppAvtivity {


    @BindView(R.id.image_build)
    ImageView mImageView;
    @BindView(R.id.tv_build_data)
    TextView mBuildText;
    @BindView(R.id.tv_right_title)
    TextView right_title;
    private MyCommunity myCommunity;

    @OnClick(R.id.rl_add_housing)
    void house() {
        RetrofitClient.getInstance(this).createBaseApi().postBuildNum(myCommunity.getCommid(), new BaseSubscriber<List<BuildData>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(ControlBuildActivity.this, e.Message);
            }

            @Override
            public void onNext(List<BuildData> buildDatas) {
                super.onNext(buildDatas);
                showDialog(buildDatas);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_build);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.TitleTextView(this, "社区建筑信息");
        right_title.setText("楼号");

        myHousing = GloData.getPersons().getMiddleueDTOS();

        for (MyCommunity community :
                myHousing) {
            if (community.isSelect()) {
                myCommunity = community;
            }
        }

        RetrofitClient.getInstance(this).createBaseApi().postBuild(myCommunity.getCommid(), new BaseSubscriber<BuildData>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(ControlBuildActivity.this, e.Message);
            }

            @Override
            public void onNext(BuildData buildData) {
                super.onNext(buildData);
                ImageUtils.image(ControlBuildActivity.this, BaseApiService.BASE_URL + buildData.getImg(), mImageView);
                mBuildText.setText("   " + buildData.getBuild());
            }
        });
    }

    public void showDialog(List<BuildData> list) {
        final Dialog dialShareDialog = new Dialog(this, R.style.below_dialog);
        dialShareDialog.setContentView(R.layout.dialog_layout_list);
        dialShareDialog.setCanceledOnTouchOutside(true);
        // 获取对话框的窗口，并设置窗口参数
        WindowManager.LayoutParams lp = dialShareDialog.getWindow().getAttributes();
        lp.dimAmount = 0.7f;
        dialShareDialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialShareDialog.getWindow().setGravity(Gravity.BOTTOM);
        ListView listView = dialShareDialog.findViewById(R.id.list_building);
        final BuildNumAdapter adapter = new BuildNumAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BuildData buildData = (BuildData) adapter.getItem(position);
                Intent intent = new Intent(ControlBuildActivity.this, TowerActivity.class);
                intent.putExtra("id", buildData.getId());
                intent.putExtra("title", buildData.getFloorname());
                startActivity(intent);
                dialShareDialog.dismiss();
            }
        });

        dialShareDialog.show();
    }
}
