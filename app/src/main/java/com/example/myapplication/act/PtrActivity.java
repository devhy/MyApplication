package com.example.myapplication.act;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.R;

public class PtrActivity extends AppCompatActivity {


    private SwipeRefreshLayout refreshLayout;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_custom);

//        refreshLayout = findViewById(R.id.srf);

        mHandler = new Handler();

//        initEvent();
    }

    private void initEvent() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                }, 2000);
            }
        });
    }


}
