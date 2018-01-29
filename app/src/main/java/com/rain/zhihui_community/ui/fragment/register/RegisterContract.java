package com.rain.zhihui_community.ui.fragment.register;

import com.rain.zhihui_community.base.BaseContract;
import com.rain.zhihui_community.entity.Persons;

import java.util.Map;

/**
 * author : Rain
 * time : 2017/10/13 0013
 * explain :
 */

public class RegisterContract {

    /**
     * ILoginView
     * LoginPresenter需要和LoginAct互动的方法，
     * 比如说presenter需要获得通过act登录框的手机号，
     * 而act需要presenter处理登录数据后的返回值
     */
    public interface IRegisterView extends BaseContract.IBaseView {
        /**
         * @data : 2017/10/13 0013
         * @author : Rain
         * @Description : 参数
         */
        Map<String, String> getMap();

        String getCode();

        int getType();

        /**
         * 处理登录数据后的返回值
         *
         * @param user
         */
        void onRegisterResult(Persons user);

        void getCode(String code);
    }

    /**
     * 注册返回
     */
    public interface IRegisterPresenter extends BaseContract.IBasePresenter {
        void getResult(Persons user);

        void onError(String request);
    }

    /**
     * 获取验证码返回
     */
    public interface IcodePresenter extends BaseContract.IBasePresenter {
        void getCodeResult(String code);

        void onCodeError(String request);
    }

    /**
     * ILoginModule Presenter需要和LoginModule互动的方法，
     * 比如说presenter需要通过module获取登录结果，
     * 而module需要presenter传递给他username和pwd,
     * 并传递一个实例化好的接口过去用来回调返回值
     */
    public interface IRegisterModule extends BaseContract.IBaseModule {
        void toRegister(Map<String, String> map, IRegisterPresenter iLoginPresenter);

        void getCode(String code, int type, IcodePresenter icodePresenter);
    }

}
