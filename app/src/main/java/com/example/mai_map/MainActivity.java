package com.example.mai_map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatDelegate;


public class MainActivity extends Activity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    boolean showSettings = false;
    String[] languages = { "RU", "EN", "CN" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);

        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        boolean isNightModeOn = sharedPreferences.getBoolean("Theme", false);
        short langIdx = (short)sharedPreferences.getInt("Lang", 0);

        if (isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onStart(){
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();

        short langIdx = (short)sharedPreferences.getInt("Lang", 0);
        boolean isNightModeOn = sharedPreferences.getBoolean("Theme", false);

        if (isNightModeOn){
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onStart();
        setContentView(R.layout.activity_main);

        ImageButton themeButton = (ImageButton) findViewById(R.id.themeButton);
        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTheme(isNightModeOn ? R.style.AppTheme : R.style.AppThemeDark);

                spEditor.putBoolean("Theme", !isNightModeOn);
                spEditor.apply();

                recreate();
                overridePendingTransition(0, 0);
            }
        });


        Button langButton = (Button)findViewById(R.id.langButton);
        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                short langIdx = (short)sharedPreferences.getInt("Lang", 0);
                langIdx = (short)((langIdx + 1) % languages.length);

                langButton.setText(languages[langIdx]);

                spEditor.putInt("Lang", langIdx);
                spEditor.apply();

                recreate();
                overridePendingTransition(0, 0);
            }
        });

        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (showSettings) {
                    themeButton.setVisibility(GONE);
                    langButton.setVisibility(GONE);
                }
                else {
                    themeButton.setVisibility(VISIBLE);
                    langButton.setVisibility(VISIBLE);
                }

                showSettings = !showSettings;
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}