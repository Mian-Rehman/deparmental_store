package com.rehman.newtrend.Cart;

public class CartModel
{
    String userUID;
    String productKey;
    String productName;
    String productPrice;
    String productDiscountPrice;
    String productDescription;
    String productCoverImage;
    String cartKey;


    public CartModel(String userUID, String productKey, String productName, String productPrice, String productDiscountPrice, String productDescription, String productCoverImage, String cartKey) {
        this.userUID = userUID;
        this.productKey = productKey;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.productDescription = productDescription;
        this.productCoverImage = productCoverImage;
        this.cartKey = cartKey;
    }

    public CartModel() {
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(String productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductCoverImage() {
        return productCoverImage;
    }

    public void setProductCoverImage(String productCoverImage) {
        this.productCoverImage = productCoverImage;
    }

    public String getCartKey() {
        return cartKey;
    }

    public void setCartKey(String cartKey) {
        this.cartKey = cartKey;
    }
}
