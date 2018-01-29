package com.rain.zhihui_community.ui.activity.lethouse.issue;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.ImageIssueData;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.imagepicker.ImagePicker;
import com.rain.zhihui_community.imagepicker.bean.ImageItem;
import com.rain.zhihui_community.imagepicker.loader.GlideImageLoader;
import com.rain.zhihui_community.imagepicker.ui.ImageGridActivity;
import com.rain.zhihui_community.ui.activity.ImagePagerActivity;
import com.rain.zhihui_community.ui.activity.village.HousingActivity;
import com.rain.zhihui_community.ui.adapter.IssueImageAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.PowerUtils;
import com.rain.zhihui_community.utils.TitleUtls;
import com.rain.zhihui_community.utils.WinToast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class IssueHouseActivity extends BaseAppAvtivity {
    @BindView(R.id.tv_right_title)
    TextView right_title;
    @BindView(R.id.rl_add_housing)
    RelativeLayout rl_issue;
    @BindView(R.id.gv_image)
    GridView gridView;

    @BindView(R.id.et_house_details)
    EditText et_housing_details;

    @BindView(R.id.tv_lethouse_name)
    TextView tv_lethouse_name;
    private MyCommunity myCommunity;


    @OnClick(R.id.ll_houing_select)
    void select_house() {
        Intent intent = new Intent(this, HousingActivity.class);
        intent.putExtra("select_housing", true);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.rl_add_housing)
    void issue() {
        if (list.size() == 0) {
            WinToast.toast(this, "最少选择一张图片");
        } else {
            loading.showLoading();
            list.remove(issueData);
            String detail = et_housing_details.getText().toString();
            Map<String, RequestBody> map = new HashMap<>();
            RequestBody uid = RequestBody.create(
                    MediaType.parse("multipart/form-data"), persons.getId());
            RequestBody commid = RequestBody.create(
                    MediaType.parse("multipart/form-data"), myCommunity.getCommid());

            RequestBody commname = RequestBody.create(
                    MediaType.parse("multipart/form-data"), myCommunity.getCommname());

            RequestBody details = RequestBody.create(
                    MediaType.parse("multipart/form-data"), detail);
            MultipartBody.Part[] part = new MultipartBody.Part[list.size()];
            for (int i = 0; i < list.size(); i++) {
                ImageIssueData image = list.get(i);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), image.getFile());
                part[i] = MultipartBody.Part.createFormData("file", image.getFile().getName(), requestFile);
            }
            map.put("uid", uid);
            map.put("commid", commid);
            map.put("commname", commname);
            map.put("details", details);
            RetrofitClient.getInstance(this).uploadLet(map, part, new BaseSubscriber<ResponseBody>(this) {
                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    WinToast.toast(IssueHouseActivity.this, e.Message);
                    loading.dismissLoading();
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    super.onNext(responseBody);
                    String request = responseBody.source().toString();
                    if (request.contains("1000")) {
                        WinToast.toast(IssueHouseActivity.this, "发布成功");
                        Intent intent = new Intent();
                        intent.putExtra("issue", 2);
                        setResult(type, intent);
                        finish();
                    } else {
                        WinToast.toast(IssueHouseActivity.this, "发布失败,请重新发布");
                    }
                    loading.dismissLoading();
                }
            });

        }
    }

    @OnItemClick(R.id.gv_image)
    void addimage(int position) {
        ImageIssueData data = (ImageIssueData) adapter.getItem(position);
        if (data.isFile()) {
            if (list.contains(issueData))
                list.remove(issueData);
            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(gridView.getMeasuredWidth(), gridView.getMeasuredHeight());
            ImagePagerActivity.startImagePagerActivity(this, list, position, imageSize);
            list.add(issueData);
        } else {
            if (!PowerUtils.isGrantExternalRW(this)) {
                return;
            }
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());
            imagePicker.setMultiMode(true);
            imagePicker.setShowCamera(true);
            imagePicker.setCrop(false);
            Intent intent = new Intent(IssueHouseActivity.this, ImageGridActivity.class);
            startActivityForResult(intent, 100);
        }
    }

    private ImageIssueData issueData = new ImageIssueData(R.mipmap.issue_house, false);
    private IssueImageAdapter adapter;
    public static LinkedList<ImageIssueData> list = new LinkedList<>();
    private LinkedList<ImageIssueData> fistlist;

    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_house);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.TitleTextView(this, "出租屋");
        right_title.setText("发布");
        rl_issue.setEnabled(false);
        right_title.setTextColor(getResources().getColor(R.color.wathet));
        type = getIntent().getIntExtra("type", 0);
        persons = GloData.getPersons().getUser();
        myHousing = GloData.getPersons().getMiddleueDTOS();
        for (MyCommunity community :
                myHousing) {
            if (community.isSelect()) {
                tv_lethouse_name.setText(community.getCommname());
                myCommunity = community;
            }
        }
        fistlist = new LinkedList<>();
        fistlist.addLast(issueData);
        adapter = new IssueImageAdapter(this, fistlist);
        gridView.setAdapter(adapter);

        et_housing_details.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    rl_issue.setEnabled(true);
                    right_title.setTextColor(getResources().getColor(R.color.white));
                } else {
                    rl_issue.setEnabled(false);
                    right_title.setTextColor(getResources().getColor(R.color.wathet));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (data != null) {
                    ArrayList<ImageItem> mImageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    for (ImageItem item : mImageItems) {
                        File file = new File(item.path);
                        ImageIssueData iamge = new ImageIssueData(file, true);
                        list.add(iamge);
                    }
                    if (list.size() < 9) {
                        if (list.contains(issueData))
                            list.remove(issueData);
                        list.addLast(issueData);
                    } else {
                        list.remove(issueData);
                    }
                    adapter.setImages(list);
                }
                break;
            case 1:
                if (data != null) {
                    myCommunity = (MyCommunity) data.getSerializableExtra("myCommunity");
                    tv_lethouse_name.setText(myCommunity.getCommname());
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        list.clear();
    }
}
