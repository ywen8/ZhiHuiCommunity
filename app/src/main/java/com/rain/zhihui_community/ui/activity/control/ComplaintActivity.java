package com.rain.zhihui_community.ui.activity.control;

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
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 提交隐患
 */
public class ComplaintActivity extends BaseAppAvtivity {


    @BindView(R.id.tv_my_housing)
    TextView tv_my_housing;
    @BindView(R.id.tv_unit_num)
    TextView tv_unit_num;
    @BindView(R.id.et_sub_address)
    EditText et_sub_address;
    @BindView(R.id.et_house_details)
    EditText et_house_details;
    @BindView(R.id.gv_image)
    GridView gv_image;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.tv_details_one)
    TextView tv_details_one;
    @BindView(R.id.tv_details_two)
    TextView tv_details_two;
    @BindView(R.id.tv_details_three)
    TextView tv_details_three;
    @BindView(R.id.tv_details_four)
    TextView tv_details_four;
    @BindView(R.id.tv_details_five)
    TextView tv_details_five;
    @BindView(R.id.tv_details_six)
    TextView tv_details_six;
    private MyCommunity myCommunity;
    private UnitNumber unitNumber;
    private boolean one = false, two = false, three = false, four = false, five = false, six = false;
    private ImageIssueData issueData = new ImageIssueData(R.mipmap.issue_house, false);
    private IssueImageAdapter adapter;

    private LinkedList<ImageIssueData> fistlist;
    private List<String> type = new ArrayList<>();

    @OnClick(R.id.ll_houing_select)
    void select_housing() {
        unitNumber = null;
        tv_unit_num.setText(getString(R.string.housing_unit_num));
        Intent intent = new Intent(this, HousingActivity.class);
        intent.putExtra("select_housing", true);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.ll_unit_num_select)
    void unit_num() {
        if (myCommunity != null) {
            Intent intent = new Intent(this, CommunityActivity.class);
            intent.putExtra("type", 2);
            intent.putExtra("commid", myCommunity.getCommid());
            startActivityForResult(intent, 2);
        } else {
            DialogUtil.ErrorDilog(this, getString(R.string.activity_housing_select));
        }
    }

    @OnClick(R.id.et_sub_address)
    void address() {
        if (unitNumber != null) {
            et_sub_address.setFocusable(true);
            et_sub_address.setFocusableInTouchMode(true);
            et_sub_address.requestFocus();
        } else {
            DialogUtil.ErrorDilog(this, "请先选择单元号");
        }
    }

    @OnClick(R.id.tv_details_one)
    void one() {
        if (one) {
            one = false;
            type.remove("1");
            tv_details_one.setBackgroundResource(R.drawable.textview_complaint_no);
            tv_details_one.setTextColor(getResources().getColor(R.color.login_select_no));
        } else {
            one = true;
            type.add("1");
            tv_details_one.setBackgroundResource(R.drawable.textview_complaint_press);
            tv_details_one.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnClick(R.id.tv_details_two)
    void two() {
        if (two) {
            two = false;
            type.remove("2");
            tv_details_two.setBackgroundResource(R.drawable.textview_complaint_no);
            tv_details_two.setTextColor(getResources().getColor(R.color.login_select_no));
        } else {
            two = true;

            type.add("2");
            tv_details_two.setBackgroundResource(R.drawable.textview_complaint_press);
            tv_details_two.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnClick(R.id.tv_details_three)
    void three() {
        if (three) {
            three = false;
            type.remove("3");
            tv_details_three.setBackgroundResource(R.drawable.textview_complaint_no);
            tv_details_three.setTextColor(getResources().getColor(R.color.login_select_no));
        } else {
            three = true;
            type.add("3");
            tv_details_three.setBackgroundResource(R.drawable.textview_complaint_press);
            tv_details_three.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnClick(R.id.tv_details_four)
    void four() {
        if (four) {
            four = false;
            type.remove("4");
            tv_details_four.setBackgroundResource(R.drawable.textview_complaint_no);
            tv_details_four.setTextColor(getResources().getColor(R.color.login_select_no));
        } else {
            four = true;
            type.add("4");
            tv_details_four.setBackgroundResource(R.drawable.textview_complaint_press);
            tv_details_four.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnClick(R.id.tv_details_five)
    void five() {
        if (five) {
            five = false;
            type.remove("5");
            tv_details_five.setBackgroundResource(R.drawable.textview_complaint_no);
            tv_details_five.setTextColor(getResources().getColor(R.color.login_select_no));
        } else {
            five = true;
            type.add("5");
            tv_details_five.setBackgroundResource(R.drawable.textview_complaint_press);
            tv_details_five.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnClick(R.id.tv_details_six)
    void six() {
        if (six) {
            six = false;
            type.remove("6");
            tv_details_six.setBackgroundResource(R.drawable.textview_complaint_no);
            tv_details_six.setTextColor(getResources().getColor(R.color.login_select_no));
        } else {
            six = true;
            type.add("6");
            tv_details_six.setBackgroundResource(R.drawable.textview_complaint_press);
            tv_details_six.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnItemClick(R.id.gv_image)
    void addimage(int position) {
        ImageIssueData data = (ImageIssueData) adapter.getItem(position);
        if (data.isFile()) {
            if (IssueHouseActivity.list.contains(issueData))
                IssueHouseActivity.list.remove(issueData);
            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(gv_image.getMeasuredWidth(), gv_image.getMeasuredHeight());
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
            if (type.size() == 0) {
                WinToast.toast(this, "最少选择一种隐患详情");
                return;
            }
            IssueHouseActivity.list.remove(issueData);
            String address = et_sub_address.getText().toString();
            String detail = et_house_details.getText().toString();
            Map<String, RequestBody> map = new HashMap<>();
            RequestBody uid = RequestBody.create(
                    MediaType.parse("multipart/form-data"), persons.getId());
            RequestBody commid = RequestBody.create(
                    MediaType.parse("multipart/form-data"), myCommunity.getCommid());
            RequestBody unitname = RequestBody.create(
                    MediaType.parse("multipart/form-data"), unitNumber.getGatename());
            RequestBody location = RequestBody.create(
                    MediaType.parse("multipart/form-data"), address);

            RequestBody remarks = RequestBody.create(
                    MediaType.parse("multipart/form-data"), detail);
            MultipartBody.Part[] part = new MultipartBody.Part[IssueHouseActivity.list.size()];
            for (int i = 0; i < IssueHouseActivity.list.size(); i++) {
                ImageIssueData image = IssueHouseActivity.list.get(i);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), image.getFile());
                part[i] = MultipartBody.Part.createFormData("files", image.getFile().getName(), requestFile);
            }
            String types = "";
            for (int i = 0; i < type.size(); i++) {
                String str = type.get(i);
                if (type.size() == 1) {
                    types = str;
                } else {
                    if (i == (type.size() - 1)) {
                        types += str;
                    } else {
                        types += str + ",";
                    }
                }
            }
            RequestBody Types = RequestBody.create(
                    MediaType.parse("multipart/form-data"), types);
            map.put("uid", uid);
            map.put("commid", commid);
            map.put("unit", unitname);
            map.put("location", location);
            map.put("type", Types);
            map.put("details", remarks);

            RetrofitClient.getInstance(this).createBaseApi().uploadComplaint(map, part, new BaseSubscriber<ResponseBody>(this) {
                @Override
                public void onError(ExceptionHandle.ResponeThrowable e) {
                    WinToast.toast(ComplaintActivity.this, e.Message);
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    super.onNext(responseBody);
                    String request = responseBody.source().toString();
                    if (request.contains("1000")) {
                        WinToast.toast(ComplaintActivity.this, "提交成功");
                        finish();
                    } else {
                        WinToast.toast(ComplaintActivity.this, "提交失败,请重新提交");
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        ButterKnife.bind(this);
        TitleUtls.setIsShow(false);
        AppManager.getAppManager().addActivity(this);
        TitleUtls.TitleItemView(this, "提交隐患");
        persons = GloData.getPersons().getUser();
        myHousing = GloData.getPersons().getMiddleueDTOS();
        for (MyCommunity community :
                myHousing) {
            if (community.isSelect()) {
                tv_my_housing.setText(community.getCommname());
                myCommunity = community;
            }
        }
        fistlist = new LinkedList<>();
        fistlist.addLast(issueData);
        adapter = new IssueImageAdapter(this, fistlist);
        gv_image.setAdapter(adapter);
        initListener();
    }

    private void initListener() {
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
                    tv_unit_num.setText(unitNumber.getGatename());
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
