package com.example.staplesmarinewayassociatecompanionapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_v2 extends AppCompatActivity implements Product_RecyclerViewInterface {

    Button btn_goToAdd, btn_goToBinning;
    ImageView imgbtn_goToWebsiteFromSearch;
    //ListView lv_showProduct;
    SearchView sv_searchProduct;
    List<Product> product_list;
    //ArrayAdapter arrayAdapter;
    RecyclerView recyclerView;
    Product_RecyclerViewAdapter adapter;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);
        databaseHelper = DatabaseHelper.getInstance(this);

        btn_goToAdd = findViewById(R.id.btn_goToAdd);
        imgbtn_goToWebsiteFromSearch = findViewById(R.id.imgbtn_goToWebsiteFromSearch);
        //lv_showProduct = findViewById(R.id.lv_showProduct);
        recyclerView = findViewById(R.id.RecyclerView_Main);
        sv_searchProduct = findViewById(R.id.sv_searchProduct);
        btn_goToBinning = findViewById(R.id.btn_goToBinning);

        //SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        product_list = databaseHelper.getAllProducts(sv_searchProduct.getQuery().toString());

        showProductsOnListView(product_list);

        btn_goToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity_v2.this, AddActivity.class));
            }
        });

        imgbtn_goToWebsiteFromSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.staples.ca/"));
                startActivity(intent);
            }
        });

        sv_searchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                product_list = databaseHelper.getAllProducts(sv_searchProduct.getQuery().toString());
                showProductsOnListView(product_list);
                return false;
            }
        });

//        lv_showProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                SharedPreferences.Editor editor = preference.edit();
//                editor.clear().commit();
//                Product clickedProduct = (Product) adapterView.getItemAtPosition(i);
//                boolean status = databaseHelper.checkIfProductExists(clickedProduct.getItemNumber());
//                if(status == true){
//                    editor.putString("itemNumber", clickedProduct.getItemNumber());
//                    editor.commit();
//                    startActivity(new Intent(MainActivity_v2.this, EditActivity.class));
//                }
//            }
//        });

        btn_goToBinning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity_v2.this, BinActivity.class));
            }
        });

    }

    private void showProductsOnListView(List<Product> product_list) {
        //arrayAdapter = new ArrayAdapter<Product>(MainActivity_v2.this, R.layout.listview_layout, databaseHelper.getAllProducts(sv_searchProduct.getQuery().toString()));
        //lv_showProduct.setAdapter(arrayAdapter);
        //adapter = new Product_RecyclerViewAdapter(this, (ArrayList<Product>) databaseHelper.getAllProducts(sv_searchProduct.getQuery().toString()), this);
        adapter = new Product_RecyclerViewAdapter(this, (ArrayList<Product>) product_list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        Product clickedProduct = product_list.get(position);
        boolean status = databaseHelper.checkIfProductExists(clickedProduct.getItemNumber());
        if(status == true){
            String itemNumber = clickedProduct.getItemNumber();
            Toast.makeText(this,"Item " + itemNumber + " selected", Toast.LENGTH_SHORT).show();
            //editor.putString("itemNumber", clickedProduct.getItemNumber());
            editor.putString("itemNumber", itemNumber);
            editor.commit();
            startActivity(new Intent(MainActivity_v2.this, EditActivity.class));
        }
    }
}
