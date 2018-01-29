package com.rain.zhihui_community.ui.fragment.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseFragment;
import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.Persons;
import com.rain.zhihui_community.ui.activity.MainActivity;
import com.rain.zhihui_community.utils.DeviceidUtil;
import com.rain.zhihui_community.utils.DialogLoading;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Rain
 * time : 2017/10/16 0016
 * explain : 注册页面
 */

public class RegisterFragment extends BaseFragment<RegisterContract.IRegisterView, RegisterPresenter> implements RegisterContract.IRegisterView {

    @BindView(R.id.et_mobile)
    EditText mPhone;
    @BindView(R.id.tv_gain_code)
    TextView mGainCode;
    @BindView(R.id.tv_code)
    EditText mCode;
    @BindView(R.id.et_passward)
    EditText mPassward;
    @BindView(R.id.btn_register)
    Button btn_register;
    DialogLoading loading;

    /**
     * 获取短信点击
     */
    @OnClick(R.id.tv_gain_code)
    public void gainCode() {
        presenter.getCode();
    }

    /**
     * 注册点击
     */
    @OnClick(R.id.btn_register)
    public void register() {
        if (mPassward.getText().toString().trim().length() < 6) {
            DialogUtil.ErrorDilog(getActivity(), "密码不得小于6位");
        } else {
            loading.showLoading();
            presenter.toRegister();
        }
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    /**
     * 获取验证码的参数
     *
     * @return
     */
    @Override
    public String getCode() {
        return mPhone.getText().toString().trim();
    }

    @Override
    public int getType() {
        return 1;
    }

    /**
     * 获取验证码的返回
     *
     * @return
     */
    @Override
    public void getCode(String code) {
        if (code.contains("1000")) {
            showToast("验证码已经发出");
            TimeUtils.countDown(60000, mGainCode, getActivity());
        } else if (code.contains("1008")) {
            showToast("该手机已注册");
            mPhone.setHint("请输入手机号码");
            mGainCode.setText("获取验证码");
            mGainCode.setEnabled(false);
            mGainCode.setTextColor(getResources().getColor(R.color.text_color_gary));
            mGainCode.setBackgroundResource(R.drawable.button_login_select_no);
        }
    }

    /**
     * 注册的参数
     *
     * @return
     */
    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", mPhone.getText().toString().trim());
        map.put("checkCode", mCode.getText().toString().trim());
        map.put("password", mPassward.getText().toString().trim());
        map.put("deviceid", DeviceidUtil.deviceid(getActivity()));
        return map;
    }

    public void dissVoid() {
        loading.dismissLoading();
    }

    /**
     * 注册返回
     *
     * @param user
     */
    @Override
    public void onRegisterResult(Persons user) {
        GloData.setPersons(user);
        SharedPreferencesUtil.putString(getActivity(), "usersData", "usersData", new Gson().toJson(user));
        loading.dismissLoading();
        startActiviys(MainActivity.class);
        getActivity().finish();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_register;
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
                if (editable.length() == 11) {
                    mGainCode.setEnabled(true);
                    mGainCode.setBackgroundResource(R.drawable.button_login_select_press);
                    mGainCode.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mGainCode.setEnabled(false);
                    mGainCode.setText("获取验证码");
                    mGainCode.setBackgroundResource(R.drawable.button_login_select_no);
                    mGainCode.setTextColor(getResources().getColor(R.color.login_select_no));
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
                if (editable.length() >= 6) {
                    btn_register.setBackgroundResource(R.drawable.button_login_select_press);
                    btn_register.setTextColor(getResources().getColor(R.color.white));
                    btn_register.setEnabled(true);
                } else {
                    btn_register.setBackgroundResource(R.drawable.button_login_select_no);
                    btn_register.setTextColor(getResources().getColor(R.color.login_select_no));
                    btn_register.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected BaseModule initModule() {
        return new RegisterModule(getActivity());
    }
}
