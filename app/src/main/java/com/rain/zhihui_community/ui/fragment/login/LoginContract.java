package com.rain.zhihui_community.ui.fragment.login;

import com.rain.zhihui_community.base.BaseContract;
import com.rain.zhihui_community.entity.Persons;

import java.util.Map;

import okhttp3.ResponseBody;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain :
 */

public class LoginContract {

    /**
     * ILoginView
     * LoginPresenter需要和LoginAct互动的方法，
     * 比如说presenter需要获得通过act登录框的手机号，
     * 而act需要presenter处理登录数据后的返回值
     */
    public interface ILoginView extends BaseContract.IBaseView{
        /**
        *@data : 2017/10/13 0013
        *@author : Rain
        *@Description : 参数
        */
        Map<String,String> getMap() ;

        /**
         * 处理登录数据后的返回值
         * @param user
         */
        void onLoginResult(ResponseBody user);
    }

    /**
     * 返回
     */
    public interface ILoginPresenter extends BaseContract.IBasePresenter {
        void getResult(ResponseBody user);
        void onError(String request);
    }
    /**
     * ILoginModule Presenter需要和LoginModule互动的方法，
     * 比如说presenter需要通过module获取登录结果，
     * 而module需要presenter传递给他username和pwd,
     * 并传递一个实例化好的接口过去用来回调返回值
     */
    public interface ILoginModule extends BaseContract.IBaseModule {
        void toLogin(Map<String ,String> map, ILoginPresenter iLoginPresenter);
    }

}
