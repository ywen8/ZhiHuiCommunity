package com.rain.zhihui_community.ui.activity.information;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.http.BaseApiService;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.imagepicker.ImagePicker;
import com.rain.zhihui_community.imagepicker.bean.ImageItem;
import com.rain.zhihui_community.imagepicker.loader.GlideImageLoader;
import com.rain.zhihui_community.imagepicker.ui.ImageGridActivity;
import com.rain.zhihui_community.ui.activity.edit.EditTextActivity;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.ImageUtils;
import com.rain.zhihui_community.utils.PowerUtils;
import com.rain.zhihui_community.utils.SharedPreferencesUtil;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class InformationActivity extends BaseAppAvtivity {

    @BindView(R.id.iv_information_photo)
    ImageView mImageView;
    @BindView(R.id.tv_information_name)
    TextView mName;
    @BindView(R.id.tv_information_sex)
    TextView mSex;

    ArrayList<ImageItem> mImageItems;

    @OnClick(R.id.ll_information_photo)
    public void photo() {
        if (!PowerUtils.isGrantExternalRW(this)) {
            return;
        }
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(true);
        imagePicker.setMultiMode(false);
        Intent intent = new Intent(InformationActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, 100);

    }

    @OnClick(R.id.ll_information_name)
    public void name() {
        startActiviys(EditTextActivity.class);
    }

    @OnClick(R.id.ll_information_sex)
    public void sex() {
        startActiviys(SexActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, getString(R.string.information_title));
        initData();
    }

    private void initData() {
        persons = GloData.getPersons().getUser();
        mName.setText(persons.getUsername());
        if (persons.getSex() != null) {
            if (persons.getSex().equals("0")) {
                mSex.setText("男");
            } else if (persons.getSex().equals("1")) {
                mSex.setText("女");
            }
        }
        if (persons.getHeadimg() != null) {
            ImageUtils.imageCircle(this, BaseApiService.BASE_URL + persons.getHeadimg(), mImageView);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (data != null) {
                    mImageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    Log.i(TAG, "onActivityResult: " + mImageItems.toString());
                    String path = mImageItems.get(0).path;
                    File file = new File(path);
                    loading.showLoading();
                    uploading(file);
                }

                break;
        }
    }

    private void uploading(final File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody idBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), persons.getId());
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("id", idBody);
        RetrofitClient.getInstance(this).createBaseApi().photo(map, filePart, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                WinToast.toast(InformationActivity.this, e.getMessage());
                loading.dismissLoading();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                try {
                    String response = responseBody.string();
                    JSONObject json = new JSONObject(response);
                    int state = json.getInt("code");
                    if (state == 1000) {
                        String str = json.getString("messageBody");
                        persons.setHeadimg(str);
                        SharedPreferencesUtil.putString(InformationActivity.this, "usersData", "usersData", new Gson().toJson(GloData.getPersons()));
                        ImageUtils.imageCircle(InformationActivity.this, file, mImageView);
                        WinToast.toast(InformationActivity.this, "头像修改成功");
                    } else {
                        WinToast.toast(InformationActivity.this, "头像修改失败");
                    }
                    loading.dismissLoading();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
