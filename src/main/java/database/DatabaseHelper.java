package database;

import java.util.ArrayList;
import java.util.List;

import model.Recipe;
import model.RecipeIngredient;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    // Pantry table
    public static final String TABLE_PANTRY = "pantry_items";
    public static final String COL_PANTRY_ID = "id";
    public static final String COL_PANTRY_NAME = "name";
    public static final String COL_PANTRY_QUANTITY = "quantity";
    public static final String COL_PANTRY_UNIT = "unit";
    public static final String COL_PANTRY_EXPIRY = "expiry_date";

    // Recipes table
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "id";
    public static final String COL_RECIPE_NAME = "name";
    public static final String COL_RECIPE_INSTRUCTIONS = "instructions";

    // Recipe ingredients table
    public static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    public static final String COL_INGREDIENT_ID = "id";
    public static final String COL_INGREDIENT_RECIPE_ID = "recipe_id";
    public static final String COL_INGREDIENT_NAME = "ingredient_name";
    public static final String COL_INGREDIENT_QUANTITY =
            "required_quantity";
    public static final String COL_INGREDIENT_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Pantry table
        db.execSQL(
                "CREATE TABLE " + TABLE_PANTRY + " (" +
                        COL_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_PANTRY_NAME + " TEXT NOT NULL, " +
                        COL_PANTRY_QUANTITY + " REAL NOT NULL, " +
                        COL_PANTRY_UNIT + " TEXT NOT NULL, " +
                        COL_PANTRY_EXPIRY + " TEXT" +
                        ")"
        );

        // Recipes table
        db.execSQL(
                "CREATE TABLE " + TABLE_RECIPES + " (" +
                        COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_RECIPE_NAME + " TEXT NOT NULL, " +
                        COL_RECIPE_INSTRUCTIONS + " TEXT NOT NULL" +
                        ")"
        );

        // Recipe ingredients table
        db.execSQL(
                "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                        COL_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_INGREDIENT_RECIPE_ID + " INTEGER NOT NULL, " +
                        COL_INGREDIENT_NAME + " TEXT NOT NULL, " +
                        COL_INGREDIENT_QUANTITY + " REAL NOT NULL, " +
                        COL_INGREDIENT_UNIT + " TEXT NOT NULL, " +
                        "FOREIGN KEY (" + COL_INGREDIENT_RECIPE_ID +
                        ") REFERENCES " + TABLE_RECIPES +
                        "(" + COL_RECIPE_ID + ")" +
                        ")"
        );

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +
                TABLE_RECIPE_INGREDIENTS);

        db.execSQL("DROP TABLE IF EXISTS " +
                TABLE_RECIPES);

        db.execSQL("DROP TABLE IF EXISTS " +
                TABLE_PANTRY);

        onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {

        addRecipe(
                db,
                "Tomato Pasta",
                "Boil pasta. Cook tomatoes with seasoning. " +
                        "Mix the sauce with pasta and serve.",
                new String[]{"pasta", "tomato", "onion"},
                new double[]{200, 2, 1},
                new String[]{"g", "unit", "unit"}
        );

        addRecipe(
                db,
                "Cheese Omelette",
                "Beat the eggs. Add cheese and cook in a pan " +
                        "until the omelette is set.",
                new String[]{"egg", "cheese"},
                new double[]{2, 50},
                new String[]{"unit", "g"}
        );

        addRecipe(
                db,
                "Chicken Stir Fry",
                "Cook chicken in a pan. Add vegetables and " +
                        "stir fry until cooked.",
                new String[]{"chicken", "carrot", "onion"},
                new double[]{250, 2, 1},
                new String[]{"g", "unit", "unit"}
        );

        addRecipe(
                db,
                "Vegetable Soup",
                "Cook the vegetables in water or stock until " +
                        "soft. Season and serve.",
                new String[]{"carrot", "potato", "onion"},
                new double[]{2, 2, 1},
                new String[]{"unit", "unit", "unit"}
        );

        addRecipe(
                db,
                "Pancakes",
                "Mix flour, milk and egg into a batter. " +
                        "Cook portions in a pan.",
                new String[]{"flour", "milk", "egg"},
                new double[]{200, 250, 1},
                new String[]{"g", "ml", "unit"}
        );

        addRecipe(
                db,
                "Fried Rice",
                "Cook rice with egg and vegetables in a hot pan.",
                new String[]{"rice", "egg", "carrot"},
                new double[]{300, 2, 1},
                new String[]{"g", "unit", "unit"}
        );

        addRecipe(
                db,
                "Garlic Pasta",
                "Cook pasta and combine with garlic and oil.",
                new String[]{"pasta", "garlic", "oil"},
                new double[]{200, 2, 20},
                new String[]{"g", "clove", "ml"}
        );

        addRecipe(
                db,
                "Chicken Pasta",
                "Cook chicken and pasta separately, then combine.",
                new String[]{"chicken", "pasta", "tomato"},
                new double[]{200, 200, 2},
                new String[]{"g", "g", "unit"}
        );

        addRecipe(
                db,
                "Mashed Potatoes",
                "Boil potatoes until soft. Mash with milk.",
                new String[]{"potato", "milk"},
                new double[]{3, 100},
                new String[]{"unit", "ml"}
        );

        addRecipe(
                db,
                "Egg Fried Rice",
                "Fry rice with eggs and seasoning.",
                new String[]{"rice", "egg"},
                new double[]{300, 2},
                new String[]{"g", "unit"}
        );

        addRecipe(
                db,
                "Tomato Omelette",
                "Cook eggs with chopped tomatoes and onion.",
                new String[]{"egg", "tomato", "onion"},
                new double[]{2, 2, 1},
                new String[]{"unit", "unit", "unit"}
        );

        addRecipe(
                db,
                "Chicken Soup",
                "Simmer chicken, carrot and potato until tender.",
                new String[]{"chicken", "carrot", "potato"},
                new double[]{250, 2, 2},
                new String[]{"g", "unit", "unit"}
        );

        addRecipe(
                db,
                "Vegetable Pasta",
                "Cook pasta and mix with cooked vegetables.",
                new String[]{"pasta", "carrot", "tomato"},
                new double[]{200, 1, 2},
                new String[]{"g", "unit", "unit"}
        );

        addRecipe(
                db,
                "Cheese Pasta",
                "Cook pasta and mix with melted cheese.",
                new String[]{"pasta", "cheese"},
                new double[]{200, 100},
                new String[]{"g", "g"}
        );

        addRecipe(
                db,
                "Chicken Sandwich",
                "Place cooked chicken and tomato between bread.",
                new String[]{"bread", "chicken", "tomato"},
                new double[]{2, 150, 1},
                new String[]{"slice", "g", "unit"}
        );

        addRecipe(
                db,
                "Toast and Eggs",
                "Toast the bread and serve with cooked eggs.",
                new String[]{"bread", "egg"},
                new double[]{2, 2},
                new String[]{"slice", "unit"}
        );

        addRecipe(
                db,
                "Potato Omelette",
                "Cook potatoes and combine with beaten eggs.",
                new String[]{"potato", "egg", "onion"},
                new double[]{2, 2, 1},
                new String[]{"unit", "unit", "unit"}
        );

        addRecipe(
                db,
                "Tomato Soup",
                "Cook tomatoes and onion until soft, then blend.",
                new String[]{"tomato", "onion"},
                new double[]{4, 1},
                new String[]{"unit", "unit"}
        );

        addRecipe(
                db,
                "Chicken and Rice",
                "Cook chicken and serve with cooked rice.",
                new String[]{"chicken", "rice"},
                new double[]{250, 300},
                new String[]{"g", "g"}
        );

        addRecipe(
                db,
                "Vegetable Fried Rice",
                "Fry rice with carrots, onion and egg.",
                new String[]{"rice", "carrot", "onion", "egg"},
                new double[]{300, 1, 1, 1},
                new String[]{"g", "unit", "unit", "unit"}
        );
    }

    private void addRecipe(
            SQLiteDatabase db,
            String recipeName,
            String instructions,
            String[] ingredients,
            double[] quantities,
            String[] units) {

        ContentValues recipeValues = new ContentValues();

        recipeValues.put(
                COL_RECIPE_NAME,
                recipeName
        );

        recipeValues.put(
                COL_RECIPE_INSTRUCTIONS,
                instructions
        );

        long recipeId =
                db.insert(
                        TABLE_RECIPES,
                        null,
                        recipeValues
                );

        for (int i = 0; i < ingredients.length; i++) {

            ContentValues ingredientValues =
                    new ContentValues();

            ingredientValues.put(
                    COL_INGREDIENT_RECIPE_ID,
                    recipeId
            );

            ingredientValues.put(
                    COL_INGREDIENT_NAME,
                    ingredients[i]
            );

            ingredientValues.put(
                    COL_INGREDIENT_QUANTITY,
                    quantities[i]
            );

            ingredientValues.put(
                    COL_INGREDIENT_UNIT,
                    units[i]
            );

            db.insert(
                    TABLE_RECIPE_INGREDIENTS,
                    null,
                    ingredientValues
            );
        }
    }

    // CREATE pantry item
    public long addPantryItem(
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_PANTRY_NAME, name);
        values.put(COL_PANTRY_QUANTITY, quantity);
        values.put(COL_PANTRY_UNIT, unit);
        values.put(COL_PANTRY_EXPIRY, expiryDate);

        return db.insert(
                TABLE_PANTRY,
                null,
                values
        );
    }

    // READ pantry items
    public Cursor getAllPantryItems() {

        SQLiteDatabase db = getReadableDatabase();

        return db.query(
                TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                COL_PANTRY_NAME + " ASC"
        );
    }

    // UPDATE pantry item
    public int updatePantryItem(
            int id,
            String name,
            double quantity,
            String unit,
            String expiryDate) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_PANTRY_NAME, name);
        values.put(COL_PANTRY_QUANTITY, quantity);
        values.put(COL_PANTRY_UNIT, unit);
        values.put(COL_PANTRY_EXPIRY, expiryDate);

        return db.update(
                TABLE_PANTRY,
                values,
                COL_PANTRY_ID + "=?",
                new String[]{String.valueOf(id)}
        );
    }

    // DELETE pantry item
    public int deletePantryItem(int id) {

        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                TABLE_PANTRY,
                COL_PANTRY_ID + "=?",
                new String[]{String.valueOf(id)}
        );
    }
    public List<Recipe> getAllRecipes() {

        List<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                COL_RECIPE_NAME + " ASC"
        );

        while (cursor.moveToNext()) {

            int id = cursor.getInt(
                    cursor.getColumnIndexOrThrow(
                            COL_RECIPE_ID
                    )
            );

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            COL_RECIPE_NAME
                    )
            );

            String instructions = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            COL_RECIPE_INSTRUCTIONS
                    )
            );

            recipes.add(
                    new Recipe(
                            id,
                            name,
                            instructions
                    )
            );
        }

        cursor.close();

        return recipes;
    }
    public List<RecipeIngredient> getRecipeIngredients(
            int recipeId) {

        List<RecipeIngredient> ingredients =
                new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPE_INGREDIENTS,
                null,
                COL_INGREDIENT_RECIPE_ID + "=?",
                new String[]{
                        String.valueOf(recipeId)
                },
                null,
                null,
                COL_INGREDIENT_NAME + " ASC"
        );

        while (cursor.moveToNext()) {

            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            COL_INGREDIENT_NAME
                    )
            );

            double quantity = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(
                            COL_INGREDIENT_QUANTITY
                    )
            );

            String unit = cursor.getString(
                    cursor.getColumnIndexOrThrow(
                            COL_INGREDIENT_UNIT
                    )
            );

            ingredients.add(
                    new RecipeIngredient(
                            recipeId,
                            name,
                            quantity,
                            unit
                    )
            );
        }

        cursor.close();

        return ingredients;
    }
}
