package com.rain.zhihui_community.ui.activity.wallet;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.WinToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

public class RedPacketActivity extends BaseAppAvtivity {

    private AnimatorSet mFrontAnimator;
    private Animation mAnimationTop, mAnimationBottom, mBackAnimationBottom;
    private Dialog dialog;
    private ImageView image_open;
    private RelativeLayout mTop, mTopIn, mBottom, mBottomIn;
    private LinearLayout ll_text, ll_money;
    private TextView mAppName, mAppData, mAppBround, tv_money, tv_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        persons = GloData.getPersons().getUser();
        bround(this);
        mAnimationTop = AnimationUtils.loadAnimation(this, R.anim.top_translate);
        mAnimationBottom = AnimationUtils.loadAnimation(this, R.anim.bottom_translate);
        mBackAnimationBottom = AnimationUtils.loadAnimation(this, R.anim.bround_bottom_translate);
        mAnimationTop.setFillAfter(true);
        mAnimationBottom.setFillAfter(true);
        mBackAnimationBottom.setFillAfter(true);
        mFrontAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.anim_in);
        setCameraDistance();
        setAnimatorListener();
    }

    public void bround(AppCompatActivity activity) {
        dialog = new Dialog(activity, R.style.MyDialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_bround, null);
        mTop = (RelativeLayout) view.findViewById(R.id.top);
        mTopIn = (RelativeLayout) view.findViewById(R.id.top_in);
        mBottom = (RelativeLayout) view.findViewById(R.id.bottom);
        mBottomIn = (RelativeLayout) view.findViewById(R.id.bottom_in);
        ImageView image_diss = (ImageView) view.findViewById(R.id.iamge_diss);
        mAppName = (TextView) view.findViewById(R.id.tv_app_name);
        mAppData = (TextView) view.findViewById(R.id.tv_app_data);
        mAppBround = (TextView) view.findViewById(R.id.tv_app_bround);
        image_open = (ImageView) view.findViewById(R.id.image_open);
        ll_text = (LinearLayout) view.findViewById(R.id.ll_text);
        ll_money = (LinearLayout) view.findViewById(R.id.ll_money);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        image_diss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });
        image_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();
            }
        });
        WindowManager wm = activity.getWindowManager();
        int mWeight = wm.getDefaultDisplay().getWidth();
        int mHeight = wm.getDefaultDisplay().getHeight();
        dialog.setContentView(view, new ViewGroup.LayoutParams((int) (mWeight * 0.8), (int) (mHeight * 0.6)));
        dialog.show();
    }

    private void startAnim(int state, String str) {
        if (state == 1008) {
            mAppBround.setText(str);
            image_open.setVisibility(View.INVISIBLE);
            return;
        }
        if (state == 1000) {
            double money = Double.parseDouble(str);
            tv_money.setText((money / 100) + "");
            mTopIn.startAnimation(mAnimationTop);
            mBottomIn.startAnimation(mBackAnimationBottom);
            mBottom.startAnimation(mBackAnimationBottom);
            ll_text.startAnimation(mAnimationBottom);
            mAnimationTop.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    image_open.setVisibility(View.INVISIBLE);
                    mTop.setBackgroundResource(R.drawable.linearlayout_dialog_radius);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mAppName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    mAppName.setTextColor(getResources().getColor(R.color.text_color));
                    mAppData.setVisibility(View.GONE);
                    mAppBround.setTextColor(getResources().getColor(R.color.text_color));
                    mAppBround.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    mAppBround.setTextSize(19);
                    ll_money.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            return;
        }
    }

    private CountDownTimer timer = new CountDownTimer(2000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            image_open.setEnabled(false);
            mFrontAnimator.setTarget(image_open);
            mFrontAnimator.start();
        }

        @Override
        public void onFinish() {
            image_open.setEnabled(false);
            RetrofitClient.getInstance(RedPacketActivity.this).createBaseApi().bround(persons.getPhone(), new BaseSubscriber<ResponseBody>(RedPacketActivity.this) {
                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    WinToast.toast(RedPacketActivity.this, "网络异常");
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    super.onNext(responseBody);
                    try {
                        String request = responseBody.string();
                        JSONObject json = new JSONObject(request);
                        int state = json.getInt("code");
                        if (state == 1000) {
                            String str = json.getString("messageBody");
                            startAnim(state, str);
                        } else if (state == 1008) {
                            String str = json.getString("messageBody");
                            startAnim(state, str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void setAnimatorListener() {
        mFrontAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                image_open.setClickable(false);
            }
        });
    }

    private void setCameraDistance() {
        int distance = 6000;
        float scale = getResources().getDisplayMetrics().density * distance;
        image_open.setCameraDistance(scale);
    }
}
