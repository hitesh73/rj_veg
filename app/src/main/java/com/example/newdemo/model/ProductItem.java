package com.example.newdemo.model;

import java.io.Serializable;

public class ProductItem implements Serializable {
    String ProductName;
    String ProductDescription;
    String ProductImage;
    String ProductPrice;
    String productId;
    String productDiscount;
    String topSelling;
    String dealOfTheDay;
    String favProductId;

    public String getFavProductId() {
        return favProductId;
    }

    public void setFavProductId(String favProductId) {
        this.favProductId = favProductId;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getTopSelling() {
        return topSelling;
    }

    public void setTopSelling(String topSelling) {
        this.topSelling = topSelling;
    }

    public String getDealOfTheDay() {
        return dealOfTheDay;
    }

    public void setDealOfTheDay(String dealOfTheDay) {
        this.dealOfTheDay = dealOfTheDay;
    }

    public String getProductWeight() {
        return ProductWeight;
    }

    public void setProductWeight(String productWeight) {
        ProductWeight = productWeight;
    }

    String ProductWeight;




    public ProductItem(){

    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }
}
