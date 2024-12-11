package com.example.mai_map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    boolean showSettings = false;
    boolean showLangSettings = false;
    private void changeAppLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources res = this.getResources();
        Configuration newConfig = res.getConfiguration();

        newConfig.setLocale(locale);

        res.updateConfiguration(newConfig, res.getDisplayMetrics());
    }

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
                AppCompatDelegate.setDefaultNightMode(showLangSettings ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);

                spEditor.putBoolean("Theme", !isNightModeOn);
                spEditor.apply();

                recreate();
                overridePendingTransition(0, 0);
            }
        });

        Button enLangButton = (Button)findViewById(R.id.langButton_EN);
        enLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAppLocale("en");

                recreate();
                overridePendingTransition(0, 0);
            }
        });

        Button ruLangButton = (Button)findViewById(R.id.langButton_RU);
        ruLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAppLocale("ru");

                recreate();
                overridePendingTransition(0, 0);
            }
        });

        Button cnLangButton = (Button)findViewById(R.id.langButton_CN);
        cnLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAppLocale("zh");

                recreate();
                overridePendingTransition(0, 0);
            }
        });

        Button langButton = (Button)findViewById(R.id.langButton);
        langButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showLangSettings) {
                    enLangButton.setVisibility(GONE);
                    ruLangButton.setVisibility(GONE);
                    cnLangButton.setVisibility(GONE);
                }
                else {
                    enLangButton.setVisibility(VISIBLE);
                    ruLangButton.setVisibility(VISIBLE);
                    cnLangButton.setVisibility(VISIBLE);
                }

                showLangSettings = !showLangSettings;
            }
        });

        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (showSettings) {
                    themeButton.setVisibility(GONE);
                    findViewById(R.id.langSettingsContainer).setVisibility(GONE);
                }
                else {
                    themeButton.setVisibility(VISIBLE);
                    langButton.setVisibility(VISIBLE);
                    findViewById(R.id.langSettingsContainer).setVisibility(VISIBLE);
                }

                showSettings = !showSettings;
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}