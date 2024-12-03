package com.example.mai_map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = this.getSharedPreferences("settings", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = getSharedPreferences("settings", Context.MODE_PRIVATE).edit();

        boolean isNightModeOn = sharedPreferences.getBoolean("Theme", false);

        Button switchTheme = (Button)findViewById(R.id.switchThemeButton);
        switchTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNightModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switchTheme.setText("Enable Dark Mode");
                    editor.putBoolean("Theme", false);

                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchTheme.setText("Disable Dark Mode");
                    editor.putBoolean("Theme", true);
                }
                editor.apply();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}