package com.example.mai_map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class MainActivity extends UnityPlayerActivity {
    SharedPreferences sharedPreferences;
    boolean showSettings = false;
    boolean showLangSettings = false;

    Map<String, String> points = Map.ofEntries(
            Map.entry("КПП №1", "kpp_1"),
            Map.entry("КПП №3", "kpp_3"),
            Map.entry("КПП №4", "kpp_4"),
            Map.entry("Корпус №3", "corp_3"),
            Map.entry("Корпус №5", "corp_5"),
            Map.entry("Корпус №9", "corp_9"),
            Map.entry("Корпус ГАК", "corp_gak"),
            Map.entry("Корпус ГУК В", "corp_guk_v")
    );

    private void changeAppLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources res = this.getResources();
        Configuration newConfig = res.getConfiguration();

        newConfig.setLocale(locale);

        res.updateConfiguration(newConfig, res.getDisplayMetrics());
    }

    public static void showRelaunchMessage(Context ctx) {
        String message = ctx.getString(R.string.relaunch_theme);
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBX_8888);

        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean isNightModeOn = sharedPreferences.getBoolean("Theme", false);
        String langCode = sharedPreferences.getString("Lang", "en");

        if (isNightModeOn) {
            setTheme(R.style.AppThemeDark);
            UnityPlayer.UnitySendMessage("MainCamera", "ChangeBackroungColor", "#202020");
        }
        else {
            setTheme(R.style.AppTheme);
            UnityPlayer.UnitySendMessage("MainCamera", "ChangeBackroungColor", "D9D9D9");
        }
        changeAppLocale(langCode);

        setContentView(R.layout.activity_main);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();

        FrameLayout unityLayout = (FrameLayout)findViewById(R.id.unityLayout);
        while (((View)mUnityPlayer.getView()).getParent() != null) {
            ((ViewGroup)((View)mUnityPlayer.getView()).getParent()).removeView((View)mUnityPlayer.getView());
        }

        unityLayout.addView(mUnityPlayer.getView(), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        ImageButton themeButton = (ImageButton) findViewById(R.id.themeButton);
        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDarkMode = sharedPreferences.getBoolean("Theme", false);
                spEditor.putBoolean("Theme", !isDarkMode);
                spEditor.apply();

                showRelaunchMessage(MainActivity.this);
            }
        });

        Button enLangButton = (Button)findViewById(R.id.langButton_EN);
        enLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spEditor.putString("Lang", "en");
                spEditor.apply();;

                showRelaunchMessage(MainActivity.this);
            }
        });

        Button ruLangButton = (Button)findViewById(R.id.langButton_RU);
        ruLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spEditor.putString("Lang", "ru");
                spEditor.apply();

                showRelaunchMessage(MainActivity.this);
            }
        });

        Button cnLangButton = (Button)findViewById(R.id.langButton_CN);
        cnLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spEditor.putString("Lang", "zh");
                spEditor.apply();

                showRelaunchMessage(MainActivity.this);
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

        Set<String> pointsSet = points.keySet();
        String[] pointsArray = pointsSet.toArray(new String[pointsSet.size()]);

        Spinner srcPointList = findViewById(R.id.listBeginning);
        ArrayAdapter<String> srcAdapterItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pointsArray);
        srcAdapterItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        srcPointList.setAdapter(srcAdapterItems);

        Spinner endPointList = findViewById(R.id.listEnding);
        ArrayAdapter<String> endAdapterItems = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pointsArray);
        endAdapterItems.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endPointList.setAdapter(endAdapterItems);

        Button calcPathBtn = (Button)findViewById(R.id.calculatePath);
        calcPathBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String srcPointCode = points.get(srcPointList.getSelectedItem().toString());
                String endPointCode = points.get(endPointList.getSelectedItem().toString());

                String combinedParam = srcPointCode + ";" + endPointCode;

                UnityPlayer.UnitySendMessage("Housing_Full_Map", "findPath", combinedParam);
            }
        });
    }

    protected void onStart(){
        super.onStart();
    }
}