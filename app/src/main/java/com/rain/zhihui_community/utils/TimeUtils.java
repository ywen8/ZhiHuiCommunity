package com.rain.zhihui_community.utils;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.rain.zhihui_community.R;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * author : Rain
 * time : 2017/10/17 0017
 * explain :
 */

public class TimeUtils {
    private static CountDownTimer timer;
    private static long time = 0;//倒计时

    /**
     * 倒计时
     *
     * @param times
     */
    public static void countDown(long times, final TextView textView, final Activity activity) {
        timer = new CountDownTimer(times, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished / 1000;
                textView.setText(time + " 秒");
                textView.setEnabled(false);
                textView.setTextColor(activity.getResources().getColor(R.color.title_black));
                textView.setBackgroundResource(R.drawable.textview_register_select);

            }

            @Override
            public void onFinish() {
                textView.setText("重新获取验证码");
                textView.setEnabled(true);
                textView.setTextColor(activity.getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.button_login_select_press);
            }
        }.start();
    }

    /**
     * 返回年
     *
     * @return
     */
    public static int getYear() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mYear = c.get(Calendar.YEAR);// 获取当前年份
        return mYear;
    }

    /**
     * 返回月
     *
     * @return
     */
    public static int getMonth() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mMonth = c.get(Calendar.MONTH);// 获取当前月份
        return mMonth;
    }

    /**
     * 返回日
     *
     * @return
     */
    public static int getDay() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        return mDay;
    }

    /**
     * 返回时
     *
     * @return
     */
    public static int getHour() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int hour = c.get(Calendar.HOUR_OF_DAY);
        return hour;

    }

    /**
     * 返回分
     *
     * @return
     */
    public static int getMinute() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int Minute = c.get(Calendar.MINUTE);// 获取当前月份的日期号码
        return Minute;
    }
}
