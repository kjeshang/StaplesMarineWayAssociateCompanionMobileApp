package com.example.staplesmarinewayassociatecompanionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.staplesmarinewayassociatecompanionapp.R;

public class MainActivity extends AppCompatActivity {

    Button btn_goToAdd;
    ListView lv_showProduct;
    SearchView sv_searchProduct;

    ArrayAdapter arrayAdapter;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = DatabaseHelper.getInstance(this);

        btn_goToAdd = findViewById(R.id.btn_goToAdd);
        lv_showProduct = findViewById(R.id.lv_showProduct);
        sv_searchProduct = findViewById(R.id.sv_searchProduct);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);

        showProductsOnListView(databaseHelper);

        btn_goToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        sv_searchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                showProductsOnListView(databaseHelper);
                return false;
            }
        });

        lv_showProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences.Editor editor = preference.edit();
                editor.clear().commit();
                Product clickedProduct = (Product) adapterView.getItemAtPosition(i);
                boolean status = databaseHelper.checkIfProductExists(clickedProduct.getItemNumber());
                if(status == true){
                    editor.putString("itemNumber", clickedProduct.getItemNumber());
                    editor.commit();
                    startActivity(new Intent(MainActivity.this, EditActivity.class));
                }
            }
        });

    }

    private void showProductsOnListView(DatabaseHelper databaseHelper) {
        arrayAdapter = new ArrayAdapter<Product>(MainActivity.this, R.layout.listview_layout, databaseHelper.getAllProducts(sv_searchProduct.getQuery().toString()));
        lv_showProduct.setAdapter(arrayAdapter);
    }
}