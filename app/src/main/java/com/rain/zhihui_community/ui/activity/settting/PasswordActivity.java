package com.rain.zhihui_community.ui.activity.settting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.activity.login.LoginActivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class PasswordActivity extends BaseAppAvtivity {


    @BindView(R.id.et_worn_password)
    EditText et_worn_password;
    @BindView(R.id.et_new_password)
    EditText et_new_password;
    @BindView(R.id.et_complete_password)
    EditText et_complete_password;
    @BindView(R.id.iv_worn_password)
    ImageView iv_worn_password;
    @BindView(R.id.iv_new_password)
    ImageView iv_new_password;
    @BindView(R.id.iv_complete_password)
    ImageView iv_complete_password;
    @BindView(R.id.btn_complete)
    Button btn_complete;
    private boolean isWorn = false, isNew = true, isComplete = true;


    @OnClick(R.id.iv_worn_password)
    void worn() {
        if (isWorn) {
            isWorn = false;
            iv_worn_password.setImageResource(R.mipmap.visible_password);
            et_worn_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isWorn = true;
            iv_worn_password.setImageResource(R.mipmap.gone_password);
            et_worn_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.iv_new_password)
    void news() {
        if (isNew) {
            isNew = false;
            iv_new_password.setImageResource(R.mipmap.visible_password);
            et_new_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isNew = true;
            iv_new_password.setImageResource(R.mipmap.gone_password);
            et_new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.iv_complete_password)
    void complete_password() {
        if (isComplete) {
            isComplete = false;
            iv_complete_password.setImageResource(R.mipmap.visible_password);
            et_complete_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isComplete = true;
            iv_complete_password.setImageResource(R.mipmap.gone_password);
            et_complete_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.btn_complete)
    void complete() {
        loading.showLoading();
        String worn = et_worn_password.getText().toString().trim();
        String news = et_new_password.getText().toString().trim();
        String complete = et_complete_password.getText().toString().trim();
        if (news.equals(complete)) {
            Map<String, String> map = new HashMap<>();
            map.put("password", worn);
            map.put("newpassword", complete);
            map.put("id", persons.getId());
            RetrofitClient.getInstance(this).createBaseApi().password(map, new BaseSubscriber<ResponseBody>(this) {
                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    loading.dismissLoading();
                    WinToast.toast(PasswordActivity.this, e.getMessage());
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    super.onNext(responseBody);
                    try {
                        String response = responseBody.string();
                        JSONObject json = new JSONObject(response);
                        int state = json.getInt("code");
                        if (state == 1000) {
                            WinToast.toast(PasswordActivity.this, "密码修改成功");
                            AppManager.getAppManager().AppExit(PasswordActivity.this);
                            SharedPreferencesUtil.putString(PasswordActivity.this, "usersData", "usersData", "");
                            finish();
                            startActiviys(LoginActivity.class);
                        } else {
                            WinToast.toast(PasswordActivity.this, json.getString("describe"));
                        }
                        loading.dismissLoading();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            WinToast.toast(this, "两次密码输入不一致");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.TitleItemView(this, "修改密码");
        persons = GloData.getPersons().getUser();
        iv_worn_password.setImageResource(R.mipmap.visible_password);
        et_worn_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        initListener();
    }

    private void initListener() {
        et_worn_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 6 && et_new_password.length() >= 6 && et_complete_password.length() >= 6) {
                    btn_complete.setBackgroundResource(R.drawable.button_login_select_press);
                    btn_complete.setTextColor(getResources().getColor(R.color.white));
                    btn_complete.setEnabled(true);
                } else {
                    btn_complete.setBackgroundResource(R.drawable.button_login_select_no);
                    btn_complete.setTextColor(getResources().getColor(R.color.login_select_no));
                    btn_complete.setEnabled(false);
                }

            }
        });
        et_new_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 6 && et_worn_password.length() >= 6 && et_complete_password.length() >= 6) {
                    btn_complete.setBackgroundResource(R.drawable.button_login_select_press);
                    btn_complete.setTextColor(getResources().getColor(R.color.white));
                    btn_complete.setEnabled(true);
                } else {
                    btn_complete.setBackgroundResource(R.drawable.button_login_select_no);
                    btn_complete.setTextColor(getResources().getColor(R.color.login_select_no));
                    btn_complete.setEnabled(false);
                }
            }
        });
        et_complete_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 6 && et_new_password.length() >= 6 && et_worn_password.length() >= 6) {
                    btn_complete.setBackgroundResource(R.drawable.button_login_select_press);
                    btn_complete.setTextColor(getResources().getColor(R.color.white));
                    btn_complete.setEnabled(true);
                } else {
                    btn_complete.setBackgroundResource(R.drawable.button_login_select_no);
                    btn_complete.setTextColor(getResources().getColor(R.color.login_select_no));
                    btn_complete.setEnabled(false);
                }
            }
        });
    }
}
