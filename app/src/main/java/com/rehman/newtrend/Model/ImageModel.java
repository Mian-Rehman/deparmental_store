package com.rehman.newtrend.Model;

public class ImageModel
{

    String productImages;


    public ImageModel(String productImages) {
        this.productImages = productImages;
    }

    public ImageModel() {
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }
}