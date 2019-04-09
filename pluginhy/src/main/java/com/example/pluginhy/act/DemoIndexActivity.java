package com.example.pluginhy.act;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pluginhy.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DemoIndexActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_index);

        unbinder = ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_plugin, R.id.btn_eventBus})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_plugin:

                startActivity(new Intent(this, MainActivity.class));

                break;
            case R.id.btn_eventBus:

                startActivity(new Intent(this, EventBusAct.class));

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
