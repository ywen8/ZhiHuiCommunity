package com.rain.zhihui_community.http;


import com.rain.zhihui_community.entity.Appointment;
import com.rain.zhihui_community.entity.BannerItem;
import com.rain.zhihui_community.entity.BuildData;
import com.rain.zhihui_community.entity.Community;
import com.rain.zhihui_community.entity.Deposit;
import com.rain.zhihui_community.entity.GroupChatDB;
import com.rain.zhihui_community.entity.MyCommunity;
import com.rain.zhihui_community.entity.MyHouse;
import com.rain.zhihui_community.entity.Neighbor;
import com.rain.zhihui_community.entity.Persons;
import com.rain.zhihui_community.entity.TMessage;
import com.rain.zhihui_community.entity.TowerData;
import com.rain.zhihui_community.entity.UnitNumber;
import com.rain.zhihui_community.entity.UpDataApk;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Rain on 2017/6/13 0013.
 */

public interface BaseApiService {
//    public static final String Base_URL = "http://192.168.1.171:8080";

    public static final String Base_URL = "http://117.36.77.242:8080";

    public static final String TYPE = "/yykjZhCommunity";
    //    public static final String TYPE = "";
//    public static final String Base_URL = "http://192.168.1.171:8080";
    public static final String BASE_URL = Base_URL + TYPE;

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    /**
     * 检验APP是否需要更新
     *
     * @return
     */
    @POST(TYPE + "/app/version/selectNewVersion")
    Observable<BaseResponse<UpDataApk>> updataApk();


    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param deviceid
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/user/loginRegisterPasswordBack")
    Observable<BaseResponse<Persons>> register(@Field("phone") String phone,
                                               @Field("password") String password,
                                               @Field("checkCode") String checkCode,
                                               @Field("deviceid") String deviceid);

    /**
     * 获取验证码
     *
     * @param phone
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/user/SendTextMessages")
    Observable<ResponseBody> getCode(@Field("phone") String phone,
                                     @Field("type") int type);

    /**
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/user/login")
    Observable<ResponseBody> getLogin(@FieldMap Map<String, String> map);

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @param checkCode
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/user/newLoginPassword")
    Observable<ResponseBody> postForget(@Field("phone") String phone,
                                        @Field("password") String password,
                                        @Field("checkCode") String checkCode);

    /**
     * 获取小区
     *
     * @return
     */
    @POST(TYPE + "/app/community/selectAllCommunity")
    Observable<BaseResponse<List<Community>>> postCommunity();


    /**
     * 获取单元号
     *
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/community/selectCommgate")
    Observable<BaseResponse<List<UnitNumber>>> postUnit(@Field("commid") String commid);

    /**
     * 添加小区
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/middleuc/saveMiddleuc")
    Observable<BaseResponse<MyCommunity>> postAddHousing(@FieldMap Map<String, String> map);


    /**
     * 获取小区
     *
     * @param uid
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/middleuc/selectMiddleuc")
    Observable<BaseResponse<List<MyCommunity>>> postHouing(@Field("uid") String uid);

    /**
     * 删除小区
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/middleuc/delMiddleuc")
    Observable<ResponseBody> delete(@Field("id") String id);

    /**
     * 修改头像
     *
     * @param file
     * @param map
     * @return
     */
    @Multipart
    @POST(TYPE + "/app/user/updateUserHeadImg")
    Observable<ResponseBody> photo(@PartMap() Map<String, RequestBody> map,
                                   @Part MultipartBody.Part file);

    /**
     * 修改名字
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/user/updateUserUsername")
    Observable<ResponseBody> upName(@FieldMap Map<String, String> map);

    /**
     * 修改性别
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/user/updateUserSex")
    Observable<ResponseBody> upSex(@FieldMap Map<String, String> map);

    /**
     * 获取我的房源
     *
     * @param uid
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/realtor/selectUserRealtor")
    Observable<BaseResponse<List<MyHouse>>> house(@Field("uid") String uid);

    /**
     * 刪除
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/realtor/delUserRealtor")
    Observable<ResponseBody> deleteHouse(@Field("id") String id);

    /**
     * 修改密码
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/user/updateUserPassword")
    Observable<ResponseBody> password(@FieldMap Map<String, String> map);

    /**
     * 获取banner
     *
     * @return
     */
    @POST(TYPE + "/app/news/selectHomeNews")
    Observable<BaseResponse<List<BannerItem>>> postBanner();


