package com.example.smartpantry;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import adapter.PantryAdapter;
import database.DatabaseHelper;
import model.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity
        implements PantryAdapter.OnPantryItemActionListener {

    private RecyclerView recyclerPantry;

    private Button btnAddIngredient;

    private DatabaseHelper databaseHelper;

    private PantryAdapter adapter;

    private List<PantryItem> pantryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry);

        recyclerPantry =
                findViewById(R.id.recyclerPantry);

        btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        databaseHelper =
                new DatabaseHelper(this);

        pantryItems =
                new ArrayList<>();

        adapter =
                new PantryAdapter(
                        pantryItems,
                        this
                );

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerPantry.setAdapter(adapter);

        btnAddIngredient.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            PantryActivity.this,
                            AddEditPantryActivity.class
                    );

            startActivity(intent);
        });

        loadPantryItems();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (databaseHelper != null) {

            loadPantryItems();
        }
    }

    private void loadPantryItems() {

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

                String expiryDate =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        DatabaseHelper.COL_PANTRY_EXPIRY
                                )
                        );

                PantryItem item =
                        new PantryItem(
                                id,
                                name,
                                quantity,
                                unit,
                                expiryDate
                        );

                items.add(item);
            }

            cursor.close();
        }

        adapter.updateItems(items);
    }

    @Override
    public void onEdit(PantryItem item) {

        Intent intent =
                new Intent(
                        PantryActivity.this,
                        AddEditPantryActivity.class
                );

        intent.putExtra("item_id", item.getId());
        intent.putExtra("item_name", item.getName());
        intent.putExtra("item_quantity", item.getQuantity());
        intent.putExtra("item_unit", item.getUnit());
        intent.putExtra("item_expiry", item.getExpiryDate());

        startActivity(intent);
    }

    @Override
    public void onDelete(PantryItem item) {

        int result =
                databaseHelper.deletePantryItem(
                        item.getId()
                );

        if (result > 0) {

            Toast.makeText(
                    this,
                    "Ingredient deleted",
                    Toast.LENGTH_SHORT
            ).show();

            loadPantryItems();

        } else {

            Toast.makeText(
                    this,
                    "Unable to delete ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
