package com.rain.zhihui_community.ui.activity.life;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.rain.zhihui_community.R;
import com.rain.zhihui_community.base.BaseAppAvtivity;
import com.rain.zhihui_community.entity.GloData;
import com.rain.zhihui_community.entity.ImageIssueData;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.entity.UnitNumber;
import com.rain.zhihui_community.http.BaseSubscriber;
import com.rain.zhihui_community.http.ExceptionHandle;
import com.rain.zhihui_community.http.RetrofitClient;
import com.rain.zhihui_community.imagepicker.ImagePicker;
import com.rain.zhihui_community.imagepicker.bean.ImageItem;
import com.rain.zhihui_community.imagepicker.loader.GlideImageLoader;
import com.rain.zhihui_community.imagepicker.ui.ImageGridActivity;
import com.rain.zhihui_community.ui.activity.ImagePagerActivity;
import com.rain.zhihui_community.ui.activity.community.CommunityActivity;
import com.rain.zhihui_community.ui.activity.edit.EditTextActivity;
import com.rain.zhihui_community.ui.activity.lethouse.issue.IssueHouseActivity;
import com.rain.zhihui_community.ui.activity.village.HousingActivity;
import com.rain.zhihui_community.ui.adapter.IssueImageAdapter;
import com.rain.zhihui_community.utils.AppManager;
import com.rain.zhihui_community.utils.DialogUtil;
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

public class RepairsActivity extends BaseAppAvtivity {


