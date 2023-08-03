package com.example.book_shelf.Models;

public class UserModel {

    String name;
    String phone;
    String email;
    String password;
    String profile;
    String isAdmin;

    int ownBook = 0;

    public UserModel(){}

    public UserModel(String name, String phone, String email, String password, String profile, String isAdmin,int ownBook) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.isAdmin = isAdmin;
        this.ownBook = ownBook;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public int getOwnBook() {
        return ownBook;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profile='" + profile + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", ownBook='" + ownBook + '\'' +
                '}';
    }
}
