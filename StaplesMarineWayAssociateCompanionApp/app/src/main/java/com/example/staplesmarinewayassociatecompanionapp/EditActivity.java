package com.example.staplesmarinewayassociatecompanionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class EditActivity extends AppCompatActivity {

    Button btn_backToMainFromEdit, btn_editProductDelete, btn_editProductSave;
    Button btn_editWebsite, btn_editVideoReviewLink;
    Switch stch_editProductEdit;
    WebView wv_editWebsiteLink;

    // Product Variables:
    EditText num_editItemNumber, pt_editItemName, mt_editTypicalLocation, mt_editGeneralNotes, pt_editWebsiteLink, pt_editReviewVideoLink;

    // Layout Variables:
    //ConstraintLayout ConstrainLayout_editOptions;

    DatabaseHelper databaseHelper;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //setContentView(R.layout.activity_edit_v2);

        btn_backToMainFromEdit = findViewById(R.id.btn_backToMainFromEdit);
        btn_editProductDelete = findViewById(R.id.btn_editProductDelete);
        btn_editProductSave = findViewById(R.id.btn_editProductSave);
        stch_editProductEdit = findViewById(R.id.stch_editProductEdit);

        num_editItemNumber = findViewById(R.id.num_editItemNumber);
        pt_editItemName = findViewById(R.id.pt_editItemName);
        mt_editTypicalLocation = findViewById(R.id.mt_editTypicalLocation);
        mt_editGeneralNotes = findViewById(R.id.mt_editGeneralNotes);
        pt_editWebsiteLink = findViewById(R.id.pt_editWebsiteLink);
        pt_editReviewVideoLink = findViewById(R.id.pt_editReviewVideoLink);

        stch_editProductEdit = findViewById(R.id.stch_editProductEdit);

        wv_editWebsiteLink = findViewById(R.id.wv_editWebsiteLink);

        btn_editWebsite = findViewById(R.id.btn_editWebsite);
        btn_editVideoReviewLink = findViewById(R.id.btn_editVideoReviewLink);

        //ConstrainLayout_editOptions = findViewById(R.id.ConstrainLayout_editOptions);

        format();

        databaseHelper = DatabaseHelper.getInstance(this);

        SharedPreferences preference = getSharedPreferences("user",MODE_PRIVATE);
        String clickedProductItemNumber = preference.getString("itemNumber",null);
        product = databaseHelper.getProductByItemNumber(clickedProductItemNumber);
        setProductInformation(product);

        btn_backToMainFromEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditActivity.this, MainActivity_v2.class));
            }
        });

        stch_editProductEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true) {
                    //Toast.makeText(EditActivity.this, "Edit Mode Enabled", Toast.LENGTH_SHORT).show();
                    btn_editProductDelete.setVisibility(View.VISIBLE);
                    btn_editProductSave.setVisibility(View.VISIBLE);
                    btn_backToMainFromEdit.setVisibility(View.INVISIBLE);

                    pt_editWebsiteLink.setVisibility(View.VISIBLE);
                    wv_editWebsiteLink.setVisibility(View.INVISIBLE);

                    pt_editReviewVideoLink.setVisibility(View.VISIBLE);

                    btn_editWebsite.setVisibility(View.INVISIBLE);
                    btn_editVideoReviewLink.setVisibility(View.INVISIBLE);

                    //ConstrainLayout_editOptions.setMaxHeight(250);

                    pt_editItemName.setEnabled(true);
                    if (pt_editItemName.isEnabled()) {
                        pt_editItemName.setTextColor(Color.WHITE);
                    }

                    mt_editTypicalLocation.setEnabled(true);
                    if (mt_editTypicalLocation.isEnabled()) {
                        mt_editTypicalLocation.setTextColor(Color.WHITE);
                    }

                    mt_editGeneralNotes.setEnabled(true);
                    if (mt_editGeneralNotes.isEnabled()) {
                        mt_editGeneralNotes.setTextColor(Color.WHITE);
                    }
                }
                else {
                    //Toast.makeText(EditActivity.this, "Edit Mode Disabled", Toast.LENGTH_SHORT).show();
                    btn_editProductDelete.setVisibility(View.INVISIBLE);
                    btn_editProductSave.setVisibility(View.INVISIBLE);
                    btn_backToMainFromEdit.setVisibility(View.VISIBLE);

                    pt_editWebsiteLink.setVisibility(View.INVISIBLE);
                    wv_editWebsiteLink.setVisibility(View.VISIBLE);

                    pt_editReviewVideoLink.setVisibility(View.INVISIBLE);

                    btn_editWebsite.setVisibility(View.VISIBLE);
                    btn_editVideoReviewLink.setVisibility(View.VISIBLE);

                    //ConstrainLayout_editOptions.setMaxHeight(800);

                    pt_editItemName.setEnabled(false);

                    mt_editTypicalLocation.setEnabled(false);

                    mt_editGeneralNotes.setEnabled(false);
                }
            }
        });

        btn_editWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getWebsiteLink()));
                    startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(EditActivity.this, "Website Link was not provided", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_editVideoReviewLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.getReviewVideoLink()));
                    startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(EditActivity.this, "Review Video Link was not provided", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_editProductSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProductInformation();
                databaseHelper.updateProduct(product);
                format();
                //Toast.makeText(EditActivity.this, "Updated " + product.toString(), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(EditActivity.this, MainActivity.class));
                stch_editProductEdit.setChecked(false);
            }
        });

        btn_editProductDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteProduct(product);
                //Toast.makeText(EditActivity.this, "Deleted " + product.toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditActivity.this, MainActivity_v2.class));
            }
        });
    }

    private void setProductInformation(Product product) {
        num_editItemNumber.setText(product.getItemNumber());
        pt_editItemName.setText(product.getItemName());
        mt_editTypicalLocation.setText(product.getTypicalLocation());
        mt_editGeneralNotes.setText(product.getGeneralNotes());
        pt_editWebsiteLink.setText(product.getWebsiteLink());
        pt_editReviewVideoLink.setText(product.getReviewVideoLink());

        try{
            wv_editWebsiteLink.loadUrl(product.getWebsiteLink());
            //Toast.makeText(EditActivity.this, product.getWebsiteLink(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            wv_editWebsiteLink.loadUrl("https://www.staples.ca/");
        }
    }

    private void format() {
        num_editItemNumber.setEnabled(false);

        pt_editItemName.setEnabled(false);
        if (pt_editItemName.isEnabled() == false) {
            pt_editItemName.setTextColor(Color.WHITE);
        }

        mt_editTypicalLocation.setEnabled(false);
        if (mt_editTypicalLocation.isEnabled() == false) {
            mt_editTypicalLocation.setTextColor(Color.WHITE);
        }

        mt_editGeneralNotes.setEnabled(false);
        if (mt_editGeneralNotes.isEnabled() == false) {
            mt_editGeneralNotes.setTextColor(Color.WHITE);
        }

        stch_editProductEdit.setChecked(false);

        btn_editProductDelete.setVisibility(View.INVISIBLE);
        btn_editProductSave.setVisibility(View.INVISIBLE);
        btn_backToMainFromEdit.setVisibility(View.VISIBLE);

        pt_editWebsiteLink.setVisibility(View.INVISIBLE);
        wv_editWebsiteLink.setVisibility(View.VISIBLE);

        pt_editReviewVideoLink.setVisibility(View.INVISIBLE);

        btn_editWebsite.setVisibility(View.VISIBLE);
        btn_editVideoReviewLink.setVisibility(View.VISIBLE);

        wv_editWebsiteLink.getSettings().setJavaScriptEnabled(true);
        wv_editWebsiteLink.getSettings().setDomStorageEnabled(true);
        wv_editWebsiteLink.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wv_editWebsiteLink.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    private void updateProductInformation(){
        String itemName = pt_editItemName.getText().toString();
        String typicalLocation = mt_editTypicalLocation.getText().toString();
        String generalNotes = mt_editGeneralNotes.getText().toString();
        String websiteLink = pt_editWebsiteLink.getText().toString();
        String reviewVideoLink = pt_editReviewVideoLink.getText().toString();

        product.setItemName(itemName);
        product.setTypicalLocation(typicalLocation);
        product.setGeneralNotes(generalNotes);
        product.setWebsiteLink(websiteLink);
        product.setReviewVideoLink(reviewVideoLink);
    }
}
