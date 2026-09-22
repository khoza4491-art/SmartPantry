package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Button btnPantry;
    private Button btnSuggestedRecipes;
    private Button btnSettings;

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnPantry =
                findViewById(R.id.btnPantry);

        btnSuggestedRecipes =
                findViewById(R.id.btnSuggestedRecipes);

        btnSettings =
                findViewById(R.id.btnSettings);

        bottomNavigation =
                findViewById(R.id.bottomNavigation);

        // My Pantry button
        btnPantry.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            PantryActivity.class
                    );

            startActivity(intent);
        });

        // Suggested Recipes button
        btnSuggestedRecipes.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SuggestedRecipesActivity.class
                    );

            startActivity(intent);
        });

        // Settings button
        btnSettings.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SettingsActivity.class
                    );

            startActivity(intent);
        });

        // Bottom navigation
        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.nav_pantry) {

                Intent intent =
                        new Intent(
                                MainActivity.this,
                                PantryActivity.class
                        );

                startActivity(intent);

                return true;
            }

            if (itemId == R.id.nav_recipes) {

                Intent intent =
                        new Intent(
                                MainActivity.this,
                                SuggestedRecipesActivity.class
                        );

                startActivity(intent);

                return true;
            }

            if (itemId == R.id.nav_settings) {

                Intent intent =
                        new Intent(
                                MainActivity.this,
                                SettingsActivity.class
                        );

                startActivity(intent);

                return true;
            }

            return false;
        });
    }
}