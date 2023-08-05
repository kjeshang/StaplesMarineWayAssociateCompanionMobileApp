package com.example.staplesmarinewayassociatecompanionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BinActivity extends AppCompatActivity implements Product_RecyclerViewInterface {

    Button btn_binBackToMain, btn_binAdd;
    RadioGroup RadioGroup_binStock;
    RadioButton rdbtn_binAll, rdbtn_binStockIn, rdbtn_btnStockOut;
    List<BinEntry> binEntry_list;
    RecyclerView recyclerView;
    BinEntry_RecyclerViewAdapter adapter;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin);

        databaseHelper = DatabaseHelper.getInstance(this);

        btn_binBackToMain = findViewById(R.id.btn_binBackToMain);
        btn_binAdd = findViewById(R.id.btn_binAdd);
        RadioGroup_binStock = findViewById(R.id.RadioGroup_binStock);
        rdbtn_binAll = findViewById(R.id.rdbtn_binAll);
        rdbtn_binAll.setChecked(true);
        rdbtn_binStockIn = findViewById(R.id.rdbtn_binStockIn);
        rdbtn_binStockIn.setChecked(false);
        rdbtn_btnStockOut = findViewById(R.id.rdbtn_btnStockOut);
        rdbtn_btnStockOut.setChecked(false);

        binEntry_list = databaseHelper.getAllBinEntries("All");

        recyclerView = findViewById(R.id.RecyclerView_Bin);

        showBinEntriesOnListView(binEntry_list);

        // Back Button:
        btn_binBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BinActivity.this, MainActivity_v2.class));
            }
        });

        // Add Button:
        btn_binAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BinActivity.this, BinAddActivity.class));
            }
        });

        RadioGroup_binStock.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String condition = null;
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton radio = findViewById(id);
                if(R.id.rdbtn_binStockIn == radio.getId()){
                    condition = "StockIn";
                }
                else if (R.id.rdbtn_btnStockOut == radio.getId()) {
                    condition = "StockOut";
                }
                else {
                    condition = "All";
                }
                binEntry_list = databaseHelper.getAllBinEntries(condition);
                showBinEntriesOnListView(binEntry_list);
            }
        });

    }

    private void showBinEntriesOnListView(List<BinEntry> binEntry_list) {
        //arrayAdapter = new ArrayAdapter<Product>(MainActivity_v2.this, R.layout.listview_layout, databaseHelper.getAllProducts(sv_searchProduct.getQuery().toString()));
        //lv_showProduct.setAdapter(arrayAdapter);
        //adapter = new Product_RecyclerViewAdapter(this, (ArrayList<Product>) databaseHelper.getAllProducts(sv_searchProduct.getQuery().toString()), this);
        adapter = new BinEntry_RecyclerViewAdapter(this, (ArrayList<BinEntry>) binEntry_list, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        BinEntry selectedBinEntry = binEntry_list.get(position);
        boolean status = databaseHelper.checkIfBinEntryExists(selectedBinEntry.getProduct());
        if(status == true){
            Toast.makeText(BinActivity.this, selectedBinEntry.toString(), Toast.LENGTH_SHORT).show();
            editor.putString("product", selectedBinEntry.getProduct());
            editor.commit();
            startActivity(new Intent(BinActivity.this, BinEditActivity.class));
        }
    }
}
