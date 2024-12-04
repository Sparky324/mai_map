package com.example.mai_map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.jakewharton.processphoenix.ProcessPhoenix;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = getSharedPreferences("settings", Context.MODE_PRIVATE).edit();

        boolean isNightModeOn = sharedPreferences.getBoolean("Theme", false);

        if (isNightModeOn){
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button switchTheme = (Button)findViewById(R.id.switchThemeButton);
        Button restart = (Button)findViewById(R.id.buttonRestart);

        switchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("Theme", !isNightModeOn);
                editor.apply();
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(R.string.needRestartText);
                restart.setVisibility(View.VISIBLE);
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProcessPhoenix.triggerRebirth(getApplicationContext());
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}