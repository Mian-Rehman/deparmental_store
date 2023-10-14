package com.rehman.newtrend.Model;

public class ProductDetailsModel
{
    String productKey;
    String productCategory;
    String productColor;
    String productCoverImage;
    String productDescription;
    String productDiscountPrice;
    String productPrice;
    String productTitle;

    public ProductDetailsModel(String productKey, String productCategory, String productColor, String productCoverImage, String productDescription, String productDiscountPrice, String productPrice, String productTitle) {
        this.productKey = productKey;
        this.productCategory = productCategory;
        this.productColor = productColor;
        this.productCoverImage = productCoverImage;
        this.productDescription = productDescription;
        this.productDiscountPrice = productDiscountPrice;
        this.productPrice = productPrice;
        this.productTitle = productTitle;
    }

    public ProductDetailsModel() {
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductCoverImage() {
        return productCoverImage;
    }

    public void setProductCoverImage(String productCoverImage) {
        this.productCoverImage = productCoverImage;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(String productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
}
