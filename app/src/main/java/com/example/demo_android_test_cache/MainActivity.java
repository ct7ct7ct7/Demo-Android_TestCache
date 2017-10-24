package com.example.demo_android_test_cache;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Anson on 2016/5/24.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ex1Button)
    public void onClickEx1Button() {
        startActivity(new Intent(this, Example1Activity.class));
    }

    @OnClick(R.id.ex2Button)
    public void onClickEx2Button() {
        startActivity(new Intent(this, Example2Activity.class));
    }

    @OnClick(R.id.ex3Button)
    public void onClickEx3Button() {
        startActivity(new Intent(this, Example3Activity.class));
    }
}