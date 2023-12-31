package com.example.staplesmarinewayassociatecompanionapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.staplesmarinewayassociatecompanionapp.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // SQL Variables ---------------------------------------------------------

    // Product Table & Columns:
    private static final String PRODUCT_TABLE = "PRODUCT";

    private static final String ITEM_NUMBER_COL = "ItemNumber";
    private static final String ITEM_NAME_COL = "ItemName";
    private static final String TYPICAL_LOCATION_COL = "TypicalLocation";
    private static final String GENERAL_NOTES_COL = "GeneralNotes";
    private static final String WEBSITE_LINK_COL = "WebsiteLink";
    private static final String REVIEW_VIDEO_LINK_COL = "ReviewVideoLink";

    // Bin Table & Columns:
    private static final String BIN_TABLE = "BIN";

    private static final String BIN_ID_COL = "BinID";
    private static final String STOCK_IN_COUNT_COL = "StockInCount";
    private static final String STOCK_OUT_COUNT_COL = "StockOutCount";
    private static final String PRODUCT_NAME_COL = "ItemName";
    private static final String PRODUCT_COL = "ItemNumber";

    // SQL Statements --------------------------------------------------------

    // Create:
    private static final String createProductTable = "CREATE TABLE " + PRODUCT_TABLE +
            "(" +
                ITEM_NUMBER_COL + " TEXT PRIMARY KEY, " +
                ITEM_NAME_COL + " TEXT, " +
                TYPICAL_LOCATION_COL + " TEXT, " +
                GENERAL_NOTES_COL + " TEXT, " +
                WEBSITE_LINK_COL + " TEXT, " +
                REVIEW_VIDEO_LINK_COL + " TEXT " +
            ");";

    private static final String createBinTable = "CREATE TABLE " + BIN_TABLE +
            "(" +
                BIN_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                STOCK_IN_COUNT_COL + " INTEGER, " +
                STOCK_OUT_COUNT_COL + " INTEGER, " +
                PRODUCT_NAME_COL + " TEXT, " +
                PRODUCT_COL + " TEXT " +
                    "REFERENCES " + PRODUCT_TABLE + "(" + ITEM_NUMBER_COL + ") " +
                    "ON DELETE CASCADE" +
            ");";

    // Drop:
    private static final String dropProductTable = "DROP TABLE if exists " + PRODUCT_TABLE + ";";
    private static final String dropBinTable = "DROP TABLE if exists " + BIN_TABLE + ";";

    // Database Instantiation Variables --------------------------------------
    private static final String DATABASE_NAME = "ProductData.db";
    private static final int DATABASE_VERSION = 1;
    private static String TAG = "DBHelper";

    private static DatabaseHelper instance;

    // Database Constructors -------------------------------------------------

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Database Instantiation Functions ------------------------------------

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createProductTable);
        Log.d(TAG, "Product Table Created");
        db.execSQL(createBinTable);
        Log.d(TAG, "Bin Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL(dropProductTable);
            db.execSQL(dropBinTable);
            onCreate(db);
        }
    }

    // Functions that enact Insert/Update/Delete/Select SQL Statements regarding Product Table -------------------------

    public boolean checkIfProductExists(String itemNumber) {
        String query = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + ITEM_NUMBER_COL + " == " + itemNumber + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean status = false;
        if(cursor.moveToFirst()) {
            status = true;
        }
        cursor.close();
        return status;
    }

    public void insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ITEM_NUMBER_COL, product.getItemNumber());
        values.put(ITEM_NAME_COL, product.getItemName());
        values.put(TYPICAL_LOCATION_COL, product.getTypicalLocation());
        values.put(GENERAL_NOTES_COL, product.getGeneralNotes());
        values.put(WEBSITE_LINK_COL, product.getWebsiteLink());
        values.put(REVIEW_VIDEO_LINK_COL, product.getReviewVideoLink());

        db.insert(PRODUCT_TABLE, null, values);
        db.close();
    }

    public List<Product> getAllProducts(String searchViewText) {
        String query = "";
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(searchViewText.length() > 0) {
            //query = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + ITEM_NUMBER_COL + " = ? OR " + ITEM_NAME_COL + " = ?;";
            //cursor = db.rawQuery(query, new String[]{searchViewText, searchViewText});
            query = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + ITEM_NUMBER_COL + " = ? OR " + ITEM_NAME_COL + " LIKE ?;";
            cursor = db.rawQuery(query, new String[]{searchViewText, "%" + searchViewText + "%"});
        }
        else {
            query = "SELECT * FROM " + PRODUCT_TABLE + ";";
            cursor = db.rawQuery(query, null);
        }

        if(cursor.moveToFirst()) {
            do {
                String itemNumber = cursor.getString(0);
                String itemName = cursor.getString(1);
                String typicalLocation = cursor.getString(2);
                String generalNotes = cursor.getString(3);
                String websiteLink = cursor.getString(4);
                String reviewVideoLink = cursor.getString(5);
                Product product = new Product(itemNumber,itemName,typicalLocation,generalNotes,websiteLink,reviewVideoLink);
                list.add(product);

            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public Product getProductByItemNumber(String itemNumber){
        String query = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + ITEM_NUMBER_COL + " = " + itemNumber + ";";
        Product product = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            //String itemNumber = cursor.getString(0);
            String itemName = cursor.getString(1);
            String typicalLocation = cursor.getString(2);
            String generalNotes = cursor.getString(3);
            String websiteLink = cursor.getString(4);
            String reviewVideoLink = cursor.getString(5);
            product = new Product(itemNumber,itemName,typicalLocation,generalNotes,websiteLink,reviewVideoLink);
        }
        return product;
    }

    public Product getProductByItemName(String itemName){
        String query = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + ITEM_NAME_COL + " = '" + itemName + "';";
        Product product = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            String itemNumber = cursor.getString(0);
            //String itemName = cursor.getString(1);
            String typicalLocation = cursor.getString(2);
            String generalNotes = cursor.getString(3);
            String websiteLink = cursor.getString(4);
            String reviewVideoLink = cursor.getString(5);
            product = new Product(itemNumber,itemName,typicalLocation,generalNotes,websiteLink,reviewVideoLink);
        }
        return product;
    }

    public void updateProduct(Product product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_NAME_COL,product.getItemName());
        values.put(TYPICAL_LOCATION_COL, product.getTypicalLocation());
        values.put(GENERAL_NOTES_COL, product.getGeneralNotes());
        values.put(WEBSITE_LINK_COL, product.getWebsiteLink());
        values.put(REVIEW_VIDEO_LINK_COL, product.getReviewVideoLink());
        db.update(PRODUCT_TABLE, values,ITEM_NUMBER_COL + " = ?", new String[]{product.getItemNumber()});
        db.close();
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRODUCT_TABLE, ITEM_NUMBER_COL + " = ?", new String[]{product.getItemNumber()});
        db.close();
    }

    // Functions that enact Insert/Update/Delete/Select SQL Statements regarding Bin Table -------------------------

    public boolean checkIfBinEntryExists(String itemNumber) {
        String query = "SELECT * FROM " + BIN_TABLE + " WHERE " + PRODUCT_COL + " == " + itemNumber + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean status = false;
        if(cursor.moveToFirst()) {
            status = true;
        }
        cursor.close();
        return status;
    }

    public void insertBinEntry(BinEntry binEntry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STOCK_IN_COUNT_COL, binEntry.getStockInCount());
        values.put(STOCK_OUT_COUNT_COL, binEntry.getStockOutCount());
        values.put(PRODUCT_NAME_COL, binEntry.getProductName());
        values.put(PRODUCT_COL, binEntry.getProduct());

        db.insert(BIN_TABLE, null, values);
        db.close();
    }

    public List<BinEntry> getAllBinEntries(String condition) {
        String query = "";
        List<BinEntry> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(condition == "StockIn"){
            query = "SELECT * FROM " + BIN_TABLE + " WHERE " + STOCK_IN_COUNT_COL + " > 0";
        }
        else if (condition == "StockOut"){
            query = "SELECT * FROM " + BIN_TABLE + " WHERE " + STOCK_OUT_COUNT_COL + " > 0";
        }
        else {
            query = "SELECT * FROM " + BIN_TABLE + ";";
        }
        //query = "SELECT * FROM " + BIN_TABLE + ";";
        cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                int stockInCount = cursor.getInt(1);
                int stockOutCount = cursor.getInt(2);
                String productName = cursor.getString(3);
                String product = cursor.getString(4);
                BinEntry binEntry = new BinEntry(stockInCount, stockOutCount, productName, product);
                list.add(binEntry);

            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public BinEntry getBinEntryByProduct(String itemNumber){
        String query = "SELECT * FROM " + BIN_TABLE + " WHERE " + PRODUCT_COL + " == " + itemNumber + ";";
        BinEntry binEntry = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            int stockInCount = cursor.getInt(1);
            int stockOutCount = cursor.getInt(2);
            String productName = cursor.getString(3);
            String product = itemNumber;
            binEntry = new BinEntry(stockInCount, stockOutCount, productName, product);
        }
        cursor.close();
        return binEntry;
    }

    public void deleteBinEntry(BinEntry binEntry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BIN_TABLE, PRODUCT_COL + " = ?", new String[]{binEntry.getProduct()});
        db.close();
    }

    public void updateBinEntry(BinEntry binEntry){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STOCK_IN_COUNT_COL, binEntry.getStockInCount());
        values.put(STOCK_OUT_COUNT_COL, binEntry.getStockOutCount());
        db.update(BIN_TABLE, values,PRODUCT_COL + " = ?", new String[]{binEntry.getProduct()});
        db.close();
    }

}
