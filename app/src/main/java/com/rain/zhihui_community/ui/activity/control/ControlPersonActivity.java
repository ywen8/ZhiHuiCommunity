package com.rain.zhihui_community.ui.activity.control;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.ControlPerson;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.adapter.ControlPersonAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class ControlPersonActivity extends BaseAppAvtivity {

    private MyCommunity myCommunity;

    private Map<String, List<ControlPerson>> map = new HashMap<>();

    @BindView(R.id.tv_control_person)
    TextView tv_control_person;
    @BindView(R.id.tv_control_management)
    TextView tv_control_management;
    @BindView(R.id.tv_control_phone)
    TextView tv_control_phone;
    @BindView(R.id.gv_control)
    GridView gv_control;
    @BindView(R.id.gv_building)
    GridView gv_building;
    @BindView(R.id.sc_view)
    ScrollView sc_view;

    ControlPersonAdapter adapter;
    ControlPersonAdapter conAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_person);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "社区消防员");
        AppManager.getAppManager().addActivity(this);
        sc_view.smoothScrollTo(0, 20);
        adapter = new ControlPersonAdapter(this);
        gv_control.setAdapter(adapter);
        conAdapter = new ControlPersonAdapter(this);
        gv_building.setAdapter(conAdapter);
        myHousing = GloData.getPersons().getMiddleueDTOS();
        for (MyCommunity community :
                myHousing) {
            if (community.isSelect()) {
                myCommunity = community;
            }
        }
        RetrofitClient.getInstance(this).createBaseApi().postControlPerson(myCommunity.getCommid(), new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(ControlPersonActivity.this, e.Message);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                try {
                    String request = responseBody.string();
                    JSONObject json = new JSONObject(request);
                    JSONObject jsonObject = json.getJSONObject("messageBody");
                    Iterator<String> keys = jsonObject.keys();
                    Gson gson = new Gson();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        String datas = jsonObject.getString(key);
                        TypeToken<List<ControlPerson>> token = new TypeToken<List<ControlPerson>>() {
                        };
                        List<ControlPerson> controlPerson = gson.fromJson(datas, token.getType());
                        map.put(key, controlPerson);
                    }
                    if (null != map.get("0").get(0)) {
                        tv_control_person.setText(map.get("0").get(0).getName());
                    }
                    if (null != map.get("1").get(0)) {
                        tv_control_management.setText(map.get("1").get(0).getName());
                        tv_control_phone.setText(map.get("1").get(0).getPhone());
                    }
                    adapter.setList(map.get("2"));
                    conAdapter.setList(map.get("3"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
