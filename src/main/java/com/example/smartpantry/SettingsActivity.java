package com.example.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryAlerts;

    private SharedPreferences preferences;

    private static final String PREFS_NAME =
            "SmartPantrySettings";

    private static final String EXPIRY_ALERTS =
            "expiry_alerts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_settings
        );

        switchExpiryAlerts =
                findViewById(
                        R.id.switchExpiryAlerts
                );

        preferences =
                getSharedPreferences(
                        PREFS_NAME,
                        MODE_PRIVATE
                );

        boolean alertsEnabled =
                preferences.getBoolean(
                        EXPIRY_ALERTS,
                        true
                );

        switchExpiryAlerts.setChecked(
                alertsEnabled
        );

        switchExpiryAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences
                            .edit()
                            .putBoolean(
                                    EXPIRY_ALERTS,
                                    isChecked
                            )
                            .apply();
                }
        );
    }
}