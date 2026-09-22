package logic;


import model.PantryItem;
import model.Recipe;
import model.RecipeIngredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
public class RecipeMatcher {

    public static boolean canMakeRecipe(
            Recipe recipe,
            List<RecipeIngredient> requiredIngredients,
            List<PantryItem> pantryItems) {

        Map<String, PantryItem> pantryMap =
                new HashMap<>();

        for (PantryItem pantryItem : pantryItems) {

            String normalizedName =
                    normalizeIngredientName(
                            pantryItem.getName()
                    );

            pantryMap.put(
                    normalizedName,
                    pantryItem
            );
        }

        // EVERY required ingredient must pass.
        for (RecipeIngredient required :
                requiredIngredients) {

            String requiredName =
                    normalizeIngredientName(
                            required.getName()
                    );

            PantryItem pantryItem =
                    pantryMap.get(requiredName);

            // Ingredient completely missing.
            if (pantryItem == null) {
                return false;
            }

            // Quantity is insufficient.
            if (pantryItem.getQuantity()
                    < required.getRequiredQuantity()) {

                return false;
            }

            // Unit compatibility.
            if (!unitsCompatible(
                    pantryItem.getUnit(),
                    required.getUnit())) {

                return false;
            }
        }

        // Only reaches here if EVERY
        // required ingredient passed.
        return true;
    }

    private static String normalizeIngredientName(
            String name) {

        String normalized =
                name.trim().toLowerCase();

        // Basic singular/plural handling.
        if (normalized.endsWith("ies")) {

            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 3
                    ) + "y";

        } else if (normalized.endsWith("oes")) {

            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 2
                    );

        } else if (normalized.endsWith("s")
                && !normalized.endsWith("ss")) {

            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 1
                    );
        }

        return normalized;
    }

    private static boolean unitsCompatible(
            String pantryUnit,
            String requiredUnit) {

        String pantry =
                pantryUnit.trim().toLowerCase();

        String required =
                requiredUnit.trim().toLowerCase();

        if (pantry.equals(required)) {
            return true;
        }

        // Basic equivalent units.
        if ((pantry.equals("unit")
                || pantry.equals("units"))
                &&
                (required.equals("unit")
                        || required.equals("units"))) {

            return true;
        }

        if ((pantry.equals("g")
                || pantry.equals("gram")
                || pantry.equals("grams"))
                &&
                (required.equals("g")
                        || required.equals("gram")
                        || required.equals("grams"))) {

            return true;
        }

        if ((pantry.equals("ml")
                || pantry.equals("millilitre")
                || pantry.equals("millilitres"))
                &&
                (required.equals("ml")
                        || required.equals("millilitre")
                        || required.equals("millilitres"))) {

            return true;
        }

        return false;
    }
}
