package com.rain.zhihui_community.ui.activity.wallet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class DepositActivity extends BaseAppAvtivity {

    @BindView(R.id.et_money)
    EditText et_money;
    @BindView(R.id.et_acc)
    EditText et_acc;
    @BindView(R.id.tv_max)
    TextView tv_max;
    @BindView(R.id.tv_max_money)
    TextView tv_max_money;
    private double mMoney = 0.00;
    DecimalFormat df = new DecimalFormat("######0.00");

    @OnClick(R.id.btn_deposit)
    void deposit() {
        String money = et_money.getText().toString().trim();
        String apply = et_acc.getText().toString().trim();
        if (apply.isEmpty()) {
            WinToast.toast(this, "请输入支付宝账号");
            return;
        }
        if (Double.parseDouble(money) > mMoney) {
            DialogUtil.dialog(this, "转出金额超限");
            DialogUtil.getTv_cancle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtil.dismissDialog();
                }
            });
        } else {
            if (money.substring(0, 1).equals(".")) {
                DialogUtil.dialog(this, "输入金额有误");
                DialogUtil.getTv_cancle().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogUtil.dismissDialog();
                    }
                });
            } else {
                depositMoney(apply, money);
            }
        }
    }

    private void depositMoney(String apply, String money) {
        double m = Double.parseDouble(money);
        int mo = (int) (m * 100);
        Map<String, String> map = new HashMap<>();
        map.put("alipay", apply);
        map.put("phone", persons.getPhone());
        map.put("money", mo + "");
        RetrofitClient.getInstance(this).createBaseApi().deposit(map, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(DepositActivity.this, "网络错误");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                try {
                    String request = responseBody.string();
                    JSONObject json = new JSONObject(request);
                    int state = json.getInt("code");
                    if (state == 1000) {
                        DialogUtil.dialog(DepositActivity.this, "处理中...\n预计24小时之内到账，周末节假日推迟。");
                        DialogUtil.getTv_cancle().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogUtil.dismissDialog();
                                finish();
                            }
                        });
                    } else {
                        DialogUtil.dialog(DepositActivity.this, "提现失败");
                        DialogUtil.getTv_cancle().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogUtil.dismissDialog();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        persons = GloData.getPersons().getUser();
        TitleUtls.TitleItemView(this, "提现");
        mMoney = getIntent().getDoubleExtra("money", 0.00);
        et_money.setText(df.format(mMoney));
        tv_max_money.setText("最高可提现" + df.format(mMoney) + "元");
        initListener();
    }

    private void initListener() {
        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        et_money.setText(s);
                        et_money.setSelection(s.length());
                    }
                }
//                if (s.toString().trim().substring(0).equals(".")) {
//                    s = "0" + s;
//                    et_money.setText(s);
//                    et_money.setSelection(2);
//                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et_money.setText(s.subSequence(0, 1));
                        et_money.setSelection(1);
                        return;
                    }
                }
                if (Double.parseDouble(s.toString()) > mMoney) {
                    tv_max.setVisibility(View.VISIBLE);
                } else {
                    tv_max.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
