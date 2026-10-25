package com.example.yebtoyma.Model;

public class Order_food {
    String ProductImage,ProductName,ProductType;
    int ProductPrice,ProductAmount;
    String ProductUid;

    public String getProductUid() {
        return ProductUid;
    }

    public void setProductUid(String productUid) {
        ProductUid = productUid;
    }

    public Order_food() {
    }

    public Order_food(String productImage, String productName, String productType, int productPrice, int productAmount) {
        ProductImage = productImage;
        ProductName = productName;
        ProductType = productType;
        ProductPrice = productPrice;
        ProductAmount = productAmount;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public int getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(int productAmount) {
        ProductAmount = productAmount;
    }
}
