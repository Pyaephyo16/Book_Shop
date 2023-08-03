package com.example.book_shelf.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<Integer> addToCardList = new ArrayList<>();

    public static List<Integer> promoList = new ArrayList<>();

    public static int totalCost = 0;

    public static int tryCount = 0;

    public static void showToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void saveData(Context context,String key,String value){
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edt = sh.edit();
        edt.putString(key,value);
        edt.apply();
    }

    public static String getData(Context context,String key){
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String data = sh.getString(key,"null");
        if(data!=null){
            return data;
        }else{
            return "";
        }
    }


}
