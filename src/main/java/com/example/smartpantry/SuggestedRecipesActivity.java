package com.example.smartpantry;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import adapter.RecipeAdapter;
import database.DatabaseHelper;
import logic.RecipeMatcher;
import model.PantryItem;
import model.Recipe;
import model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity
        extends AppCompatActivity {

    private RecyclerView recyclerRecipes;

    private TextView txtNoRecipes;

    private DatabaseHelper databaseHelper;

    private RecipeAdapter adapter;

    private List<Recipe> suggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_suggested_recipes
        );

        recyclerRecipes =
                findViewById(
                        R.id.recyclerRecipes
                );

        txtNoRecipes =
                findViewById(
                        R.id.txtNoRecipes
                );

        databaseHelper =
                new DatabaseHelper(this);

        suggestedRecipes =
                new ArrayList<>();

        adapter =
                new RecipeAdapter(
                        this,
                        suggestedRecipes
                );

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerRecipes.setAdapter(adapter);

        loadSuggestedRecipes();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (databaseHelper != null) {
            loadSuggestedRecipes();
        }
    }

    private void loadSuggestedRecipes() {

        suggestedRecipes.clear();

        List<PantryItem> pantryItems =
                loadPantryItems();

        List<Recipe> allRecipes =
                databaseHelper.getAllRecipes();

        for (Recipe recipe : allRecipes) {

            List<RecipeIngredient> requiredIngredients =
                    databaseHelper.getRecipeIngredients(
                            recipe.getId()
                    );

            boolean canMake =
                    RecipeMatcher.canMakeRecipe(
                            recipe,
                            requiredIngredients,
                            pantryItems
                    );

            if (canMake) {

                suggestedRecipes.add(recipe);
            }
        }

        adapter.notifyDataSetChanged();

        if (suggestedRecipes.isEmpty()) {

            txtNoRecipes.setVisibility(
                    View.VISIBLE
            );

        } else {

            txtNoRecipes.setVisibility(
                    View.GONE
            );
        }
    }

    private List<PantryItem> loadPantryItems() {

        List<PantryItem> items =
                new ArrayList<>();

        Cursor cursor =
                databaseHelper.getAllPantryItems();

        if (cursor != null) {

            while (cursor.moveToNext()) {

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_PANTRY_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_PANTRY_NAME
                                )
                        );

                double quantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_PANTRY_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_PANTRY_UNIT
                                )
                        );

                String expiry =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_PANTRY_EXPIRY
                                )
                        );

                items.add(
                        new PantryItem(
                                id,
                                name,
                                quantity,
                                unit,
                                expiry
                        )
                );
            }

            cursor.close();
        }

        return items;
    }
}