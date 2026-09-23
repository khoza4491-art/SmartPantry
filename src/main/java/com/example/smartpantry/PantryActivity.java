package com.example.smartpantry;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.PantryAdapter;
import database.DatabaseHelper;
import model.PantryItem;

public class PantryActivity extends AppCompatActivity
        implements PantryAdapter.OnPantryItemActionListener {

    private RecyclerView recyclerViewPantry;
    private PantryAdapter pantryAdapter;
    private DatabaseHelper databaseHelper;

    private TextView txtEmptyPantry;
    private Button btnAddIngredient;

    private List<PantryItem> pantryItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantry);

        databaseHelper = new DatabaseHelper(this);

        recyclerViewPantry =
                findViewById(R.id.recyclerViewPantry);

        txtEmptyPantry =
                findViewById(R.id.txtEmptyPantry);

        btnAddIngredient =
                findViewById(R.id.btnAddIngredient);


        recyclerViewPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );


        pantryItems = new ArrayList<>();

        pantryAdapter =
                new PantryAdapter(
                        pantryItems,
                        this
                );

        recyclerViewPantry.setAdapter(
                pantryAdapter
        );


        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
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

        loadPantryItems();
    }


    private void loadPantryItems() {

        List<PantryItem> items =
                new ArrayList<>();


        Cursor cursor =
                databaseHelper.getAllPantryItems();


        if (cursor != null) {

            int idIndex =
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.COL_PANTRY_ID
                    );

            int nameIndex =
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.COL_PANTRY_NAME
                    );

            int quantityIndex =
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.COL_PANTRY_QUANTITY
                    );

            int unitIndex =
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.COL_PANTRY_UNIT
                    );

            int expiryIndex =
                    cursor.getColumnIndexOrThrow(
                            DatabaseHelper.COL_PANTRY_EXPIRY
                    );


            while (cursor.moveToNext()) {

                int id =
                        cursor.getInt(idIndex);

                String name =
                        cursor.getString(nameIndex);

                double quantity =
                        cursor.getDouble(quantityIndex);

                String unit =
                        cursor.getString(unitIndex);

                String expiryDate =
                        cursor.getString(expiryIndex);


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


        pantryItems = items;


        pantryAdapter.updateItems(
                pantryItems
        );


        updateEmptyState();
    }


    private void updateEmptyState() {

        if (pantryItems.isEmpty()) {

            txtEmptyPantry.setVisibility(
                    View.VISIBLE
            );

            recyclerViewPantry.setVisibility(
                    View.GONE
            );

        } else {

            txtEmptyPantry.setVisibility(
                    View.GONE
            );

            recyclerViewPantry.setVisibility(
                    View.VISIBLE
            );
        }
    }


    @Override
    public void onEdit(PantryItem item) {

        Intent intent =
                new Intent(
                        PantryActivity.this,
                        AddEditPantryActivity.class
                );


        intent.putExtra(
                "itemId",
                item.getId()
        );


        startActivity(intent);
    }


    @Override
    public void onDelete(PantryItem item) {

        new AlertDialog.Builder(this)

                .setTitle("Delete Ingredient")

                .setMessage(
                        "Are you sure you want to delete "
                                + item.getName()
                                + "?"
                )

                .setPositiveButton(
                        "Delete",
                        (dialog, which) -> {

                            databaseHelper.deletePantryItem(
                                    item.getId()
                            );


                            Toast.makeText(
                                    PantryActivity.this,
                                    "Ingredient deleted",
                                    Toast.LENGTH_SHORT
                            ).show();


                            loadPantryItems();
                        }
                )

                .setNegativeButton(
                        "Cancel",
                        null
                )

                .show();
    }
}