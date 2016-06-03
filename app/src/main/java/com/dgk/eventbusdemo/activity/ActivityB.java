package com.dgk.eventbusdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dgk.eventbusdemo.R;
import com.dgk.eventbusdemo.event.CloseActivityB;
import com.dgk.eventbusdemo.event.CloseAllActivity;
import com.dgk.eventbusdemo.event.MessageEventB;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ActivityB extends AppCompatActivity  implements View.OnClickListener {

    private Button btn_open_c;
    private Button btn_close_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_b);

        EventBus.getDefault().register(this);

        btn_open_c = (Button) findViewById(R.id.btn_open_c);
        btn_close_b = (Button) findViewById(R.id.btn_close_b);

        btn_open_c.setOnClickListener(this);
        btn_close_b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_c:
                startActivity(new Intent(ActivityB.this, ActivityC.class));  // 打开界面C
                break;
            case R.id.btn_close_b:
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

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void closeActivity(CloseAllActivity event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void closeActivity(CloseActivityB event) {
        finish();
    }
}
