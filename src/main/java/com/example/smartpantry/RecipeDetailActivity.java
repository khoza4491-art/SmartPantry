package com.example.smartpantry;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import database.DatabaseHelper;
import model.Recipe;
import model.RecipeIngredient;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeDetailName;
    private TextView txtRecipeIngredients;
    private TextView txtRecipeInstructions;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_recipe_detail
        );

        txtRecipeDetailName =
                findViewById(
                        R.id.txtRecipeDetailName
                );

        txtRecipeIngredients =
                findViewById(
                        R.id.txtRecipeIngredients
                );

        txtRecipeInstructions =
                findViewById(
                        R.id.txtRecipeInstructions
                );

        databaseHelper =
                new DatabaseHelper(this);

        int recipeId =
                getIntent().getIntExtra(
                        "recipe_id",
                        -1
                );

        if (recipeId == -1) {

            Toast.makeText(
                    this,
                    "Recipe not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        loadRecipe(recipeId);
    }

    private void loadRecipe(int recipeId) {

        List<Recipe> recipes =
                databaseHelper.getAllRecipes();

        Recipe selectedRecipe = null;

        for (Recipe recipe : recipes) {

            if (recipe.getId() == recipeId) {

                selectedRecipe = recipe;

                break;
            }
        }

        if (selectedRecipe == null) {

            Toast.makeText(
                    this,
                    "Recipe not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

            return;
        }

        txtRecipeDetailName.setText(
                selectedRecipe.getName()
        );

        txtRecipeInstructions.setText(
                selectedRecipe.getInstructions()
        );

        List<RecipeIngredient> ingredients =
                databaseHelper.getRecipeIngredients(
                        recipeId
                );

        StringBuilder ingredientText =
                new StringBuilder();

        for (RecipeIngredient ingredient :
                ingredients) {

            ingredientText
                    .append("• ")
                    .append(ingredient.getName())
                    .append(" - ")
                    .append(ingredient.getRequiredQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }

        txtRecipeIngredients.setText(
                ingredientText.toString()
        );
    }
}