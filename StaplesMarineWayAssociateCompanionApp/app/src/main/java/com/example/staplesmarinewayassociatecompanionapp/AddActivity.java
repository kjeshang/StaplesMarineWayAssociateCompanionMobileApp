package com.example.staplesmarinewayassociatecompanionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    // Button Variables:
    Button btn_backToMainFromAdd;
    Button btn_addProductSave;

    // Product Variables:
    EditText num_addItemNumber, pt_addItemName, mt_addTypicalLocation, mt_addGeneralNotes, pt_addWebsiteLink, pt_addReviewVideoLink;

    Product product;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //setContentView(R.layout.activity_add_v2);
        databaseHelper = DatabaseHelper.getInstance(this);

        btn_backToMainFromAdd = findViewById(R.id.btn_backToMainFromEdit);
        btn_addProductSave = findViewById(R.id.btn_editProductSave);

        num_addItemNumber = findViewById(R.id.num_editItemNumber);
        pt_addItemName = findViewById(R.id.pt_editItemName);
        mt_addTypicalLocation = findViewById(R.id.mt_editTypicalLocation);
        mt_addGeneralNotes = findViewById(R.id.mt_editGeneralNotes);
        pt_addWebsiteLink = findViewById(R.id.pt_editWebsiteLink);
        pt_addReviewVideoLink = findViewById(R.id.pt_editReviewVideoLink);

        btn_backToMainFromAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, MainActivity_v2.class));
            }
        });

        btn_addProductSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num_addItemNumber.getText().toString().isEmpty()) {
                    Toast.makeText(AddActivity.this, "Item Number cannot be blank", Toast.LENGTH_SHORT).show();
                }
//                else if ( (pt_addWebsiteLink.getText().toString().contains("https://") == false) | (pt_addWebsiteLink.getText().toString().contains(".com") == false) ) {
//                    Toast.makeText(AddActivity.this, "Website Link must be a valid URL", Toast.LENGTH_SHORT).show();
//                }
//                else if ( (pt_addReviewVideoLink.getText().toString().contains("https://") == false) | (pt_addReviewVideoLink.getText().toString().contains(".com") == false) ) {
//                    Toast.makeText(AddActivity.this, "Video Review Link must be a valid URL", Toast.LENGTH_SHORT).show();
//                }
                else if (databaseHelper.checkIfProductExists(num_addItemNumber.getText().toString()) == true) {
                    Toast.makeText(AddActivity.this, "Item Number already used", Toast.LENGTH_SHORT).show();
                }
                else {
                    String itemNumber = String.valueOf(num_addItemNumber.getText());
                    String itemName = pt_addItemName.getText().toString();
                    String typicalLocation = mt_addTypicalLocation.getText().toString();
                    String generalNotes = mt_addGeneralNotes.getText().toString();
                    String websiteLink = pt_addWebsiteLink.getText().toString();
                    String reviewVideoLink = pt_addReviewVideoLink.getText().toString();

                    product = new Product(itemNumber,itemName,typicalLocation,generalNotes,websiteLink,reviewVideoLink);

                    databaseHelper.insertProduct(product);
                    Toast.makeText(AddActivity.this, product.toString(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AddActivity.this, MainActivity_v2.class));
                }
            }
        });

    }
}
