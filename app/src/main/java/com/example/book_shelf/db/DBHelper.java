package com.example.book_shelf.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Models.OrderDetailModel;
import com.example.book_shelf.Models.OrderModel;
import com.example.book_shelf.Models.ShelfBookModel;
import com.example.book_shelf.Models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bookStore.db";

    ///Table Names
    private static final String userTable = "user";

    private static final String bookTable = "book";

    private static final String orderTable = "orderBook";

    private static final String orderDetailTable = "orderDetail";

    private static final String shelfTable = "shelf";

    ///userTable field names
    private String userId = "uId";
    private String userName = "uName";
    private String userPhone = "phone";
    private String userEmail = "email";
    private String userPassword = "password";
    private String userProfile = "profile";

    private String isAdmin = "isAdmin";

    private String ownBook = "ownBook";

    ///bookTable field names
    private String bookId = "bId";
    private String bookTitle = "title";
    private String bookAuthor = "author";
    private String bookPrice = "price";
    private String bookPicture = "picture";
    private String bookDescription = "description";
    private String bookPages = "pages";
    private String bookType = "type";
    private String bookStock = "stock";

    private String promoPrice = "promoPrice";
    private String promoPercent = "promoPercent";
    private String promoName = "promoName";


    ///orderTable field names
    private String orderId = "oId";
    private String orderUserName = "name"; //
    private String orderUserPhone = "phone"; //
    private String totalPrice = "totalPrice";
    private String totalQuantity = "totalQty";
    private String orderDate = "date";
    private String orderAddress = "address";

    ///orderDetailTable field names
    private String orderDetailId = "odId";
    private String orderDetailIdForOrder = "oId";
    private String orderDetailBookId = "bId";
    private String orderBookQuantity = "bookQty";
    private String orderBookNTitle = "title";

    ///shelfTable fields names
    private String shelfId = "sId";
    private String shelfUserPhone = "phone";
    private String shelfUserName = "name";
    private String shelfBookId = "bId";
    private String shelfBookTitle = "title";
    private String shelfBookAuthor = "author";
    private String shelfBookPrice = "price";
    private String shelfBookPicture = "picture";
    private String shelfBookDescription = "description";
    private String shelfBookPages = "pages";
    private String shelfBookType = "type";
    private String shelfOrderDate = "orderDate";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String userCreate ="CREATE TABLE "+userTable+"("+userName+" TEXT,"+userPhone+" TEXT,"+userEmail+" TEXT,"+userPassword+" TEXT,"+userProfile+" TEXT,"+isAdmin+" TEXT,"+ownBook+" INTEGER)";
        db.execSQL(userCreate);

        String bookCreate = "CREATE TABLE "+bookTable+"("+bookId+" INTEGER PRIMARY KEY AUTOINCREMENT,"+bookTitle+" TEXT,"+bookAuthor+" TEXT,"+bookPrice+" TEXT,"+bookPicture+" TEXT,"+bookDescription+" TEXT,"+bookPages+" TEXT,"+bookType+" TEXT,"+bookStock+" INTEGER,"+promoPrice+" TEXT,"+promoPercent+" TEXT,"+promoName+" TEXT)";
        db.execSQL(bookCreate);

        String orderCreate = "CREATE TABLE "+orderTable+"("+orderId+" INTEGER PRIMARY KEY AUTOINCREMENT,"+orderUserName+" TEXT,"+orderUserPhone+" TEXT,"+totalPrice+" TEXT,"+totalQuantity+" INTEGER,"+orderDate+" TEXT,"+orderAddress+" TEXT)";
        db.execSQL(orderCreate);

        String orderDetailCreate = "CREATE TABLE "+orderDetailTable+"("+orderDetailId+" INTEGER PRIMARY KEY AUTOINCREMENT,"+orderDetailIdForOrder+" INTEGER,"+orderDetailBookId+" INTEGER,"+orderBookQuantity+" INTEGER,"+orderBookNTitle+" TEXT)";
            db.execSQL(orderDetailCreate);

            String userShelfCreate = "CREATE TABLE "+shelfTable+"("+shelfId+" INTEGER PRIMARY KEY AUTOINCREMENT,"+shelfUserPhone+" TEXT,"+shelfUserName+" TEXT,"+shelfBookId+" INTEGER,"+shelfBookTitle+" TEXT,"+shelfBookAuthor+" TEXT,"+shelfBookPrice+" TEXT,"+shelfBookPicture+" TEXT,"+shelfBookDescription+" TEXT,"+shelfBookPages+" TEXT,"+shelfBookType+" TEXT,"+shelfOrderDate+" TEXT)";
            db.execSQL(userShelfCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+userTable);
        db.execSQL("DROP TABLE IF EXISTS "+bookTable);
        db.execSQL("DROP TABLE IF EXISTS "+orderTable);
        db.execSQL("DROP TABLE IF EXISTS "+orderDetailTable);
        db.execSQL("DROP TABLE IF EXISTS "+shelfTable);
    }

    ///Register User
    public void registerUser(String name,String phone,String email,String password,String profile){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        System.out.println("register user db ====================> "+name+"  "+phone+"  "+email+"  "+password+"  "+profile);

        contentValues.put(userName,name);
        contentValues.put(userPhone,phone);
        contentValues.put(userEmail,email);
        contentValues.put(userPassword,password);
        contentValues.put(userProfile,profile);
        contentValues.put(isAdmin,"false");
        contentValues.put(ownBook,0);

        database.insert(userTable,null,contentValues);
        database.close();
    }

    ///Get All users
    public List<UserModel> getAllUsers(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor userCursor = database.rawQuery("SELECT * FROM "+userTable, null);

        List<UserModel> userList = new ArrayList<>();

        if(userCursor.moveToFirst()){
            do {
                userList.add(
                    new UserModel(
                    userCursor.getString(0),
                    userCursor.getString(1),
                    userCursor.getString(2),
                    userCursor.getString(3),
                    userCursor.getString(4),
                    userCursor.getString(5),
                            userCursor.getInt(6)
                    )
                );
            }while (userCursor.moveToNext());
        }
        return userList;
    }

    ///Get user detail
    public UserModel getUserDetail(String phone){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor userDetailCursor =database.rawQuery("SELECT * FROM user WHERE phone='"+phone+"'",null);

        UserModel model = new UserModel();

        if (userDetailCursor.moveToFirst()){
            model = new UserModel(
                    userDetailCursor.getString(0),
                    userDetailCursor.getString(1),
                    userDetailCursor.getString(2),
                    userDetailCursor.getString(3),
                    userDetailCursor.getString(4),
                    userDetailCursor.getString(5),
                    userDetailCursor.getInt(6)
            );
        }
        return model;
    }

    ///Update User
    public void updateUser(String updateName,String updatePhone,String updateEmail,String updatePassword,String updateProfile,int own){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(userName,updateName);
        content.put(userPhone,updatePhone);
        content.put(userEmail,updateEmail);
        content.put(userPassword,updatePassword);
        content.put(userProfile,updateProfile);
        content.put(ownBook,own);

        db.update(userTable,content,"phone=?",new String[]{updatePhone});
        db.close();
    }

    ///Add book
    public void addBook(String title,String author,String price,String picture,String description,String pages,String type,int stock,String pPrice,String pPercent,String pName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(bookTitle,title);
        content.put(bookAuthor,author);
        content.put(bookPrice,price);
        content.put(bookPicture,picture);
        content.put(bookDescription,description);
        content.put(bookPages,pages);
        content.put(bookType,type);
        content.put(bookStock,stock);
        content.put(promoPrice,pPrice);
        content.put(promoPercent,pPercent);
        content.put(promoName,pName);

        db.insert(bookTable,null,content);
        db.close();
    }

    ///Update book
    public void updateBook(String id,String title,String author,String price,String picture,String description,String pages,String type,int stock,String pPrice,String pPercent,String pName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(bookTitle,title);
        content.put(bookAuthor,author);
        content.put(bookPrice,price);
        content.put(bookPicture,picture);
        content.put(bookDescription,description);
        content.put(bookPages,pages);
        content.put(bookType,type);
        content.put(bookStock,stock);
        content.put(promoPrice,pPrice);
        content.put(promoPercent,pPercent);
        content.put(promoName,pName);

        db.update(bookTable,content,"bId=?",new String[]{id});
    }

    ///Delete book
    public void deleteBook(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(bookTable,"bId=?",new String[]{id});
        database.close();
    }

    ///Get all books
    public List<BookModel> getAllBooks(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+bookTable,null);

        List<BookModel> bookList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                bookList.add(
                  new BookModel(
                          cursor.getInt(0),
                          cursor.getString(1),
                          cursor.getString(2),
                          cursor.getString(3),
                          cursor.getString(4),
                          cursor.getString(5),
                          cursor.getString(6),
                          cursor.getString(7),
                          cursor.getInt(8),
                          1,
                          cursor.getString(9),
                          cursor.getString(10),
                          cursor.getString(11)
                  )
                );
            }while (cursor.moveToNext());
        }
        return bookList;
    }


    ///Get Single Book
    public BookModel getSingleBook(String bookId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+bookTable+" WHERE bId="+bookId,null);

        BookModel model = new BookModel();
        if (cursor.moveToFirst()){
                model =new BookModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getInt(8),
                        1,
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11)
                );
        }
        return model;
    }

    ///Add Order
    public void addOrder(String name,String phone,String price,int qty,String date,String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(orderUserName,name);
        content.put(orderUserPhone,phone);
        content.put(totalPrice,price);
        content.put(totalQuantity,qty);
        content.put(orderDate,date);
        content.put(orderAddress,address);

        db.insert(orderTable,null,content);
        db.close();
    }

    ///Get All Orders
    public List<OrderModel> getAllOrders(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor orderCursor = db.rawQuery("SELECT * FROM "+orderTable,null);

        List<OrderModel> orderList = new ArrayList<>();

        if (orderCursor.moveToFirst()){
            do {
                orderList.add(
                        new OrderModel(
                                orderCursor.getInt(0),
                                orderCursor.getString(1),
                                orderCursor.getString(2),
                                orderCursor.getString(3),
                                orderCursor.getInt(4),
                                orderCursor.getString(5),
                                orderCursor.getString(6)
                        )
                );
            }while (orderCursor.moveToNext());
        }
        return orderList;
    }

    ///Get Order By User
    //to get last index of list
    public List<OrderModel> getOrderByUser(String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor orderCursor = db.rawQuery("SELECT * FROM orderBook WHERE phone='"+phone+"'",null);

        List<OrderModel> orderList = new ArrayList<>();

        if (orderCursor.moveToFirst()){
            do {
                orderList.add(
                        new OrderModel(
                            orderCursor.getInt(0),
                                orderCursor.getString(1),
                                orderCursor.getString(2),
                                orderCursor.getString(3),
                                orderCursor.getInt(4),
                                orderCursor.getString(5),
                                orderCursor.getString(6)
                        )
                );
            }while (orderCursor.moveToNext());
        }
        return orderList;
    }

    ///Get Order By Order Id
    public OrderModel getOrderById(String orderId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor orderCursor = db.rawQuery("SELECT * FROM orderBook WHERE oId='"+orderId+"'",null);

        OrderModel order = new OrderModel();

        if (orderCursor.moveToFirst()){
                       order = new OrderModel(
                                orderCursor.getInt(0),
                                orderCursor.getString(1),
                                orderCursor.getString(2),
                                orderCursor.getString(3),
                                orderCursor.getInt(4),
                                orderCursor.getString(5),
                                orderCursor.getString(6)
                        );
        }
        return order;
    }



    ///Add Order Detail
    public void addOrderDetail(int orderId,int bookId,int bookQty,String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(orderDetailIdForOrder,orderId);
        content.put(orderDetailBookId,bookId);
        content.put(orderBookQuantity,bookQty);
        content.put(orderBookNTitle,title);

        db.insert(orderDetailTable,null,content);
        db.close();
    }

    ///Get Order Detail By User
    public List<OrderDetailModel> getOrderDetailByUser(int orderId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor detailCursor = db.rawQuery("SELECT * FROM orderDetail WHERE oId='"+orderId+"'",null);

        List<OrderDetailModel> orderDetailList = new ArrayList<>();

        if (detailCursor.moveToFirst()){
            do {
                orderDetailList.add(
                        new OrderDetailModel(
                                detailCursor.getInt(0),
                                detailCursor.getInt(1),
                                detailCursor.getInt(2),
                                detailCursor.getInt(3),
                                detailCursor.getString(4)
                        )
                );
            }while (detailCursor.moveToNext());
        }
        return orderDetailList;
    }

    ///Add User shelf
    public void addUserShelf(String phone,String name,int bookId,String title,String author,String price,String picture,String description,String pages,String type,String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(shelfUserPhone,phone);
        content.put(shelfUserName,name);
        content.put(shelfBookId,bookId);
        content.put(shelfBookTitle,title);
        content.put(shelfBookAuthor,author);
        content.put(shelfBookPrice,price);
        content.put(shelfBookPicture,picture);
        content.put(shelfBookDescription,description);
        content.put(shelfBookPages,pages);
        content.put(shelfBookType,type);
        content.put(shelfOrderDate,date);


        db.insert(shelfTable,null,content);
        db.close();
    }

    ///Get shelf by User
    public List<ShelfBookModel> getShelfBooksByUser(String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM shelf WHERE phone='"+phone+"'",null);

        List<ShelfBookModel> shelfList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                shelfList.add(
                        new ShelfBookModel(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getInt(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9),
                                cursor.getString(10),
                                cursor.getString(11)
                        )
                );
            }while (cursor.moveToNext());
        }
        return shelfList;
    }

}
