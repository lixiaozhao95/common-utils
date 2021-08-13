package com.lee.commonutils.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.lee.commonutils.R;
import com.lee.lutils.PreferenceUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceUtils.putString(getApplicationContext(), "11111111", "2222222222222");
    }
}