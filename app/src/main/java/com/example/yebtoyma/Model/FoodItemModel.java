package com.example.yebtoyma.Model;

public class FoodItemModel {

    String food_image,food_name,food_type;
    int food_price;
    int totalcart;

    public int getTotalcart() {
        return totalcart;
    }

    public void setTotalcart(int totalcart) {
        this.totalcart = totalcart;
    }

    public FoodItemModel() {
    }


    public FoodItemModel(String food_image, String food_name, String food_type, int food_price) {
        this.food_image = food_image;
        this.food_name = food_name;
        this.food_type = food_type;
        this.food_price = food_price;
    }

    public String getFood_image() {
        return food_image;
    }

    public int getFood_price() {
        return food_price;
    }

    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }

    public void setFood_image(String food_image) {
        this.food_image = food_image;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }



    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }
}
