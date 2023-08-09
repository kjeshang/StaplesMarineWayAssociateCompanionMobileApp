package com.example.staplesmarinewayassociatecompanionapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BinEditActivity extends AppCompatActivity {

    Button btn_binEditBackToBin, btn_binEditDelete, btn_binEditUpdate, btn_binEditStaplesWebsite;
    TextView tv_binEditItemNumber, tv_binEditItemName, tv_binEditTypicalLocation;
    Product product;
    EditText et_binEditStockInCount, et_binEditStockOutCount;
    BinEntry binEntry;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_edit);

        btn_binEditBackToBin = findViewById(R.id.btn_binEditBackToBin);
        btn_binEditDelete = findViewById(R.id.btn_binEditDelete);
        btn_binEditUpdate = findViewById(R.id.btn_binEditUpdate);
        tv_binEditItemNumber = findViewById(R.id.tv_binEditItemNumber);
        tv_binEditItemName = findViewById(R.id.tv_binEditItemName);
        tv_binEditTypicalLocation = findViewById(R.id.tv_binEditTypicalLocation);
        et_binEditStockInCount = findViewById(R.id.et_binEditStockInCount);
        et_binEditStockOutCount = findViewById(R.id.et_binEditStockOutCount);
        btn_binEditStaplesWebsite = findViewById(R.id.btn_binEditStaplesWebsite);

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        String product = preference.getString("product",null);
        //tv_binEditItemNumber.setText(product);
        binEntry = databaseHelper.getBinEntryByProduct(product);
        setBinEntry(binEntry);

        btn_binEditBackToBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BinEditActivity.this, BinActivity.class));
            }
        });

        btn_binEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteBinEntry(binEntry);
                Toast.makeText(BinEditActivity.this, "Bin Entry Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BinEditActivity.this, BinActivity.class));
            }
        });

        btn_binEditUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((et_binEditStockInCount.getText().toString().length() == 0) | (et_binEditStockOutCount.getText().toString().length() == 0)){
                    Toast.makeText(BinEditActivity.this, "Stock-In/Stock-Out Count cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    int stockInCount = Integer.parseInt(et_binEditStockInCount.getText().toString());
                    int stockOutCount = Integer.parseInt(et_binEditStockOutCount.getText().toString());
                    //String productName = tv_binEditItemName.getText().toString();
                    //String product = tv_binEditItemNumber.getText().toString();

                    binEntry.setStockInCount(stockInCount);
                    binEntry.setStockOutCount(stockOutCount);
                    //binEntry.setProductName(productName);
                    //binEntry.setProduct(product);

                    databaseHelper.updateBinEntry(binEntry);
                    Toast.makeText(BinEditActivity.this, "Updated Bin Entry for Product " + binEntry.getProduct(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_binEditStaplesWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = databaseHelper.getProductByItemNumber(tv_binEditItemNumber.getText().toString());
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getWebsiteLink()));
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(BinEditActivity.this, "Website Link was not provided", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setBinEntry(BinEntry binEntry){
        tv_binEditItemNumber.setText(binEntry.getProduct());
        tv_binEditItemName.setText(binEntry.getProductName());
        tv_binEditTypicalLocation.setText(databaseHelper.getProductByItemNumber(binEntry.getProduct()).getTypicalLocation());
        et_binEditStockInCount.setText(Integer.toString(binEntry.getStockInCount()));
        et_binEditStockOutCount.setText(Integer.toString(binEntry.getStockOutCount()));
    }

}
