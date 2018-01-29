package com.rain.zhihui_community.ui.activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.TitleUtls;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;

public class ScanCodeActivity extends BaseAppAvtivity implements QRCodeView.Delegate {


    private boolean isLight = false;

    @BindView(R.id.zxingview)
    QRCodeView mQRCodeView;
    @BindView(R.id.iv_open_light)
    ImageView iv_open_light;

    @OnClick(R.id.iv_open_light)
    void light() {
        if (isLight) {
            isLight = false;
            mQRCodeView.closeFlashlight();
            iv_open_light.setImageResource(R.drawable.light_off);
        } else {
            isLight = true;
            mQRCodeView.openFlashlight();
            iv_open_light.setImageResource(R.drawable.light_on);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "扫描添加");
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        mQRCodeView.startSpot();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }
}
