package com.example.book_shelf.Helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.book_shelf.Frag.LoginByEmailFrag;
import com.example.book_shelf.Frag.LoginByPhoneFrag;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    String[] ary;

    public MyFragmentAdapter(@NonNull FragmentManager fm,String[] ary) {
        super(fm);
        this.ary = ary;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fg = null;
        switch (position){
            case 0 :
                fg = new LoginByPhoneFrag();break;
            case 1:
                fg =  new LoginByEmailFrag();break;
        }
        return fg;
    }

    @Override
    public int getCount() {
        return ary.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return ary[position];
    }
}
