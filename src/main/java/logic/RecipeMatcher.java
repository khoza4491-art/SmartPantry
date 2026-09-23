package logic;

import java.util.List;
import java.util.Locale;

import model.PantryItem;
import model.Recipe;
import model.RecipeIngredient;

public class RecipeMatcher {

    /*
     * Strict recipe matching.
     *
     * A recipe qualifies ONLY when every required
     * ingredient is present in the pantry with
     * sufficient quantity and a compatible unit.
     */
    public static boolean canMakeRecipe(
            Recipe recipe,
            List<RecipeIngredient> requiredIngredients,
            List<PantryItem> pantryItems) {

        if (recipe == null) {
            return false;
        }

        if (requiredIngredients == null
                || requiredIngredients.isEmpty()) {

            return false;
        }

        if (pantryItems == null
                || pantryItems.isEmpty()) {

            return false;
        }


        /*
         * Check EVERY ingredient required by
         * the recipe.
         */
        for (RecipeIngredient requiredIngredient
                : requiredIngredients) {

            boolean ingredientFound = false;


            /*
             * Search the user's pantry for a
             * matching ingredient.
             */
            for (PantryItem pantryItem : pantryItems) {

                if (pantryItem == null) {
                    continue;
                }


                boolean nameMatches =
                        namesMatch(
                                pantryItem.getName(),
                                requiredIngredient.getName()
                        );


                boolean unitMatches =
                        unitsMatch(
                                pantryItem.getUnit(),
                                requiredIngredient.getUnit()
                        );


                boolean quantityMatches =
                        pantryItem.getQuantity()
                                >= requiredIngredient
                                .getRequiredQuantity();


                /*
                 * The ingredient only qualifies when
                 * name, unit and quantity all match.
                 */
                if (nameMatches
                        && unitMatches
                        && quantityMatches) {

                    ingredientFound = true;

                    break;
                }
            }


            /*
             * If even ONE required ingredient is
             * missing or insufficient, the entire
             * recipe is rejected.
             */
            if (!ingredientFound) {

                return false;
            }
        }


        /*
         * Every required ingredient passed.
         */
        return true;
    }


    /*
     * Handles simple singular/plural differences.
     *
     * Examples:
     * tomato -> tomatoes
     * egg -> eggs
     * potato -> potatoes
     */
    private static boolean namesMatch(
            String pantryName,
            String requiredName) {

        if (pantryName == null
                || requiredName == null) {

            return false;
        }


        String pantry =
                normaliseIngredientName(
                        pantryName
                );

        String required =
                normaliseIngredientName(
                        requiredName
                );


        return pantry.equals(required);
    }


    private static String normaliseIngredientName(
            String name) {

        String value =
                name.trim()
                        .toLowerCase(Locale.ROOT);


        /*
         * Remove duplicate spaces.
         */
        value =
                value.replaceAll(
                        "\\s+",
                        " "
                );


        /*
         * Basic plural handling.
         */
        if (value.endsWith("ies")
                && value.length() > 3) {

            value =
                    value.substring(
                            0,
                            value.length() - 3
                    ) + "y";

        } else if (value.endsWith("oes")
                && value.length() > 3) {

            value =
                    value.substring(
                            0,
                            value.length() - 2
                    );

        } else if (value.endsWith("es")
                && value.length() > 2) {

            value =
                    value.substring(
                            0,
                            value.length() - 2
                    );

        } else if (value.endsWith("s")
                && value.length() > 1) {

            value =
                    value.substring(
                            0,
                            value.length() - 1
                    );
        }


        return value;
    }


    /*
     * Handles common unit differences.
     *
     * grams / gram -> g
     * millilitres / millilitre -> ml
     * units -> unit
     * cloves -> clove
     * slices -> slice
     */
    private static boolean unitsMatch(
            String pantryUnit,
            String requiredUnit) {

        if (pantryUnit == null
                || requiredUnit == null) {

            return false;
        }


        String pantry =
                normaliseUnit(pantryUnit);

        String required =
                normaliseUnit(requiredUnit);


        return pantry.equals(required);
    }


    private static String normaliseUnit(
            String unit) {

        String value =
                unit.trim()
                        .toLowerCase(Locale.ROOT);


        if (value.equals("gram")
                || value.equals("grams")) {

            return "g";
        }


        if (value.equals("millilitre")
                || value.equals("millilitres")
                || value.equals("milliliter")
                || value.equals("milliliters")) {

            return "ml";
        }


        if (value.equals("units")) {

            return "unit";
        }


        if (value.equals("cloves")) {

            return "clove";
        }


        if (value.equals("slices")) {

            return "slice";
        }


        return value;
    }
}
