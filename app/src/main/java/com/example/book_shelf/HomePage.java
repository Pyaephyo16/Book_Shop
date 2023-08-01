package com.example.book_shelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.book_shelf.Frag.DashboardFrag;
import com.example.book_shelf.Frag.HomeFrag;
import com.example.book_shelf.Frag.LibraryFrag;
import com.example.book_shelf.Util.Util;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    ///isAdmin ko u pho a twt db ka ny userDetail query swl pho lo
    FragmentManager fg;
    BottomNavigationView btnNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        setupUI();
    }

    private void setupUI(){
        btnNav = findViewById(R.id.btnNav);
        fg = getSupportFragmentManager();

        FragmentTransaction ft = fg.beginTransaction();
        ft.add(R.id.container,new HomeFrag(),"HomeFrag");
        ft.commit();

//        BadgeDrawable bd = btnNav.getOrCreateBadge(R.id.noti);
//        bd.setVisible(true);
//        bd.setNumber(5);

        checkAdminOrUser();

        btnNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.home){
                    fg.beginTransaction().replace(R.id.container,new HomeFrag()).commit();
                    return true;
                }
                else if(id == R.id.library){
                    String isAdmin = Util.getData(HomePage.this,"isAdmin");


                    if(isAdmin.equals("true")){
                        fg.beginTransaction().replace(R.id.container,new DashboardFrag()).commit();
                    }else{

                        fg.beginTransaction().replace(R.id.container,new LibraryFrag()).commit();
                    }
                    return true;
                }

                return false;
            }
        });


    }

    private void checkAdminOrUser(){
        MenuItem menuItemDashboard = btnNav.getMenu().findItem(R.id.library);
      String isAdmin =  Util.getData(HomePage.this,"isAdmin");
      if (isAdmin.equals("true")){
          menuItemDashboard.setIcon(R.drawable.dashboard);
          menuItemDashboard.setTitle("Dashboard");
//          btnNav.getMenu().findItem(R.id.library).setVisible(false);
//          btnNav.getMenu().findItem(R.id.dashboard).setVisible(true);
     }else{
          menuItemDashboard.setIcon(R.drawable.library);
          menuItemDashboard.setTitle("Library");
//          btnNav.getMenu().findItem(R.id.library).setVisible(true);
//          btnNav.getMenu().findItem(R.id.dashboard).setVisible(false);
      }
    }

    public void changeFrag(Fragment frag){
        //fg.beginTransaction().replace(R.id.fragHolder,frag).commit();
        fg.beginTransaction().replace(R.id.container,frag).commit();
    }
}