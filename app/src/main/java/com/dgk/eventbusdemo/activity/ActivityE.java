package com.dgk.eventbusdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dgk.eventbusdemo.R;
import com.dgk.eventbusdemo.event.LoginInfoBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ActivityE extends AppCompatActivity  implements View.OnClickListener {

    private TextView tv_sticky_event;
    private Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_e);

        tv_sticky_event = (TextView) findViewById(R.id.tv_sticky_event);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(this);

        /*
            注意：当注册的时候，粘性事件会立刻被post给订阅者，所以注意注册的位置，
            如果在findviewbyid之前注册，那么注册时候就会执行下面订阅中的方法，导致空指针异常！
         */
        EventBus.getDefault().register(this);

    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void getLoginInfo(LoginInfoBean event){
        tv_sticky_event.setText("登陆名：代高凯\n登陆时间：2016-6-6\n登陆地点：北京\n经度：100\n纬度：100\n"+event.msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_finish:
                finish();
                break;
        }
    }
}
