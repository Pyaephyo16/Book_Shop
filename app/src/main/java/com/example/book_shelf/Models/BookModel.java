package com.example.book_shelf.Models;

public class BookModel {

    int bookId;
    String title;
    String author;
    String price;
    String pages;
    String picture;
    String type;
    int stock;
    String description;

    int currentTake = 1;

    public BookModel(){}

    public BookModel(int bookId, String title, String author, String price, String picture, String description, String pages,String type,int stock,int currentTake) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.pages = pages;
        this.picture = picture;
        this.type = type;
        this.stock = stock;
        this.description = description;
        this.currentTake = currentTake;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPrice() {
        return price;
    }

    public String getPages() {
        return pages;
    }

    public String getPicture() {
        return picture;
    }

    public String getType() {
        return type;
    }

    public int getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    public int getCurrentTake(){
        return currentTake;
    }

    public int increaseCurrentTake(){
        currentTake++;
        return  currentTake;
    }

    public int decreaseCurrentTake(){
        if (currentTake > 1){
            currentTake--;
        }
        return currentTake;
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", pages='" + pages + '\'' +
                ", picture='" + picture + '\'' +
                ", type='" + type + '\'' +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", currentTake=" + currentTake +
                '}';
    }
}
