package com.rain.zhihui_community.ui.fragment.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseFragment;
import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.Persons;
import com.rain.zhihui_community.ui.activity.MainActivity;
import com.rain.zhihui_community.ui.activity.forget.ForgetPasswordActivity;
import com.rain.zhihui_community.utils.DeviceidUtil;
import com.rain.zhihui_community.utils.DialogLoading;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.WinToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * author : Rain
 * time : 2017/10/16 0016
 * explain :
 */

public class LoginFragment extends BaseFragment<LoginContract.ILoginView, LoginPresenter> implements LoginContract.ILoginView {


    @BindView(R.id.et_mobile)
    EditText mPhone;
    @BindView(R.id.et_passward)
    EditText mPassward;
    @BindView(R.id.btn_login)
    Button btn_login;
    DialogLoading loading;
    @BindView(R.id.tv_hint_error)
    TextView tv_hint_error;

    @OnClick(R.id.et_mobile)
    void mobile() {
        mPassward.setFocusable(false);
        if (mPassward.getText().toString().length() == 0) {
            mPassward.setHint("请输入6位以上密码");
        }
        mPhone.setFocusable(true);
        mPhone.setHint("");
        mPhone.setFocusableInTouchMode(true);
        mPhone.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @OnClick(R.id.et_passward)
    void passward() {
        mPhone.setFocusable(false);
        if (mPhone.getText().toString().length() == 0) {
            mPhone.setHint("请输入手机号码");
        }
        mPassward.setFocusable(true);
        mPassward.setHint("");
        mPassward.setFocusableInTouchMode(true);
        mPassward.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @OnClick(R.id.tv_forgt_pwd)
    public void forgt() {
        startActiviys(ForgetPasswordActivity.class);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        loading.showLoading();
        presenter.toLogin();
    }

    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", mPhone.getText().toString().trim());
        map.put("password", mPassward.getText().toString().trim());
        map.put("deviceid", DeviceidUtil.deviceid(getActivity()));
        return map;
    }

    @Override
    public void onLoginResult(ResponseBody user) {

        try {
            String request = user.string();
            JSONObject json = new JSONObject(request);
            int state = json.getInt("code");
            if (state == 1000) {
                String str = json.getString("messageBody");
                Gson gson = new Gson();
                TypeToken<Persons> token = new TypeToken<Persons>() {
                };
                Persons persons = gson.fromJson(str, token.getType());
                GloData.setPersons(persons);
                SharedPreferencesUtil.putString(getActivity(), "usersData", "usersData", new Gson().toJson(user));
                startActiviys(MainActivity.class);
                getActivity().finish();
            } else {
                WinToast.toast(getActivity(), json.getString("describe"));
            }
            loading.dismissLoading();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_login;
    }

    public void dissVoid() {
        loading.dismissLoading();
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
        initData();
    }

    private void initData() {
        loading = new DialogLoading(getActivity());
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 11 && mPassward.length() > 0) {
                    tv_hint_error.setVisibility(View.GONE);
                    btn_login.setBackgroundResource(R.drawable.button_login_select_press);
                    btn_login.setTextColor(getResources().getColor(R.color.white));
                    btn_login.setEnabled(true);
                } else {
                    if (editable.length() < 11) {
                        tv_hint_error.setVisibility(View.VISIBLE);
                    } else if (editable.length() == 11) {
                        tv_hint_error.setVisibility(View.GONE);
                    }
                    btn_login.setBackgroundResource(R.drawable.button_login_select_no);
                    btn_login.setTextColor(getResources().getColor(R.color.login_select_no));
                    btn_login.setEnabled(false);
                }
            }
        });
        mPassward.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && mPhone.length() == 11) {
                    tv_hint_error.setVisibility(View.GONE);
                    btn_login.setBackgroundResource(R.drawable.button_login_select_press);
                    btn_login.setTextColor(getResources().getColor(R.color.white));
                    btn_login.setEnabled(true);
                } else {
                    if (mPhone.length() < 11) {
                        tv_hint_error.setVisibility(View.VISIBLE);
                    }
                    if (editable.length() == 0) {
                        mPassward.setHint("请输入6位以上密码");
                    }
                    btn_login.setBackgroundResource(R.drawable.button_login_select_no);
                    btn_login.setTextColor(getResources().getColor(R.color.login_select_no));
                    btn_login.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected BaseModule initModule() {
        return new LoginModule(getActivity());
    }

}
