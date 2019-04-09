package com.example.myapplication.lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;

public class Act_b extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_b);
        Log.d("Act_b", "onCreate");
    }

    public void jumpToC(View view) {
        startActivity(new Intent(this, Act_c.class));
    }
}
