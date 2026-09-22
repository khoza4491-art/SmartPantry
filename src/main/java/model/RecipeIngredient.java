package model;


public class RecipeIngredient {

    private int recipeId;
    private String name;
    private double requiredQuantity;
    private String unit;

    public RecipeIngredient(
            int recipeId,
            String name,
            double requiredQuantity,
            String unit) {

        this.recipeId = recipeId;
        this.name = name;
        this.requiredQuantity = requiredQuantity;
        this.unit = unit;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public double getRequiredQuantity() {
        return requiredQuantity;
    }

    public String getUnit() {
        return unit;
    }
}
