package com.rain.zhihui_community.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

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

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RetrofitClient主要负责创建具体Retrofit，
 * 和调度分发请求。设置格式工厂。添加cookie同步，
 * 构建OkHttpClient，添加BaseUrl,对加密证书https
 * RetrofitClient
 * Created by Rain on 2017/6/13 0013.
 */

public class RetrofitClient {
    private static final int DEFAULT_TIMEOUT = 5;
    private BaseApiService apiService;
    private static OkHttpClient okHttpClient;
    public static String baseUrl = BaseApiService.Base_URL;
    private static Context mContext;
    private static RetrofitClient sNewInstance;

    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheDirectory;


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(baseUrl);


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES);


    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(
                mContext);
    }


    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitClient getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }

        return new RetrofitClient(context, url);
    }

    public static RetrofitClient getInstance(Context context, String url, Map<String, String> headers) {
        if (context != null) {
            mContext = context;
        }
        return new RetrofitClient(context, url, headers);
    }

    private RetrofitClient() {

    }


    private RetrofitClient(Context context) {

        this(context, baseUrl, null);
    }


    private RetrofitClient(Context context, String url) {

        this(context, url, null);
    }


    private RetrofitClient(Context context, String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "tamic_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cookieJar(new NovateCookieManger(context))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CaheInterceptor(context))
                .addNetworkInterceptor(new CaheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
                .connectionPool(new ConnectionPool(8, 3, TimeUnit.MINUTES))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }


    /**
     * ApiBaseUrl
     *
     * @param newApiBaseUrl
     */
    public static void changeApiBaseUrl(String newApiBaseUrl) {
        baseUrl = newApiBaseUrl;
        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl);
    }

    /**
     * addcookieJar
     */
    public static void addCookie() {
        okHttpClient.newBuilder().cookieJar(new NovateCookieManger(mContext)).build();
        retrofit = builder.client(okHttpClient).build();
    }

    /**
     * ApiBaseUrl
     *
     * @param newApiHeaders
     */
    public static void changeApiHeader(Map<String, String> newApiHeaders) {

        okHttpClient.newBuilder().addInterceptor(new BaseInterceptor(newApiHeaders)).build();
        builder.client(httpClient.build()).build();
    }

    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public RetrofitClient createBaseApi() {
        apiService = create(BaseApiService.class);
        return this;
    }


    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    public void download(String url, final CallBack callBack) {
        apiService.downloadFile(url)
                .compose(schedulersTransformer())
                .compose(this.applySchedulers())
                .subscribe(new DownSubscriber<ResponseBody>(callBack));
    }

    /**
     * 检查APP是否要更新
     *
     * @param subscriber
     * @return
     */
    public Subscription updata(Subscriber<UpDataApk> subscriber) {
        return apiService.updataApk()
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);
    }


    /**
     * 获取验证码
     *
     * @param phone
     * @param type
     * @param subscriber
     */
    public void getCode(String phone, int type, Subscriber<ResponseBody> subscriber) {
        apiService.getCode(phone, type)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param checkCode
     * @param deviceid
     * @param subscriber
     * @return
     */
    public Subscription register(String phone, String password, String checkCode, String deviceid, Subscriber<Persons> subscriber) {
        return apiService.register(phone, password, checkCode, deviceid)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);

    }

    /**
     * 登录
     *
     * @param map
     * @param subscriber
     * @return
     */
    public void getLogin(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        apiService.getLogin(map)
                .compose(schedulersTransformer())
                .compose(this.applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @param checkCode
     * @param subscriber
     */
    public void postForget(String phone, String password, String checkCode, Subscriber<ResponseBody> subscriber) {
        apiService.postForget(phone, password, checkCode)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 获取小区
     *
     * @param subscriber
     * @return
     */
    public Subscription postCommunity(Subscriber<List<Community>> subscriber) {
        return apiService.postCommunity()
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);

    }

    /**
     * 获取单元
     *
     * @param subscriber
     * @return
     */
    public Subscription postUnit(String commid, Subscriber<List<UnitNumber>> subscriber) {
        return apiService.postUnit(commid)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);

    }

    /**
     * 添加小区
     *
     * @param map
     * @param subscriber
     */
    public Subscription addHousing(Map<String, String> map, Subscriber<MyCommunity> subscriber) {
        return apiService.postAddHousing(map)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);
    }

    /**
     * 获取小区
     *
     * @param uid
     * @param subscriber
     * @return
     */
    public Subscription postHouing(String uid, Subscriber<List<MyCommunity>> subscriber) {
        return apiService.postHouing(uid)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);
    }

    /**
     * 删除小区
     *
     * @param id
     * @param subscriber
     */
    public void delete(String id, Subscriber<ResponseBody> subscriber) {
        apiService.delete(id)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 修改头像
     *
     * @param file
     * @param map
     * @param subscriber
     */
    public void photo(Map<String, RequestBody> map, MultipartBody.Part file, Subscriber<ResponseBody> subscriber) {
        apiService.photo(map, file)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 修改名字
     *
     * @param map
     * @param subscriber
     */
    public void name(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        apiService.upName(map)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 修改性别
     *
     * @param map
     * @param subscriber
     */
    public void sex(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        apiService.upSex(map)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 获取我的小区
     *
     * @param uid
     * @param subscriber
     * @return
     */
    public Subscription house(String uid, Subscriber<List<MyHouse>> subscriber) {
        return apiService.house(uid)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);
    }

    /**
     * 删除我发布的房源信息
     *
     * @param id
     * @param subscriber
     */
    public void deleteHouse(String id, Subscriber<ResponseBody> subscriber) {
        apiService.deleteHouse(id)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 修改密码
     *
     * @param map
     * @param subscriber
     */
    public void password(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        apiService.password(map)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 获取首页banner
     *
     * @param subscriber
     * @return
     */
    public Subscription banner(Subscriber<List<BannerItem>> subscriber) {
        return apiService.postBanner()
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);

    }

    /**
     * 提交预约
     *
     * @param map
     * @param subscriber
     */
    public void appointment(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        apiService.appointment(map)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 预约一键开门
     *
     * @param phone
     * @param subscriber
     * @return
     */
    public Subscription app_open(String phone, Subscriber<List<Appointment>> subscriber) {
        return apiService.app_open(phone)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);
    }

    /**
     * 获取出租屋数据
     *
     * @param commid
     * @param subscriber
     * @return
     */
    public Subscription letData(String commid, Subscriber<List<MyHouse>> subscriber) {
        return apiService.letData(commid)
                .compose(schedulersTransformer())
                .compose(transformer())
                .subscribe(subscriber);
    }

    /**
     * 提交出租屋
     *
     * @param map
     * @param subscriber
     */
    public void uploadLet(Map<String, RequestBody> map, MultipartBody.Part[] files, Subscriber<ResponseBody> subscriber) {
        apiService.uploadLet(map, files)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 获取社区新闻
     *
     * @param subscriber
     * @return
     */
    public Subscription communityNews(Subscriber<List<BannerItem>> subscriber) {
        return apiService.postCommunityNews()
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);

    }

    /**
     * 提交物业报修
     *
     * @param map
     * @param files
     * @param subscriber
     */
    public void uploadRepair(Map<String, RequestBody> map, MultipartBody.Part[] files, Subscriber<ResponseBody> subscriber) {
        apiService.uploadRepair(map, files)
                .compose(schedulersTransformer())
                .compose(this.applySchedulers())
                .subscribe(subscriber);

    }

    /**
     * 消防公告
     *
     * @param type
     * @param subscriber
     * @return
     */
    public Subscription postControNews(int type, Subscriber<List<BannerItem>> subscriber) {
        return apiService.postControNews(type)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);

    }

    /**
     * 提交隐患
     *
     * @param map
     * @param files
     * @param subscriber
     */
    public void uploadComplaint(Map<String, RequestBody> map, MultipartBody.Part[] files, Subscriber<ResponseBody> subscriber) {
        apiService.uploadComplaint(map, files)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 小区建筑信息
     * 消防管理公约
     * 社区概况
     * 微消防站
     *
     * @param commid
     * @param subscriber
     * @return
     */
    public Subscription postBuild(String commid, Subscriber<BuildData> subscriber) {
        return apiService.postBuild(commid)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);
    }

    /**
     * 获取楼信息
     *
     * @param id
     * @param subscriber
     * @return
     */
    public Subscription postTower(String id, Subscriber<TowerData> subscriber) {
        return apiService.postTower(id)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);
    }

    /**
     * 小区楼号
     *
     * @param commid
     * @param subscriber
     * @return
     */
    public Subscription postBuildNum(String commid, Subscriber<List<BuildData>> subscriber) {
        return apiService.postBuildNum(commid)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);
    }

    /**
     * 获取小区消防员
     *
     * @param commid
     * @param subscriber
     * @return
     */
    public Subscription postControlPerson(String commid, Subscriber<ResponseBody> subscriber) {
        return apiService.postControlPerson(commid)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }


    /**
     * 获取消息
     *
     * @param type
     * @param id
     * @param subscriber
     * @return
     */
    public Subscription messageData(int type, String id, Subscriber<List<TMessage>> subscriber) {
        return apiService.messageData(type, id)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);

    }

    /**
     * 获取群聊组
     *
     * @param id
     * @param subscriber
     * @return
     */
    public Subscription neighbor(String id, Subscriber<List<Neighbor>> subscriber) {
        return apiService.neighbor(id)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);

    }

    /**
     * 发送消息
     *
     * @param map
     * @param subscriber
     */
    public void send(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        apiService.sendGroup(map)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 获取聊天内容
     *
     * @param map
     * @param subscriber
     * @return
     */
    public Subscription receive(Map<String, String> map, Subscriber<List<GroupChatDB>> subscriber) {
        return apiService.receive(map)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);
    }

    /**
     * 抢红包的接口
     *
     * @param phone
     * @param subscriber
     */
    public void bround(String phone, Subscriber<ResponseBody> subscriber) {
        apiService.bround(phone)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 提现金额
     *
     * @param phone
     * @param subscriber
     */
    public void money(String phone, Subscriber<Deposit> subscriber) {
        apiService.money(phone)
                .compose(schedulersTransformer())
                .compose(this.transformer())
                .subscribe(subscriber);
    }

    /**
     * 提现
     *
     * @param map
     * @param subscriber
     */
    public void deposit(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        apiService.deposit(map)
                .compose(schedulersTransformer())
                .compose(applySchedulers())
                .subscribe(subscriber);
    }

    Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {


            @Override
            public Object call(Object observable) {
                return ((Observable) observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer();
    }


    public <T> Observable.Transformer<BaseResponse<T>, T> transformer() {

        return new Observable.Transformer() {

            @Override
            public Object call(Object observable) {
                return ((Observable) observable).map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }

    public <T> Observable<T> switchSchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }

    private class HandleFuc<T> implements Func1<BaseResponse<T>, T> {
        @Override
        public T call(BaseResponse<T> response) {
            if (!response.isOk())
                throw new RuntimeException(response.getState() + "" + response.getMessage() != null ? response.getMessage() : "");
            return response.getData();
        }
    }

    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> T execute(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io

                ())
                .unsubscribeOn(Schedulers.io

                        ())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }

    /**
     * DownSubscriber
     *
     * @param <ResponseBody>
     */
    class DownSubscriber<ResponseBody> extends Subscriber<ResponseBody> {
        CallBack callBack;

        public DownSubscriber(CallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onStart() {
            super.onStart();
            if (callBack != null) {
                callBack.onStart();
            }
        }

        @Override
        public void onCompleted() {
            if (callBack != null) {
                callBack.onCompleted();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (callBack != null) {
                callBack.onError(e);
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            DownLoadManager.getInstance(callBack).writeResponseBodyToDisk(mContext, (okhttp3.ResponseBody) responseBody);

        }
    }

}
