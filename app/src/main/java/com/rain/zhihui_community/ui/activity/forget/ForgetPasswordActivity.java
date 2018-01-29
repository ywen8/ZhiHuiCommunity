package com.rain.zhihui_community.ui.activity.forget;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseActivity;
import com.rain.zhihui_community.base.BaseModule;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.TimeUtils;
import com.rain.zhihui_community.utils.TitleUtls;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgetPasswordActivity extends BaseActivity<ForgetContract.IForgetView, ForgetPresenter> implements ForgetContract.IForgetView {

    @BindView(R.id.et_mobile)
    EditText mPhone;
    @BindView(R.id.tv_gain_code)
    TextView mGainCode;
    @BindView(R.id.tv_code)
    EditText mCode;
    @BindView(R.id.et_passward)
    EditText mPassward;
    @BindView(R.id.btn_complete)
    Button btn_complete;


    @OnClick(R.id.tv_gain_code)
    public void gainCode() {
        TimeUtils.countDown(60000, mGainCode, this);
        presenter.getCode();
    }

    @OnClick(R.id.btn_complete)
    public void complete() {
        if (mPassward.getText().toString().trim().length() < 6) {
            DialogUtil.ErrorDilog(this, "密码不得小于6位");
        } else {
            presenter.toForget();
        }
    }

    @Override
    public String getCode() {
        return mPhone.getText().toString().trim();
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void getCode(String result) {
        if (result.contains("1000")) {
            showToast("验证码已经发出");
        }
    }

    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", mPhone.getText().toString().trim());
        map.put("checkCode", mCode.getText().toString().trim());
        map.put("password", mPassward.getText().toString().trim());
        return map;
    }

    @Override
    public void onForgetResult(String request) {
        if (request.contains("1000")) {
            showToast("密码修改成功");
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        TitleUtls.TitleView(this, "找回密码");
        initData();
    }

    @Override
    protected ForgetPresenter initPresenter() {
        return new ForgetPresenter();
    }

    @Override
    protected BaseModule initModule() {
        return new ForgetModule(this);
    }

    private void initData() {
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
                    mGainCode.setBackgroundResource(R.drawable.button_login_select_no);
                    mGainCode.setTextColor(getResources().getColor(R.color.login_select_no));
                }
            }
        });
        mCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {

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
                if (editable.length() > 0) {
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
