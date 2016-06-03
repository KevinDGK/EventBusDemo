package com.dgk.eventbusdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dgk.eventbusdemo.R;
import com.dgk.eventbusdemo.event.CloseAllActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ActivityA extends AppCompatActivity implements View.OnClickListener  {

    private Button btn_open_b;
    private Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        EventBus.getDefault().register(this);

        btn_open_b = (Button) findViewById(R.id.btn_open_b);
        btn_close = (Button) findViewById(R.id.btn_close);

        btn_open_b.setOnClickListener(this);
        btn_close.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_open_b:
                startActivity(new Intent(ActivityA.this, ActivityB.class));  // 打开界面B
                break;
            case R.id.btn_close:    //关掉本界面
                finish();
                break;
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        EventBus.getDefault().register(this);
//
//    }
//
//    @Override
//    protected void onStop() {
//        EventBus.getDefault().unregister(this);
//
//        super.onStop();
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void closeActivity(CloseAllActivity event) {
        finish();
    }
}
