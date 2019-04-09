package com.example.pluginhy.act;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pluginhy.R;
import com.example.pluginhy.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * EventBus 使用的基本步骤：
 * 1.Gradle引入EventBus
 * 2.定义发送事件类
 * 3.编写事件接收方法  使用@Subscribe注解
 * 4.注册事件接收者
 * 5.发送事件
 * 6.注销事件接收者
 */
public class EventBusAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        ButterKnife.bind(this);


        //EventBus 注册事件接收者
        EventBus.getDefault().register(this);
    }


    /**
     * EventBus 接收消息并处理
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceive(MessageEvent messageEvent) {

        // TODO: 2019/4/6
        if (messageEvent != null) {
            Toast.makeText(getApplicationContext(), "btnId=" + messageEvent.getFlag(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onDestroy() {

        //EventBus 注销事件接收者
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }


    @OnClick({R.id.btn1, R.id.btn2})
    public void onClick(View v) {
        MessageEvent messageEvent = new MessageEvent();

        switch (v.getId()) {
            case R.id.btn1:

                messageEvent.setFlag(R.id.btn1);
                //EventBus 发送事件
                EventBus.getDefault().post(messageEvent);

                break;
            case R.id.btn2:

                messageEvent.setFlag(R.id.btn2);
                //EventBus 发送事件
                EventBus.getDefault().post(messageEvent);
                break;
        }
    }

}
