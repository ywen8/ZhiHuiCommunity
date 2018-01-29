package com.rain.zhihui_community.ui.activity.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.Deposit;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.TitleUtls;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends BaseAppAvtivity {


    @BindView(R.id.tv_money)
    TextView tv_money;
    private double money = 0.00;

    @OnClick(R.id.btn_deposit)
    void btn_deposit() {
        if (tv_money.getText().toString().contains("0.00")) {
            DialogUtil.dialog(WalletActivity.this, "当前无金额可以提现,可以通过开门抢红包得到。");
            DialogUtil.getTv_cancle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtil.dismissDialog();
                }
            });
            return;
        }
        Intent intent = new Intent(this, DepositActivity.class);
        intent.putExtra("money", money);
        startActivity(intent);
    }

    DecimalFormat df = new DecimalFormat("######0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "我的钱包");
        persons = GloData.getPersons().getUser();
        deposit();
    }

    void deposit(){
        loading.showLoading();
        RetrofitClient.getInstance(this).createBaseApi().money(persons.getPhone(), new BaseSubscriber<Deposit>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                loading.dismissLoading();
                tv_money.setText("¥ " + df.format(money));
            }

            @Override
            public void onNext(Deposit deposit) {
                super.onNext(deposit);
                money = deposit.getMoney() * 0.01;
                tv_money.setText("¥ " + df.format(money));
                loading.dismissLoading();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        deposit();
    }
}
