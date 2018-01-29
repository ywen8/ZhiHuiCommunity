package com.rain.zhihui_community.ui.activity.information;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class SexActivity extends BaseAppAvtivity {


    @BindView(R.id.iv_boy)
    ImageView iv_boy;
    @BindView(R.id.iv_girl)
    ImageView iv_girl;

    private String sex;


    @OnClick(R.id.rl_add_housing)
    public void comple() {
        Map<String, String> map = new HashMap<>();
        map.put("id", persons.getId());
        map.put("sex", sex);
        RetrofitClient.getInstance(this).createBaseApi().sex(map, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(SexActivity.this, e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                String request = responseBody.source().toString();
                if (request.contains("1000")) {
                    persons.setSex(sex);
                    GloData.getPersons().setUser(persons);
                    SharedPreferencesUtil.putString(SexActivity.this, "usersData", "usersData", new Gson().toJson(GloData.getPersons()));
                    DialogUtil.dialog(SexActivity.this, "性别修改成功");
                    DialogUtil.getTv_cancle().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtil.dismissDialog();
                            finish();
                        }
                    });
                } else {
                    WinToast.toast(SexActivity.this, "网络错误");
                }
            }
        });
    }

    @OnClick(R.id.ll_information_boy)
    public void boy() {
        boys();
    }

    public void boys() {
        sex = "0";
        iv_boy.setVisibility(View.VISIBLE);
        iv_girl.setVisibility(View.GONE);
    }

    @OnClick(R.id.ll_information_girl)
    public void girl() {
        girls();
    }

    public void girls() {
        sex = "1";
        iv_boy.setVisibility(View.GONE);
        iv_girl.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.TitleTextView(this, "设置性别");
        persons = GloData.getPersons().getUser();
        if (persons.getSex() != null) {
            if (persons.getSex().equals("0")) {
                boys();
            } else if (persons.getSex().equals("1")) {
                girls();
            }
        }
    }
}
