package com.zhanghai.ttsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.iflytek.sunflower.FlowerCollector;
import com.zhanghai.ttsapp.utils.TTSUtils;


/**
 * 讯飞语音播报
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText et = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                TTSUtils.getInstance().speak("bigsea是大海");
                break;
            case R.id.btn1:
                String msg = et.getText().toString();
                TTSUtils.getInstance().speak(TextUtils.isEmpty(msg) ? "输入信息为空" : msg);
                break;
            case R.id.btn2:
                startActivity(new Intent(this, AndroidTTSActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        //移动数据统计分析--不用可不用加入
        FlowerCollector.onResume(MainActivity.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(MainActivity.this);
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        TTSUtils.getInstance().release();//释放资源
    }


}
