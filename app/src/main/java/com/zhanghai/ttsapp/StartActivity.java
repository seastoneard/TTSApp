package com.zhanghai.ttsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;
import com.zhanghai.ttsapp.utils.TTSUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by zhanghai on 2018/07/10.
 * 初始化语音播报+权限申请
 */

public class StartActivity extends AppCompatActivity {


    private List<String> permissionList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemClock.sleep(1000);//延时加载
        requestPermissions();
    }

    private void openActivity(Class<? extends AppCompatActivity> clazz) {
        initTTS();
        startActivity(new Intent(this, clazz));
        finish();
    }

    //权限申请
    private void requestPermissions() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            addListPermission();
            boolean isGranted = false;//是否全部授权
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            Iterator<String> iterator = permissionList.iterator();
            while (iterator.hasNext()) {
                // 检查该权限是否已经获取
                int granted = ContextCompat.checkSelfPermission(this, iterator.next());
                if (granted == PackageManager.PERMISSION_GRANTED) {
                    iterator.remove();//已授权则remove
                }
            }
            if (permissionList.size() > 0) {
                // 如果没有授予该权限，就去提示用户请求
                //将List转为数组
                String[] permissions = permissionList.toArray(new String[permissionList.size()]);
                // 开始提交请求权限
                ActivityCompat.requestPermissions(this, permissions, 0x10);
            } else {
                Log.i("zhh", "权限已申请");
                openActivity(MainActivity.class);
            }

        } else {
            openActivity(MainActivity.class);
        }
    }

    //初始化语音合成
    private void initTTS() {
        //讯飞语音播报平台
        SpeechUtility.createUtility(this, "appid=");//=号后面写自己应用的APPID
        Setting.setShowLog(true); //设置日志开关（默认为true），设置成false时关闭语音云SDK日志打印
        TTSUtils.getInstance().init(); //初始化工具类
    }


    /**
     * 权限申请返回结果
     *
     * @param requestCode  请求码
     * @param permissions  权限数组
     * @param grantResults 申请结果数组，里面都是int类型的数
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x10:
                if(grantResults.length>0&&ifGrantResult(grantResults)){
                    Toast.makeText(this, "同意权限申请", Toast.LENGTH_SHORT).show();
                    openActivity(MainActivity.class);
                }else{
                    Toast.makeText(this, "权限被拒绝了", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }


    }

    private boolean ifGrantResult(int[] grants) {
        boolean isGrant = true;
        for (int grant : grants) {
            if (grant == PackageManager.PERMISSION_DENIED) {
                isGrant = false;
                break;
            }
        }
        return isGrant;
    }


    //敏感权限添加
    private void addListPermission() {
        if (null == permissionList) {
            permissionList = new ArrayList<>();
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }
    }


}
