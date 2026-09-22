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

        IntentData();
    }

    private void IntentData() {

        if (getIntent().hasExtra("item_id")) {

            itemId =
                    getIntent().getIntExtra(
                            "item_id",
                            -1
                    );

            String name =
                    getIntent().getStringExtra(
                            "item_name"
                    );

            double quantity =
                    getIntent().getDoubleExtra(
                            "item_quantity",
                            0
                    );

            String unit =
                    getIntent().getStringExtra(
                            "item_unit"
                    );

            String expiry =
                    getIntent().getStringExtra(
                            "item_expiry"
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
            }

            btnSaveIngredient.setText(
                    "Update Ingredient"
            );
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

        // Validation
        if (name.isEmpty()) {

            editIngredientName.setError(
                    "Enter an ingredient name"
            );

            editIngredientName.requestFocus();

            return;
        }

        if (quantityText.isEmpty()) {

            editQuantity.setError(
                    "Enter a quantity"
            );

            editQuantity.requestFocus();

            return;
        }

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

        if (quantity <= 0) {

            editQuantity.setError(
                    "Quantity must be greater than zero"
            );

            editQuantity.requestFocus();

            return;
        }

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
