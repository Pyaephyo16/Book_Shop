package com.example.book_shelf.Models;

public class OrderDetailModel {

    int orderDetailId;
    int orderDetailIdForOder;
    int orderDetailBookId;
    int orderBookQuantity;
    String orderBookNTitle;

    public OrderDetailModel(int orderDetailId, int orderDetailIdForOder, int orderDetailBookId, int orderBookQuantity, String orderBookNTitle) {
        this.orderDetailId = orderDetailId;
        this.orderDetailIdForOder = orderDetailIdForOder;
        this.orderDetailBookId = orderDetailBookId;
        this.orderBookQuantity = orderBookQuantity;
        this.orderBookNTitle = orderBookNTitle;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public int getOrderDetailIdForOder() {
        return orderDetailIdForOder;
    }

    public int getOrderDetailBookId() {
        return orderDetailBookId;
    }

    public int getOrderBookQuantity() {
        return orderBookQuantity;
    }

    public String getOrderBookNTitle() {
        return orderBookNTitle;
    }

    @Override
    public String toString() {
        return "OrderDetailModel{" +
                "orderDetailId=" + orderDetailId +
                ", orderDetailIdForOder=" + orderDetailIdForOder +
                ", orderDetailBookId=" + orderDetailBookId +
                ", orderBookQuantity=" + orderBookQuantity +
                ", orderBookNTitle='" + orderBookNTitle + '\'' +
                '}';
    }
}
