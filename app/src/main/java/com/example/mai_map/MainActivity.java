package com.example.mai_map;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setTheme(R.style.Base_Theme_Mai_map);
        setContentView(R.layout.activity_main);
    }
}