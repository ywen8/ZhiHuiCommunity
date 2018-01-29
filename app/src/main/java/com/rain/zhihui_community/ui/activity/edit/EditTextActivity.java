package com.rain.zhihui_community.ui.activity.edit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

public class EditTextActivity extends BaseAppAvtivity {

    @BindView(R.id.et_text)
    EditText mText;

    @OnClick(R.id.iv_clear)
    public void clear() {
        mText.setText("");
    }

    @OnClick(R.id.rl_add_housing)
    public void comple() {
        if (type == 3) {
            String roomNumber = mText.getText().toString().trim();
            if (null != roomNumber && !"".equals(roomNumber)) {
                Intent intent = new Intent();
                intent.putExtra("roomNumber", roomNumber);
                setResult(type, intent);
                finish();
            } else {
                DialogUtil.ErrorDilog(this, getString(R.string.edittext_room_number));
            }
        } else if (type == 1) {
            loading.showLoading();
            final String name = mText.getText().toString().trim();
            if (null != name && !"".equals(name)) {
                Map<String, String> map = new HashMap<>();
                map.put("id", persons.getId());
                map.put("username", name);
                RetrofitClient.getInstance(this).createBaseApi().name(map, new BaseSubscriber<ResponseBody>(this) {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        WinToast.toast(EditTextActivity.this, e.getMessage());
                        loading.dismissLoading();
                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        super.onNext(responseBody);
                        String request = responseBody.source().toString();
                        if (request.contains("1000")) {
                            persons.setUsername(name);
                            GloData.getPersons().setUser(persons);
                            SharedPreferencesUtil.putString(EditTextActivity.this, "usersData", "usersData", new Gson().toJson(GloData.getPersons()));
                            DialogUtil.dialog(EditTextActivity.this, "姓名修改成功");
                            DialogUtil.getTv_cancle().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    DialogUtil.dismissDialog();
                                    finish();
                                }
                            });
                        } else {
                            WinToast.toast(EditTextActivity.this, "网络错误");
                        }
                        loading.dismissLoading();
                    }
                });
            } else {
                DialogUtil.ErrorDilog(this, getString(R.string.my_room_name));
            }
        }
    }

    private int type = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        type = getIntent().getIntExtra("type", 1);
        if (type == 3) {
            TitleUtls.TitleTextView(this, getString(R.string.edittext_room_number));
            mText.setHint(getString(R.string.edittext_room_number));
        } else if (type == 1) {
            persons = GloData.getPersons().getUser();
            TitleUtls.TitleTextView(this, "设置名字");
            mText.setText(persons.getUsername());
        }
    }


}
