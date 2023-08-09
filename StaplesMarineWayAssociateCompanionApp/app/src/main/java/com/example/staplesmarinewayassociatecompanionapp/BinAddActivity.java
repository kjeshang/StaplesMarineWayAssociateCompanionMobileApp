package com.example.staplesmarinewayassociatecompanionapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BinAddActivity extends AppCompatActivity {

    private List<String> spinnerData = new ArrayList<>();
    Button btn_backToBinFromBinAdd, btn_insertProductToBin, btn_binAddSearchProduct, btn_binAddStaplesWebsite;
    AutoCompleteTextView autotv_binAddSearchProduct;
    SearchableAdapter adapter;
    TextView tv_binAddItemNumber, tv_binAddTypicalLocation;
    EditText et_binAddStockInCount, et_binAddStockOutCount;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_add);

        autotv_binAddSearchProduct = findViewById(R.id.autotv_binAddSearchProduct);
        tv_binAddItemNumber = findViewById(R.id.tv_binAddItemNumber);
        tv_binAddTypicalLocation = findViewById(R.id.tv_binAddTypicalLocation);
        btn_backToBinFromBinAdd = findViewById(R.id.btn_backToBinFromBinAdd);
        btn_insertProductToBin = findViewById(R.id.btn_insertProductToBin);
        btn_binAddSearchProduct = findViewById(R.id.btn_binAddSearchProduct);
        btn_binAddStaplesWebsite = findViewById(R.id.btn_binAddStaplesWebsite);
        et_binAddStockInCount = findViewById(R.id.et_binAddStockInCount);
        et_binAddStockOutCount = findViewById(R.id.et_binAddStockOutCount);

        databaseHelper = DatabaseHelper.getInstance(this);

        List<Product> productList = databaseHelper.getAllProducts(autotv_binAddSearchProduct.getText().toString());
        for(int i=0; i < productList.size(); i++) {
            spinnerData.add(productList.get(i).getItemName());
        }
        adapter = new SearchableAdapter(this, android.R.layout.simple_dropdown_item_1line, spinnerData);
        autotv_binAddSearchProduct.setAdapter(adapter);

        btn_backToBinFromBinAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BinAddActivity.this, BinActivity.class));
            }
        });

        btn_binAddSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = autotv_binAddSearchProduct.getText().toString();
                if(itemName.length() > 0) {
                    Product selectedProduct = databaseHelper.getProductByItemName(itemName);
                    if(selectedProduct != null) {
                        tv_binAddItemNumber.setText(selectedProduct.getItemNumber());
                        tv_binAddTypicalLocation.setText(selectedProduct.getTypicalLocation());
                    }
                    else {
                        tv_binAddItemNumber.setText("");
                        tv_binAddItemNumber.setHint(R.string.tv_binAddItemNumber);

                        tv_binAddTypicalLocation.setText("");
                        tv_binAddTypicalLocation.setHint(R.string.tv_binAddTypicalLocation);
                    }
                }
                else {
                    tv_binAddItemNumber.setText("");
                    tv_binAddItemNumber.setHint(R.string.tv_binAddItemNumber);

                    tv_binAddTypicalLocation.setText("");
                    tv_binAddTypicalLocation.setHint(R.string.tv_binAddTypicalLocation);
                }

            }
        });

        btn_binAddStaplesWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(autotv_binAddSearchProduct.getText().toString().length() == 0) {
                    Toast.makeText(BinAddActivity.this, "Item Name is Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Product product = databaseHelper.getProductByItemNumber(tv_binAddItemNumber.getText().toString());
                    try{
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getWebsiteLink()));
                        startActivity(intent);
                    }
                    catch (Exception e){
                        Toast.makeText(BinAddActivity.this, "Website Link was not provided", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_insertProductToBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(autotv_binAddSearchProduct.getText().toString().length() == 0) {
                    Toast.makeText(BinAddActivity.this, "Item Name is Empty", Toast.LENGTH_SHORT).show();
                }
                else if ((et_binAddStockOutCount.getText().toString().length() == 0) | (et_binAddStockOutCount.getText().toString().length() == 0)) {
                    Toast.makeText(BinAddActivity.this, "Stock In/Out Count is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    int stockInCount = Integer.parseInt(et_binAddStockOutCount.getText().toString());
                    int stockOutCount = Integer.parseInt(et_binAddStockOutCount.getText().toString());
                    String product = tv_binAddItemNumber.getText().toString();
                    String productName = autotv_binAddSearchProduct.getText().toString();
                    BinEntry binEntry = new BinEntry(stockInCount, stockOutCount, productName, product);
                    if(databaseHelper.checkIfBinEntryExists(binEntry.getProduct()) == true){
                        Toast.makeText(BinAddActivity.this, "Bin Entry for this product already exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        databaseHelper.insertBinEntry(binEntry);
                        Toast.makeText(BinAddActivity.this, binEntry.toString(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(BinAddActivity.this, BinActivity.class));
                    }
                }
            }
        });


    }
}
