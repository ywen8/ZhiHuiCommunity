package com.rain.zhihui_community.ui.activity.village.addhousing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseActivity;
import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.entity.UnitNumber;
import com.rain.zhihui_community.ui.activity.community.CommunityActivity;
import com.rain.zhihui_community.ui.activity.edit.EditTextActivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.TitleUtls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddHousingActivity extends BaseActivity<AddHousingContract.IAdHousingView, AddHousingPresenter> implements AddHousingContract.IAdHousingView {

    @BindView(R.id.tv_my_housing)
    TextView mHousingName;
    @BindView(R.id.tv_unit_num)
    TextView mHousingUnitNum;
    @BindView(R.id.tv_room_number)
    TextView mRoomNumber;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.tv_room_name)
    TextView room_name;
    @BindView(R.id.tv_room_phone)
    TextView room_phone;
    @BindView(R.id.li_room_rener)
    LinearLayout room_rener;
    @BindView(R.id.bt_ower)
    Button bt_ower;
    @BindView(R.id.bt_renter)
    Button bt_renter;
    @BindView(R.id.v_ower)
    View v_ower;
    @BindView(R.id.v_renter)
    View v_renter;

    private Community community;
    private UnitNumber unitNumber;
    private String roomNumber;

    @OnClick(R.id.ll_houing_select)
    public void housing() {
        unitNumber = null;
        mHousingUnitNum.setText(getString(R.string.housing_unit_num));
        startActiviys(CommunityActivity.class, 1);
    }

    @OnClick(R.id.ll_unit_num_select)
    public void unit() {
        if (community != null) {
            Intent intent = new Intent(this, CommunityActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("commid", community.getCommid());
            startActivityForResult(intent, 2);
        } else {
            DialogUtil.ErrorDilog(this, getString(R.string.activity_housing_select));
        }
    }

    @OnClick(R.id.bt_ower)
    public void ower() {
        bt_ower.setTextColor(this.getResources().getColor(R.color.title_black));
        bt_renter.setTextColor(Color.BLACK);
        v_ower.setVisibility(View.VISIBLE);
        v_renter.setVisibility(View.GONE);
        room_rener.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_renter)
    public void renter() {
        bt_ower.setTextColor(Color.BLACK);
        bt_renter.setTextColor(this.getResources().getColor(R.color.title_black));
        v_ower.setVisibility(View.GONE);
        v_renter.setVisibility(View.VISIBLE);
        room_rener.setVisibility(View.GONE);
    }

    @OnClick(R.id.ll_room_number)
    public void room_unit() {
        if (unitNumber == null) {
            DialogUtil.ErrorDilog(this, getString(R.string.activity_room_number));
        } else {
            Intent intent = new Intent(this, EditTextActivity.class);
            intent.putExtra("type", 3);
            startActivityForResult(intent, 3);
        }
    }

    @OnClick(R.id.btn_submit)
    public void submit() {
        presenter.addHousing();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_housing);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "添加小区");
        persons = GloData.getPersons().getUser();
        myHousing = GloData.getPersons().getMiddleueDTOS();
        if (null == myHousing) {
            myHousing = new ArrayList<>();
        }
        initView();
    }

    private void initView() {
        room_name.setText(persons.getUsername());
        room_phone.setText(persons.getPhone());
    }

    @Override
    protected AddHousingPresenter initPresenter() {
        return new AddHousingPresenter();
    }

    @Override
    protected BaseModule initModule() {
        return new AddHousingModule(this);
    }

    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", persons.getId());
        map.put("cid", community.getCid());
        map.put("unitnumber", unitNumber.getGatename());
        map.put("roomnumber", roomNumber);
        map.put("sign", 2 + "");
        return map;
    }

    @Override
    public void onHousingResult(MyCommunity request) {
        if (null == myHousing) {
            request.setSelect(true);
        }
        myHousing.add(request);
        GloData.getPersons().setMiddleueDTOS(myHousing);
        SharedPreferencesUtil.putString(AddHousingActivity.this, "usersData", "usersData", new Gson().toJson(GloData.getPersons()));
        DialogUtil.dialog(this, "正在审核");
        DialogUtil.getTv_cancle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.dismissDialog();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    community = (Community) data.getSerializableExtra("community");
                    mHousingName.setText(community.getCommname());
                }
                break;
            case 2:
                if (data != null) {
                    unitNumber = (UnitNumber) data.getSerializableExtra("unitNumber");
                    mHousingUnitNum.setText(unitNumber.getGatename());
                }
                break;
            case 3:
                if (data != null) {
                    roomNumber = data.getStringExtra("roomNumber");
                    mRoomNumber.setText(roomNumber);
                    btn_submit.setBackgroundResource(R.drawable.button_login_select_press);
                    btn_submit.setTextColor(getResources().getColor(R.color.white));
                    btn_submit.setEnabled(true);
                }
                break;
        }
    }
}