    private MyCommunity myCommunity;
    @BindView(R.id.tv_my_housing)
    TextView tv_my_housing;
    @BindView(R.id.tv_unit_num)
    TextView mHousingUnitNum;
    @BindView(R.id.tv_room_number)
    TextView mRoomNumber;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.et_house_details)
    EditText et_house_details;
    @BindView(R.id.gv_image)
    GridView gridView;

    private UnitNumber unitNumber;
    private String roomNumber;

    private ImageIssueData issueData = new ImageIssueData(R.mipmap.issue_house, false);
    private IssueImageAdapter adapter;

    private LinkedList<ImageIssueData> fistlist;

    @OnClick(R.id.ll_houing_select)
    void select_housing() {
        Intent intent = new Intent(this, HousingActivity.class);
        intent.putExtra("select_housing", true);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.ll_unit_num_select)
    public void unit() {
        if (myCommunity != null) {
            Intent intent = new Intent(this, CommunityActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("commid", myCommunity.getCommid());
            startActivityForResult(intent, 2);
        } else {
            DialogUtil.ErrorDilog(this, getString(R.string.activity_housing_select));
        }
    }

    @OnClick(R.id.ll_room_number)
    public void room_unit() {
        if (unitNumber == null) {
            DialogUtil.ErrorDilog(this, getString(R.string.activity_room_number));
        } else {
            Intent intent = new Intent(this, EditTextActivity.class);
            intent.putExtra("type", 3);
            startActivityForResult(intent, 3);
        }
    }

    @OnItemClick(R.id.gv_image)
    void addimage(int position) {
        ImageIssueData data = (ImageIssueData) adapter.getItem(position);
        if (data.isFile()) {
            if (IssueHouseActivity.list.contains(issueData))
                IssueHouseActivity.list.remove(issueData);
            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(gridView.getMeasuredWidth(), gridView.getMeasuredHeight());
            ImagePagerActivity.startImagePagerActivity(this, IssueHouseActivity.list, position, imageSize);
            IssueHouseActivity.list.add(issueData);
        } else {
            if (!PowerUtils.isGrantExternalRW(this)) {
                return;
            }
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());
            imagePicker.setShowCamera(true);
            imagePicker.setCrop(false);
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, 100);
        }
    }

    @OnClick(R.id.btn_submit)
    void subimt() {
        if (IssueHouseActivity.list.size() == 0) {
            WinToast.toast(this, "最少选择一张图片");
        } else {
            loading.showLoading();
            IssueHouseActivity.list.remove(issueData);
            String detail = et_house_details.getText().toString();
            Map<String, RequestBody> map = new HashMap<>();
            RequestBody uid = RequestBody.create(
                    MediaType.parse("multipart/form-data"), persons.getId());
            RequestBody commid = RequestBody.create(
                    MediaType.parse("multipart/form-data"), myCommunity.getCommid());
            RequestBody unitname = RequestBody.create(
                    MediaType.parse("multipart/form-data"), unitNumber.getGatename());
            RequestBody room = RequestBody.create(
                    MediaType.parse("multipart/form-data"), roomNumber);
            RequestBody remarks = RequestBody.create(
                    MediaType.parse("multipart/form-data"), detail);
            MultipartBody.Part[] part = new MultipartBody.Part[IssueHouseActivity.list.size()];
            for (int i = 0; i < IssueHouseActivity.list.size(); i++) {
                ImageIssueData image = IssueHouseActivity.list.get(i);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), image.getFile());
                part[i] = MultipartBody.Part.createFormData("file", image.getFile().getName(), requestFile);
            }
            map.put("uid", uid);
            map.put("commid", commid);
            map.put("unit", unitname);
            map.put("room", room);
            map.put("remarks", remarks);
            RetrofitClient.getInstance(this).createBaseApi().uploadRepair(map, part, new BaseSubscriber<ResponseBody>(this) {
                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    WinToast.toast(RepairsActivity.this, e.Message);
                    loading.dismissLoading();
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    super.onNext(responseBody);
                    String request = responseBody.source().toString();
                    if (request.contains("1000")) {
                        WinToast.toast(RepairsActivity.this, "提交成功");
                        finish();
                    } else if (request.contains("1008")) {
                        WinToast.toast(RepairsActivity.this, "请不要重复提交报修");
                    } else {
                        WinToast.toast(RepairsActivity.this, "提交失败,请重新提交");
                    }
                    loading.dismissLoading();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairs);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.setIsShow(false);
        TitleUtls.TitleItemView(this, "物业报修");
        fistlist = new LinkedList<>();
        fistlist.addLast(issueData);
        persons = GloData.getPersons().getUser();
        adapter = new IssueImageAdapter(this, fistlist);
        gridView.setAdapter(adapter);
        et_house_details.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    btn_submit.setEnabled(true);
                    btn_submit.setBackgroundResource(R.drawable.button_login_select_press);
                    btn_submit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    btn_submit.setBackgroundResource(R.drawable.button_login_select_no);
                    btn_submit.setTextColor(getResources().getColor(R.color.login_select_no));
                    btn_submit.setEnabled(false);
                }
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
                    tv_my_housing.setText(myCommunity.getCommname());
                }
                break;
            case 2:
                if (data != null) {
                    unitNumber = (UnitNumber) data.getSerializableExtra("unitNumber");
                    mHousingUnitNum.setText(unitNumber.getGatename());
                }
                break;
            case 3:
                if (data != null) {
                    roomNumber = data.getStringExtra("roomNumber");
                    mRoomNumber.setText(roomNumber);
                }
                break;
            case 100:
                if (data != null) {
                    ArrayList<ImageItem> mImageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    for (ImageItem item : mImageItems) {
                        File file = new File(item.path);
                        ImageIssueData iamge = new ImageIssueData(file, true);
                        IssueHouseActivity.list.add(iamge);
                    }
                    if (IssueHouseActivity.list.size() < 9) {
                        if (IssueHouseActivity.list.contains(issueData))
                            IssueHouseActivity.list.remove(issueData);
                        IssueHouseActivity.list.addLast(issueData);
                    } else {
                        IssueHouseActivity.list.remove(issueData);
                    }
                    adapter.setImages(IssueHouseActivity.list);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IssueHouseActivity.list.clear();
    }
}
