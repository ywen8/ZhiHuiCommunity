package com.rain.zhihui_community.ui.fragment.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppFragment;
import com.rain.zhihui_community.entity.BannerItem;
import com.rain.zhihui_community.entity.ButtonModel;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.activity.ReportPoliceActivity;
import com.rain.zhihui_community.ui.activity.WebActivity;
import com.rain.zhihui_community.ui.activity.appointment.AppointmentDoorActivity;
import com.rain.zhihui_community.ui.activity.control.FireControlActivity;
import com.rain.zhihui_community.ui.activity.government.SecurityActivity;
import com.rain.zhihui_community.ui.activity.lethouse.LetHouseActivity;
import com.rain.zhihui_community.ui.activity.life.TenementActivity;
import com.rain.zhihui_community.ui.activity.tenement.CommunityServiceActivity;
import com.rain.zhihui_community.ui.activity.traffic.TrafficActivity;
import com.rain.zhihui_community.ui.activity.village.HousingActivity;
import com.rain.zhihui_community.ui.activity.village.addhousing.AddHousingActivity;
import com.rain.zhihui_community.ui.adapter.SortButtonAdapter;
import com.rain.zhihui_community.ui.view.MyGridView;
import com.rain.zhihui_community.ui.view.banner.BannerView;
import com.rain.zhihui_community.utils.DialogLoading;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.EncrypDES3;
import com.rain.zhihui_community.utils.ImageUtils;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.WinToast;
import com.rain.zhihui_community.utils.rxBus.RxBus;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import rx.functions.Action1;


/**
 * author : Rain
 * time : 2017/10/18 0018
 * explain :
 */

public class HomeFragment extends BaseAppFragment {
    @BindView(R.id.title_text)
    TextView title;

    @BindView(R.id.banner)
    BannerView banner;

    @BindView(R.id.gv_data)
    MyGridView gv_data;

    @OnClick(R.id.title_text)
    void select() {
        if (title.getText().toString().equals("请先添加小区")) {
            startActiviys(AddHousingActivity.class);
        } else {
            Intent intent = new Intent(getActivity(), HousingActivity.class);
            intent.putExtra("select_housing", true);
            intent.putExtra("save_housing", true);
            startActivityForResult(intent, 1);
        }
    }

