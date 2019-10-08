package com.example.styleomega.Model;

public class Order {
    String productId;
    String name;
    int quantity;
    double price;


    public Order(){

    }

    public Order(String productId, String name, int quantity, double price) {

        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
