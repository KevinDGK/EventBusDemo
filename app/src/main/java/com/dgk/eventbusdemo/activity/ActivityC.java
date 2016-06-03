package com.dgk.eventbusdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dgk.eventbusdemo.R;
import com.dgk.eventbusdemo.event.CloseActivityB;
import com.dgk.eventbusdemo.event.CloseAllActivity;

import org.greenrobot.eventbus.EventBus;

public class ActivityC extends AppCompatActivity  implements View.OnClickListener {

    private Button btn_close_ab;
    private Button btn_close_b;
    private Button btn_close_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_c);

        btn_close_ab = (Button) findViewById(R.id.btn_close_ab);
        btn_close_b = (Button) findViewById(R.id.btn_close_b);
        btn_close_c = (Button) findViewById(R.id.btn_close_c);

        btn_close_ab.setOnClickListener(this);
        btn_close_b.setOnClickListener(this);
        btn_close_c.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_close_b:
                EventBus.getDefault().post(new CloseActivityB("关闭界面B"));
                break;
            case R.id.btn_close_c:
                finish();
                break;
            case R.id.btn_close_ab:
                EventBus.getDefault().post(new CloseAllActivity("关闭所有的界面"));
                break;
        }
    }
}
