package com.rain.zhihui_community.ui.activity.appointment;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.Appointment;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.view.banner.BannerView;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.EncrypDES3;
import com.rain.zhihui_community.utils.ImageUtils;
import com.rain.zhihui_community.utils.TitleUtls;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentDoorActivity extends BaseAppAvtivity {


    @BindView(R.id.banner)
    BannerView banner;

    @OnClick(R.id.btn_open_door)
    void openDoor() {
        loading.showLoading();
        RetrofitClient.getInstance(this).createBaseApi().app_open(persons.getPhone(), new BaseSubscriber<List<Appointment>>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                loading.dismissLoading();
                DialogUtil.dialogColor(AppointmentDoorActivity.this, "抱歉！您还没有预约或预约已过期，请重新联系业主。");
                DialogUtil.getTv_sure().setText("确定");
                DialogUtil.getTv_sure().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogUtil.dismissDialog();
                        finish();
                    }
                });
            }

            @Override
            public void onNext(List<Appointment> appointments) {
                super.onNext(appointments);
                try {
                    loading.dismissLoading();
                    String path = "2," + persons.getPhone() +","+ appointments.get(0).getCommid() + "," + appointments.get(0).getPhone() + "," + getTime();
                    String MiPath = getBase64(path);
                    String base64 = EncrypDES3.encode(MiPath);
                    DialogUtil.openDoor(AppointmentDoorActivity.this, base64);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @OnClick(R.id.button)
    void proprietor() {
        startActiviys(SubscribePersonActivity.class);
    }

    List<BannerItem> list = new ArrayList<>();
    public static int[] urls = new int[]{//750x500
            R.drawable.banner_yuyue_1,
            R.drawable.banner_yuyue_2,
            R.drawable.banner_yuyue_3
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_door);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "预约开门");
        persons = GloData.getPersons().getUser();
        for (int i = 0; i < urls.length; i++) {
            BannerItem item = new BannerItem();
            item.image = urls[i];
            list.add(item);
        }
        banner.setViewFactory(new BannerViewFactory());
        banner.setDataList(list);
        banner.start();
    }

    public class BannerViewFactory implements BannerView.ViewFactory<BannerItem> {

        @Override
        public View create(final BannerItem bannerItem, int position, ViewGroup container) {
            ImageView iv = new ImageView(container.getContext());
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageUtils.image(AppointmentDoorActivity.this, bannerItem.image, iv);
            return iv;
        }
    }

    public static class BannerItem {
        public int image;
        public String title;

        @Override
        public String toString() {
            return title;
        }
    }

    /**
     * 生成时间戳
     *
     * @return
     */
    public String getTime() {
        long time = System.currentTimeMillis();
        String str = String.valueOf(time);
        return str;
    }

    // 加密
    public String getBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
