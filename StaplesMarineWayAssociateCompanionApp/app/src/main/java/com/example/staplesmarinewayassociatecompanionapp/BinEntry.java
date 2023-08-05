package com.example.staplesmarinewayassociatecompanionapp;

public class BinEntry {

    private int stockInCount;
    private int stockOutCount;
    private String productName;
    private String product;

    public BinEntry(int stockInCount, int stockOutCount, String productName, String product) {
        this.stockInCount = stockInCount;
        this.stockOutCount = stockOutCount;
        this.productName = productName;
        this.product = product;
    }

    public int getStockInCount() {
        return stockInCount;
    }

    public void setStockInCount(int stockInCount) {
        this.stockInCount = stockInCount;
    }

    public int getStockOutCount() {
        return stockOutCount;
    }

    public void setStockOutCount(int stockOutCount) {
        this.stockOutCount = stockOutCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return productName + " (SKU: " + product + ")\n" +
                "\tStock-In: " + stockInCount + "\t\t" + "Stock-Out: " + stockOutCount;
    }
}
