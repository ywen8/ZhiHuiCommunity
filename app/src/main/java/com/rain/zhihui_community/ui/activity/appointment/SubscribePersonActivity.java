package com.rain.zhihui_community.ui.activity.appointment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.ui.activity.village.HousingActivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
import com.rain.zhihui_community.utils.TimeUtils;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;


public class SubscribePersonActivity extends BaseAppAvtivity {


    @BindView(R.id.et_sub_mobile)
    EditText et_mobile;
    @BindView(R.id.tv_my_housing)
    TextView tv_housing;
    @BindView(R.id.tv_sub_time)
    TextView tv_time;
    @BindView(R.id.btn_sub)
    Button btn_sub;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    private static final String STATE_TEXTVIEW = "STATE_TEXTVIEW";

    MyCommunity myCommunity;
    private SwitchDateTimeDialogFragment dateTimeFragment;

    String time;

    @OnClick(R.id.ll_houing_select)
    void selectHousing() {
        Intent intent = new Intent(this, HousingActivity.class);
        intent.putExtra("select_housing", true);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.ll_sub_time)
    void time() {
        if (myCommunity != null) {
            dateTimeFragment.startAtCalendarView();
            dateTimeFragment.setDefaultDateTime(new GregorianCalendar(TimeUtils.getYear(), TimeUtils.getMonth(), TimeUtils.getDay(), TimeUtils.getHour(), TimeUtils.getMinute()).getTime());
            dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
        } else {
            DialogUtil.ErrorDilog(this, getString(R.string.activity_housing_select));
        }
    }

    @OnClick(R.id.et_sub_mobile)
    void mobile() {
        if (tv_time.getText().toString().length() > 0) {
            et_mobile.setFocusable(true);
            et_mobile.setFocusableInTouchMode(true);
            et_mobile.requestFocus();
            et_mobile.setHint("");
        } else {
            DialogUtil.ErrorDilog(this, getString(R.string.sub_time));
        }
    }

    @OnClick(R.id.btn_sub)
    void sub() {
        loading.showLoading();
        Map<String, String> map = new HashMap<>();
        map.put("mid", myCommunity.getId());
        map.put("begindate", time);
        map.put("uid", persons.getId());
        map.put("phone", et_mobile.getText().toString().trim());
        RetrofitClient.getInstance(this).createBaseApi().appointment(map, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(SubscribePersonActivity.this, e.Message);
                loading.dismissLoading();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                String request = responseBody.source().toString();
                if (request.contains("1000")) {
                    DialogUtil.hintDilog(SubscribePersonActivity.this, "预约成功有效期为一个小时");
                    DialogUtil.getTv_sure().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DialogUtil.dismissDialog();
                            finish();
                        }
                    });
                } else {
                    WinToast.toast(SubscribePersonActivity.this, "预约失败");
                }
                loading.dismissLoading();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_person);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.TitleItemView(this, "预约开门");
        if (savedInstanceState != null) {
            // Restore value from saved state
            tv_time.setText(savedInstanceState.getCharSequence(STATE_TEXTVIEW));
        }
        persons = GloData.getPersons().getUser();
        initData();
        initListener();
    }

    private void initListener() {
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 11) {
                    btn_sub.setBackgroundResource(R.drawable.button_login_select_press);
                    btn_sub.setTextColor(getResources().getColor(R.color.white));
                    btn_sub.setEnabled(true);
                } else {
                    btn_sub.setBackgroundResource(R.drawable.button_login_select_no);
                    btn_sub.setTextColor(getResources().getColor(R.color.login_select_no));
                    btn_sub.setEnabled(false);
                }
            }
        });
    }

    private void initData() {
        et_mobile.setSelection(et_mobile.getText().length());

        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel),
                    getString(R.string.clean) // Optional
            );
        }
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        // Assign unmodifiable values
        dateTimeFragment.set24HoursMode(true);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2100, Calendar.DECEMBER, 31).getTime());

        // Define new day and month format
        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e(TAG, e.getMessage());
        }

        // Set listener for date
        // Or use dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                time = myDateFormat.format(date);
                tv_time.setText(time);
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
                tv_time.setText("");
                btn_sub.setBackgroundResource(R.drawable.button_login_select_no);
                btn_sub.setTextColor(getResources().getColor(R.color.login_select_no));
                btn_sub.setEnabled(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (data != null) {
                    myCommunity = (MyCommunity) data.getSerializableExtra("myCommunity");
                    tv_housing.setText(myCommunity.getCommname());
                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the current textView
        savedInstanceState.putCharSequence(STATE_TEXTVIEW, tv_time.getText());
        super.onSaveInstanceState(savedInstanceState);
    }
}
