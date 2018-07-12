package com.zhanghai.ttsapp.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhanghai on 2018/7/10.
 */

public class App extends Application {


    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //讯飞语音播报平台--因权限申请，初始化放在StartActivity
//        SpeechUtility.createUtility(this, "appid=");//=号后面写自己应用的APPID
//        Setting.setShowLog(true); //设置日志开关（默认为true），设置成false时关闭语音云SDK日志打印
//        TTSUtils.getInstance().init(); //初始化工具类

    }

    public static Context getContext() {
        return context;
    }
}
