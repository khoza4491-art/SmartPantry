package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import database.DatabaseHelper;

public class AddEditPantryActivity extends AppCompatActivity {

    private EditText editIngredientName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiry;

    private TextView txtFormTitle;

    private Button btnSaveIngredient;

    private DatabaseHelper databaseHelper;

    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_add_edit_pantry
        );

        txtFormTitle =
                findViewById(R.id.txtFormTitle);

        editIngredientName =
                findViewById(R.id.editIngredientName);

        editQuantity =
                findViewById(R.id.editQuantity);

        editUnit =
                findViewById(R.id.editUnit);

        editExpiry =
                findViewById(R.id.editExpiry);

        btnSaveIngredient =
                findViewById(R.id.btnSaveIngredient);

        databaseHelper =
                new DatabaseHelper(this);

        loadExistingItem();

        btnSaveIngredient.setOnClickListener(
                v -> saveIngredient()
        );
    }

    private void loadExistingItem() {

        Intent intent = getIntent();

        /*
         * PantryActivity sends the existing item's ID
         * using the key "itemId".
         */
        if (intent.hasExtra("itemId")) {

            itemId =
                    intent.getIntExtra(
                            "itemId",
                            -1
                    );

            loadItemFromDatabase();

        } else {

            txtFormTitle.setText(
                    "Add Ingredient"
            );

            btnSaveIngredient.setText(
                    "Add Ingredient"
            );
        }
    }

    private void loadItemFromDatabase() {

        /*
         * Read the existing pantry item from SQLite
         * using its ID.
         */
        android.database.Cursor cursor =
                databaseHelper.getReadableDatabase().query(
                        DatabaseHelper.TABLE_PANTRY,
                        null,
                        DatabaseHelper.COL_PANTRY_ID + "=?",
                        new String[]{
                                String.valueOf(itemId)
                        },
                        null,
                        null,
                        null
                );

        if (cursor != null && cursor.moveToFirst()) {

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

            txtFormTitle.setText(
                    "Edit Ingredient"
            );

            editIngredientName.setText(name);

            editQuantity.setText(
                    String.valueOf(quantity)
            );

            editUnit.setText(unit);

            if (expiry != null) {

                editExpiry.setText(expiry);

            } else {

                editExpiry.setText("");
            }

            btnSaveIngredient.setText(
                    "Update Ingredient"
            );

        } else {

            Toast.makeText(
                    this,
                    "Ingredient not found",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void saveIngredient() {

        String name =
                editIngredientName
                        .getText()
                        .toString()
                        .trim();

        String quantityText =
                editQuantity
                        .getText()
                        .toString()
                        .trim();

        String unit =
                editUnit
                        .getText()
                        .toString()
                        .trim();

        String expiry =
                editExpiry
                        .getText()
                        .toString()
                        .trim();

        // Validate ingredient name
        if (name.isEmpty()) {

            editIngredientName.setError(
                    "Enter an ingredient name"
            );

            editIngredientName.requestFocus();

            return;
        }

        // Validate quantity
        if (quantityText.isEmpty()) {

            editQuantity.setError(
                    "Enter a quantity"
            );

            editQuantity.requestFocus();

            return;
        }

        // Validate unit
        if (unit.isEmpty()) {

            editUnit.setError(
                    "Enter a unit"
            );

            editUnit.requestFocus();

            return;
        }

        double quantity;

        try {

            quantity =
                    Double.parseDouble(
                            quantityText
                    );

        } catch (NumberFormatException e) {

            editQuantity.setError(
                    "Enter a valid number"
            );

            editQuantity.requestFocus();

            return;
        }

        // Quantity must be positive
        if (quantity <= 0) {

            editQuantity.setError(
                    "Quantity must be greater than zero"
            );

            editQuantity.requestFocus();

            return;
        }

        /*
         * itemId == -1 means this is a NEW pantry item.
         */
        if (itemId == -1) {

            long result =
                    databaseHelper.addPantryItem(
                            name,
                            quantity,
                            unit,
                            expiry
                    );

            if (result != -1) {

                Toast.makeText(
                        this,
                        "Ingredient added",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Unable to add ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            /*
             * Existing item: UPDATE the database record
             * instead of creating a new one.
             */
            int result =
                    databaseHelper.updatePantryItem(
                            itemId,
                            name,
                            quantity,
                            unit,
                            expiry
                    );

            if (result > 0) {

                Toast.makeText(
                        this,
                        "Ingredient updated",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Unable to update ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}
