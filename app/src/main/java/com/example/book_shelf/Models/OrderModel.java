package com.example.book_shelf.Models;

public class OrderModel {

    int orderId;
    String orderUserName;
    String oderUserPhone;
    String totalPrice;
    int totalQuantity;
    String orderDate;
    String orderAddress;

    public OrderModel(){}

    public OrderModel(int orderId, String orderUserName, String oderUserPhone, String totalPrice, int totalQuantity, String orderDate, String orderAddress) {
        this.orderId = orderId;
        this.orderUserName = orderUserName;
        this.oderUserPhone = oderUserPhone;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.orderDate = orderDate;
        this.orderAddress = orderAddress;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public String getOderUserPhone() {
        return oderUserPhone;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderId=" + orderId +
                ", orderUserName='" + orderUserName + '\'' +
                ", oderUserPhone='" + oderUserPhone + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", orderDate='" + orderDate + '\'' +
                ", orderAddress='" + orderAddress + '\'' +
                '}';
    }
}
