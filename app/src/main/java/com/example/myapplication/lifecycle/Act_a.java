package com.example.myapplication.lifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;

public class Act_a extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_a);
        Log.d("Act_a", "onCreate");
    }

    public void jumpToB(View view) {
        startActivity(new Intent(this, Act_b.class));
    }
}
