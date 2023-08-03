package com.example.book_shelf.Models;

public class ShelfBookModel {

    int shelfId;
    String shelfUserPhone;
    String shelfUserName;
    int shelfBookId;
    String shelfBookTitle;
    String shelfBookAuthor;
    String shelfBookPrice;
    String shelfBookPicture;
    String shelfBookDescription;
    String shelfBookPages;
    String shelfBookType;

    String orderDate;

    public ShelfBookModel(int shelfId, String shelfUserPhone, String shelfUserName, int shelfBookId, String shelfBookTitle, String shelfBookAuthor, String shelfBookPrice, String shelfBookPicture, String shelfBookDescription, String shelfBookPages, String shelfBookType,String orderDate) {
        this.shelfId = shelfId;
        this.shelfUserPhone = shelfUserPhone;
        this.shelfUserName = shelfUserName;
        this.shelfBookId = shelfBookId;
        this.shelfBookTitle = shelfBookTitle;
        this.shelfBookAuthor = shelfBookAuthor;
        this.shelfBookPrice = shelfBookPrice;
        this.shelfBookPicture = shelfBookPicture;
        this.shelfBookDescription = shelfBookDescription;
        this.shelfBookPages = shelfBookPages;
        this.shelfBookType = shelfBookType;
        this.orderDate = orderDate;
    }

    public int getShelfId() {
        return shelfId;
    }

    public String getShelfUserPhone() {
        return shelfUserPhone;
    }

    public String getShelfUserName() {
        return shelfUserName;
    }

    public int getShelfBookId() {
        return shelfBookId;
    }

    public String getShelfBookTitle() {
        return shelfBookTitle;
    }

    public String getShelfBookAuthor() {
        return shelfBookAuthor;
    }

    public String getShelfBookPrice() {
        return shelfBookPrice;
    }

    public String getShelfBookPicture() {
        return shelfBookPicture;
    }

    public String getShelfBookDescription() {
        return shelfBookDescription;
    }

    public String getShelfBookPages() {
        return shelfBookPages;
    }

    public String getShelfBookType() {
        return shelfBookType;
    }

    public String getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "ShelfBookModel{" +
                "shelfId=" + shelfId +
                ", shelfUserPhone='" + shelfUserPhone + '\'' +
                ", shelfUserName='" + shelfUserName + '\'' +
                ", shelfBookId=" + shelfBookId +
                ", shelfBookTitle='" + shelfBookTitle + '\'' +
                ", shelfBookAuthor='" + shelfBookAuthor + '\'' +
                ", shelfBookPrice='" + shelfBookPrice + '\'' +
                ", shelfBookPicture='" + shelfBookPicture + '\'' +
                ", shelfBookDescription='" + shelfBookDescription + '\'' +
                ", shelfBookPages='" + shelfBookPages + '\'' +
                ", shelfBookType='" + shelfBookType + '\'' +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