    @OnItemClick(R.id.gv_data)
    void maueItem(int position) {
        switch (position) {
            case 0:
                if (null != myHousing && myHousing.size() != 0) {
                    try {
                        for (MyCommunity community :
                                myHousing) {
                            if (community.isSelect()) {
                                String path = "1," + community.getCommid() + "," + persons.getUser().getPhone() + "," + getTime();
                                String MiPath = getBase64(path);
                                String base64 = EncrypDES3.encode(MiPath);
                                DialogUtil.openDoor(getActivity(), base64);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    startActiviys(AddHousingActivity.class);
                }
                break;
            case 1:
                if (null != myHousing && myHousing.size() != 0) {
                    startActiviys(AppointmentDoorActivity.class);
                } else {
                    startActiviys(AddHousingActivity.class);
                }
                break;
            case 2:
                startActiviys(ReportPoliceActivity.class);
                break;
            case 3:
                startActiviys(LetHouseActivity.class);
                break;
            default:
                WinToast.toast(getActivity(), "点击错误");
                break;
        }
    }

    @OnClick(R.id.rl_home_police_shield)
    void police() {
        startActiviys(SecurityActivity.class);
    }

    @OnClick(R.id.rl_fire_control)
    void fire() {
        if (null != myHousing && myHousing.size() != 0) {
            startActiviys(FireControlActivity.class);
        } else {
            startActiviys(AddHousingActivity.class);
        }
    }

    @OnClick(R.id.fl_home_jiao_police)
    void traffic() {
        startActiviys(TrafficActivity.class);
    }

    @OnClick(R.id.fl_home_community)
    void community() {
        startActiviys(CommunityServiceActivity.class);
    }

    @OnClick(R.id.rl_home_tenement)
    void tenement() {
        if (null != myHousing && myHousing.size() != 0) {
            startActiviys(TenementActivity.class);
        } else {
            startActiviys(AddHousingActivity.class);
        }
    }

    @OnClick(R.id.rl_drop_in)
    void drop() {
        WinToast.toast(getActivity(), "暂未开通,敬请期待");
    }

    List<BannerItems> list = new ArrayList<>();
    private SortButtonAdapter adapter;
    private boolean isSelect = false;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View parentView) {
    }

    @Override
    public void finishCreateView(Bundle bundle) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if (!isVisible || !isPrepared) {
            return;
        }
        isPrepared = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        final DialogLoading loading = new DialogLoading(getActivity());
        loading.showLoading();
        persons = GloData.getPersons();
        myHousing = persons.getMiddleueDTOS();
//        Log.i(TAG, "initData:个人信息 " + persons.toString());
        if (null != myHousing && myHousing.size() != 0) {
            for (MyCommunity myCommunity :
                    myHousing) {
                if (myCommunity.isSelect()) {
                    isSelect = true;
                    title.setText(myCommunity.getCommname());
                }
            }
            if (!isSelect) {
                myHousing.get(0).setSelect(true);
                GloData.getPersons().setMiddleueDTOS(myHousing);
                title.setText(myHousing.get(0).getCommname());
                SharedPreferencesUtil.putString(getActivity(), "usersData", "usersData", new Gson().toJson(GloData.getPersons()));
            }
        } else {
            title.setText("请先添加小区");
        }
//        if (title.getText().toString().equals("请先添加小区")) {
//            if (myHousing.size() == 1) {
//                myHousing.get(0).setSelect(true);
//                GloData.getPersons().setMiddleueDTOS(myHousing);
//                title.setText(myHousing.get(0).getCommname());
//                SharedPreferencesUtil.putString(getActivity(), "usersData", "usersData", new Gson().toJson(GloData.getPersons()));
//            }
//        }
        RetrofitClient.getInstance(getActivity()).createBaseApi().banner(new BaseSubscriber<List<BannerItem>>(getActivity()) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(getActivity(), e.Message);
            }

            @Override
            public void onNext(List<BannerItem> bannerItems) {
                super.onNext(bannerItems);
                list.clear();
                for (int i = 0; i < bannerItems.size(); i++) {
                    BannerItems item = new BannerItems();
                    item.setId(bannerItems.get(i).getId());
                    item.setImage(bannerItems.get(i).getNewsimg());
                    item.setTitle(bannerItems.get(i).getTitle());
                    list.add(item);
                }
                banner.setViewFactory(new BannerViewFactory());
                banner.setDataList(list);
                banner.start();
                loading.dismissLoading();

            }
        });
        adapter = new SortButtonAdapter(getActivity(), setData());
        gv_data.setAdapter(adapter);
        RxBus.getInstance().toObservableSticky(String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s.equals("1")) {
                            DialogUtil.dismissDialog();
                        }
                    }
                });
    }


    public class BannerViewFactory implements BannerView.ViewFactory<BannerItems> {

        @Override
        public View create(final BannerItems bannerItem, int position, ViewGroup container) {
            ImageView iv = new ImageView(container.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageUtils.image(getActivity(), BaseApiService.BASE_URL + bannerItem.image, iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("id", bannerItem.id);
                    intent.putExtra("title", bannerItem.title);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                }
            });
            return iv;
        }
    }

    private List<ButtonModel> setData() {
        List<ButtonModel> data = new ArrayList<>();
        ButtonModel buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.mipmap.icon_open);
        buttonModel.setName("一键开门");
        data.add(buttonModel);

        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.mipmap.icon_open_houe);
        buttonModel.setName("预约开门");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.mipmap.icon_one);
        buttonModel.setName("一键报警");
        data.add(buttonModel);
        buttonModel = new ButtonModel();
        buttonModel.setDrawableIcon(R.mipmap.icon_house);
        buttonModel.setName("出租屋");
        data.add(buttonModel);
        return data;
    }


    public static class BannerItems implements Serializable {
        public String id;
        public String image;
        public String title;

        public void setId(String id) {
            this.id = id;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setTitle(String title) {
            this.title = title;
        }

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
