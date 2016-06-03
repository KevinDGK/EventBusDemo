package com.dgk.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dgk.eventbusdemo.activity.ActivityA;
import com.dgk.eventbusdemo.event.MessageEventA;
import com.dgk.eventbusdemo.event.MessageEventB;
import com.dgk.eventbusdemo.event.MessageEventC;
import com.dgk.eventbusdemo.event.MessageEventD;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_post_from_main;
    private Button btn_post_from_sub;
    private Button btn_main_from_main;
    private Button btn_main_from_sub;
    private Button btn_bg_from_main;
    private Button btn_bg_from_sub;
    private Button btn_async_from_main;
    private Button btn_async_from_sub;
    private Button btn_open_a;
    private Button btn_post_data;
    private TextView tv_content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        tv_content = (TextView) findViewById(R.id.tv_content);

        btn_post_from_main = (Button) findViewById(R.id.btn_post_from_main);
        btn_post_from_sub = (Button) findViewById(R.id.btn_post_from_sub);
        btn_main_from_main = (Button) findViewById(R.id.btn_main_from_main);
        btn_main_from_sub = (Button) findViewById(R.id.btn_main_from_sub);
        btn_bg_from_main = (Button) findViewById(R.id.btn_bg_from_main);
        btn_bg_from_sub = (Button) findViewById(R.id.btn_bg_from_sub);
        btn_async_from_main = (Button) findViewById(R.id.btn_async_from_main);
        btn_async_from_sub = (Button) findViewById(R.id.btn_async_from_sub);
        btn_open_a = (Button) findViewById(R.id.btn_open_a);
        btn_post_data = (Button) findViewById(R.id.btn_post_data);

        btn_post_from_main.setOnClickListener(this);
        btn_post_from_sub.setOnClickListener(this);
        btn_main_from_main.setOnClickListener(this);
        btn_main_from_sub.setOnClickListener(this);
        btn_bg_from_main.setOnClickListener(this);
        btn_bg_from_sub.setOnClickListener(this);
        btn_async_from_main.setOnClickListener(this);
        btn_async_from_sub.setOnClickListener(this);
        btn_open_a.setOnClickListener(this);
        btn_post_data.setOnClickListener(this);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//
//    }

//    @Override
//    protected void onStop() {
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_post_from_main: // 主线程发送Event - 主线程接收
                EventBus.getDefault().post(new MessageEventA("Hi, post girl! I'm from UI Thread."));
                break;

            case R.id.btn_post_from_sub: // 子线程发送Event - 子线程发送子线程接收
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        EventBus.getDefault().post(new MessageEventA("Hi, post girl! I'm from Sub Thread."));
                    }
                }.start();
                break;

            case R.id.btn_main_from_main: // 主线程发送Event - 主线程接收
                EventBus.getDefault().post(new MessageEventB("Hi, main boy! I'm from UI Thread."));
                break;

            case R.id.btn_main_from_sub: // 子线程发送Event - 子线程发送子线程接收
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        EventBus.getDefault().post(new MessageEventB("Hi, main boy! I'm from Sub Thread."));
                    }
                }.start();
                break;

            case R.id.btn_bg_from_main: // 主线程发送Event - 开子线程执行  (注意：内部维护线程池！！！)
                EventBus.getDefault().post(new MessageEventC("Hi, background boy! I'm from UI Thread."));
                break;

            case R.id.btn_bg_from_sub: // 子线程发送Event - 子线程立刻执行
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        EventBus.getDefault().post(new MessageEventC("Hi, background boy! I'm from Sub Thread."));
                    }
                }.start();
                break;

            case R.id.btn_async_from_main: // 主线程发送Event - 开子线程执行  (注意：内部维护线程池！！！)
                EventBus.getDefault().post(new MessageEventD("Hi, background boy! I'm from UI Thread."));
                break;

            case R.id.btn_async_from_sub: // 子线程发送Event - 开子线程执行  (注意：内部维护线程池！！！)
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        EventBus.getDefault().post(new MessageEventD("Hi, background boy! I'm from Sub Thread."));
                    }
                }.start();
                break;


            case R.id.btn_open_a:
                Log.i("MainActivity", "onClick: 打开界面A");
                startActivity(new Intent(MainActivity.this, ActivityA.class));  // 打开界面A
                break;

            case R.id.btn_post_data:
                Toast.makeText(MainActivity.this,"尚未写完：可暂时参考外卖统一接单",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /** 四种Event事件的订阅模式 */

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEventA event) {

        final String content = event.getMsg() + System.getProperty("line.separator", "\n")
                + "from  Thread-Id：" + Thread.currentThread().getId()
                + " , Name：" + Thread.currentThread().getName();

        // 确保修改UI界面是在UI线程进行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_content.setText(content);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventB event) {

        final String content = event.getMsg() + System.getProperty("line.separator", "\n")
                + "from  Thread-Id：" + Thread.currentThread().getId()
                + " , Name：" + Thread.currentThread().getName();

        // 确保修改UI界面是在UI线程进行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_content.setText(content);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEventC event) {

        final String content = event.getMsg() + System.getProperty("line.separator", "\n")
                + "from  Thread-Id：" + Thread.currentThread().getId()
                + " , Name：" + Thread.currentThread().getName();

        // 确保修改UI界面是在UI线程进行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_content.setText(content);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(MessageEventD event) {

        final String content = event.getMsg() + System.getProperty("line.separator", "\n")
                + "from  Thread-Id：" + Thread.currentThread().getId()
                + " , Name：" + Thread.currentThread().getName() + System.getProperty("line.separator", "\n")
                + Thread.getAllStackTraces().size();

        // 确保修改UI界面是在UI线程进行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_content.setText(content);
                Toast.makeText(MainActivity.this,"子线程沉睡3s!",Toast.LENGTH_SHORT).show();
            }
        });

        SystemClock.sleep(3000);
    }

}