    /**
     * 提交预约
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/bespeak/andBespeak")
    Observable<ResponseBody> appointment(@FieldMap Map<String, String> map);

    /**
     * 预约开门
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/bespeak/selectUserBespeak")
    Observable<BaseResponse<List<Appointment>>> app_open(@Field("phone") String phone);


    /**
     * 出租屋页面
     *
     * @param commid
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/realtor/selectCommidRealtor")
    Observable<BaseResponse<List<MyHouse>>> letData(@Field("commid") String commid);

    /**
     * 上传出租屋
     *
     * @param map
     * @return
     */
    @Multipart
    @POST(TYPE + "/app/realtor/saveUserRealtor")
    Observable<ResponseBody> uploadLet(@PartMap() Map<String, RequestBody> map,
                                       @Part() MultipartBody.Part[] files);

    /**
     * 上传物业报修
     *
     * @param map
     * @param files
     * @return
     */
    @Multipart
    @POST(TYPE + "/app/property/savePropertyRepair")
    Observable<ResponseBody> uploadRepair(@PartMap() Map<String, RequestBody> map,
                                          @Part() MultipartBody.Part[] files);

    /**
     * 社区新闻
     *
     * @return
     */
    @POST(TYPE + "/app/conmmunitynews/selectAllConmmunityNews")
    Observable<BaseResponse<List<BannerItem>>> postCommunityNews();

    /**
     * 消防新闻
     *
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/xf/selectXFNotice")
    Observable<BaseResponse<List<BannerItem>>> postControNews(@Field("type") int type);


    /**
     * 提交隐患
     *
     * @param map
     * @param files
     * @return
     */
    @Multipart
    @POST(TYPE + "/app/xf/saveXFComplaint")
    Observable<ResponseBody> uploadComplaint(@PartMap() Map<String, RequestBody> map,
                                             @Part() MultipartBody.Part[] files);


    /**
     * 小区建筑信息
     * 消防管理公约
     * 社区概况
     * 微消防站
     *
     * @param commid
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/xf/queryXFGK")
    Observable<BaseResponse<BuildData>> postBuild(@Field("commid") String commid);

    /**
     * 小区楼号
     *
     * @param commid
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/xf/queryXFFloor")
    Observable<BaseResponse<List<BuildData>>> postBuildNum(@Field("commid") String commid);

    /**
     * 获取楼信息
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/xf/queryXFFloorDetails")
    Observable<BaseResponse<TowerData>> postTower(@Field("id") String id);

    /**
     * 小区消防人
     *
     * @param commid
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/xf/selectUser")
    Observable<ResponseBody> postControlPerson(@Field("commid") String commid);

    /**
     * 获取消息
     *
     * @param type 123
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/message/SelectAllMessage")
    Observable<BaseResponse<List<TMessage>>> messageData(@Field("type") int type,
                                                         @Field("uid") String id);

    /**
     * 获取群聊组
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/neighbours/selectUserNeighbours")
    Observable<BaseResponse<List<Neighbor>>> neighbor(@Field("uid") String id);

    /**
     * 发送消息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/neighbours/saveNeighboursMessages")
    Observable<ResponseBody> sendGroup(@FieldMap() Map<String, String> map);


    /**
     * 获取聊天内容
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/neighbours/selectNeighboursMessages")
    Observable<BaseResponse<List<GroupChatDB>>> receive(@FieldMap() Map<String, String> map);

    /**
     * 抢红包接口
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/hongbao/getHongbaoMoney")
    Observable<ResponseBody> bround(@Field("phone") String phone);

    /**
     * 获取金额
     *
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/account/selectByPhone")
    Observable<BaseResponse<Deposit>> money(@Field("phone") String phone);

    /**
     * 提现
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST(TYPE + "/app/account/ApplyMoney")
    Observable<ResponseBody> deposit(@FieldMap() Map<String, String> map);
}
