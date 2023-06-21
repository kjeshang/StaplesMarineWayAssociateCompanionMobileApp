package com.example.staplesmarinewayassociatecompanionapp;

public class Product {

    private String itemNumber;
    private String itemName;
    private String typicalLocation;
    private String generalNotes;
    private String websiteLink;
    private String reviewVideoLink;

    public Product(String itemNumber, String itemName, String typicalLocation, String generalNotes, String websiteLink, String reviewVideoLink) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.typicalLocation = typicalLocation;
        this.generalNotes = generalNotes;
        this.websiteLink = websiteLink;
        this.reviewVideoLink = reviewVideoLink;
    }

    public Product() { }

    public Product(String itemNumber, String itemName) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTypicalLocation() {
        return typicalLocation;
    }

    public void setTypicalLocation(String typicalLocation) {
        this.typicalLocation = typicalLocation;
    }

    public String getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(String generalNotes) {
        this.generalNotes = generalNotes;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getReviewVideoLink() {
        return reviewVideoLink;
    }

    public void setReviewVideoLink(String reviewVideoLink) {
        this.reviewVideoLink = reviewVideoLink;
    }

    @Override
    public String toString() {
//        return "Product{" +
//                "itemNumber=" + itemNumber +
//                ", itemName='" + itemName + '\'' +
//                ", typicalLocation='" + typicalLocation + '\'' +
//                ", generalNotes='" + generalNotes + '\'' +
//                ", websiteLink='" + websiteLink + '\'' +
//                ", reviewVideoLink='" + reviewVideoLink + '\'' +
//                '}';
        return itemName + " (SKU: " + itemNumber + ")";
    }

}
